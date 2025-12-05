package aqario.fowlplay.common.entity.ai.navigation;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.navigation.ExtendedNavigator;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class FlightNavigation extends GroundPathNavigation implements ExtendedNavigator {
    private static final int NODE_DISTANCE = 2;
    private static final float NODE_REACH_RADIUS = 1.5f;

    public FlightNavigation(FlyingBirdEntity bird, Level world) {
        super(bird, world);
    }

    @Override
    public Mob getMob() {
        return this.mob;
    }

    @Nullable
    @Override
    public Path getPath() {
        return super.getPath();
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new FlyNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);

        return new PathFinder(this.nodeEvaluator, maxVisitedNodes) {
            @Nullable
            @Override
            public Path findPath(PathNavigationRegion navigationRegion, Mob mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
                FlightNavigation.this.nodeEvaluator.mob = FlightNavigation.this.mob;
                final Path path = super.findPath(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
                FlightNavigation.this.nodeEvaluator.mob = null;

                return FlightNavigation.this.patchPath(path);
            }
        };
    }

    public @Nullable Path patchPath(@Nullable Path path) {
        if(path == null) {
            return null;
        }

        Path newPath = new Path(path.nodes, path.getTarget(), path.canReach()) {
            @Override
            public Vec3 getEntityPosAtNode(Entity entity, int nodeIndex) {
                return FlightNavigation.this.getEntityPosAtNode(nodeIndex);
            }
        };
        Node[] debugNodes = path.getOpenSet();
        Node[] debugSecondNodes = path.getClosedSet();
        Set<Target> debugTargetNodes = path.targetNodes;
        newPath.setDebug(debugNodes, debugSecondNodes, debugTargetNodes);

        return newPath;
    }

    @Override
    public boolean moveTo(double x, double y, double z, double speed) {
        this.mob.getMoveControl().setWantedPosition(x, y, z, speed);
        return true;
    }

    @Override
    public boolean moveTo(Entity entity, double speed) {
        this.mob.getMoveControl().setWantedPosition(entity.getX(), entity.getY(), entity.getZ(), speed);
        return true;
    }

    @Override
    protected boolean canMoveDirectly(Vec3 origin, Vec3 target) {
        return isClearForMovementBetween(this.mob, origin, target, true);
    }

    @Override
    protected boolean canUpdatePath() {
        return this.canFloat() && this.isInLiquid() || !this.mob.isPassenger();
    }

    @Override
    protected Vec3 getTempMobPos() {
        return this.getMob().position();
    }

    @Override
    protected double getGroundY(Vec3 pos) {
        return pos.y;
    }

    @Override
    public Path createPath(BlockPos target, int distance) {
        return this.createPath(ImmutableSet.of(target), 16, false, distance, 64);
    }

    @Override
    public void tick() {
        this.tick++;
        if(this.hasDelayedRecomputation) {
            this.recomputePath();
        }

        if(!this.isDone()) {
            if(this.canUpdatePath()) {
                this.followThePath();
            }
            else if(this.path != null && !this.path.isDone()) {
                Vec3 pos = this.getTempMobPos();
                Vec3 nodePos = this.path.getNextEntityPos(this.mob);
                if(pos.y > nodePos.y
                    && !this.mob.onGround()
                    && Mth.floor(pos.x) == Mth.floor(nodePos.x)
                    && Mth.floor(pos.z) == Mth.floor(nodePos.z)) {
                    this.path.advance();
                }
            }
            if(this.path != null
                && this.path.isDone()
                && this.getTargetPos() != null
                && this.mob.position().closerThan(Vec3.atBottomCenterOf(this.getTargetPos()), 2)
                && Birds.shouldLandAtDestination((FlyingBirdEntity) this.mob, this.getTargetPos())
            ) {
                ((FlyingBirdEntity) this.mob).stopFlying();
            }

            DebugPackets.sendPathFindingPacket(this.level, this.getMob(), this.getPath(), 0.1f);
            if(!this.isDone()) {
                // noinspection ConstantConditions
                Vec3 vec3d = this.path.getNextEntityPos(this.mob);
                this.mob.getMoveControl().setWantedPosition(vec3d.x, vec3d.y, vec3d.z, this.speedModifier);
            }
        }
    }

    @Override
    public Vec3 getEntityPosAtNode(int nodeIndex) {
        return Vec3.atBottomCenterOf(this.getPath().getNodePos(nodeIndex));
    }

    @Override
    protected void followThePath() {
        final Vec3 pos = this.getTempMobPos();
        final int shortcutNodeIndex = this.getClosestVerticalTraversal(Mth.floor(pos.y));
        this.maxDistanceToWaypoint = this.mob.getBbWidth() > 0.75f ? this.mob.getBbWidth() / 2f : 0.75f - this.mob.getBbWidth() / 2f;

        // temporarily set the mob since it gets called in 1.20.1 in FlyNodeEvaluator.getBlockPathType() if the pathtype is equal to fence
        this.nodeEvaluator.mob = this.mob;
        if(!this.attemptShortcut(shortcutNodeIndex, pos)) {
            this.nodeEvaluator.mob = null;
            if(this.isCloseToNextNode(NODE_REACH_RADIUS)) {
                int nextNodeIndex = this.path.getNextNodeIndex() + NODE_DISTANCE;
                if(this.path.getNextNodeIndex() < this.path.getNodeCount() - 1 && nextNodeIndex >= this.path.getNodeCount()) {
                    this.path.setNextNodeIndex(this.path.getNodeCount() - 1);
                }
                else {
                    this.path.setNextNodeIndex(nextNodeIndex);
                }
            }
        }

        this.doStuckDetection(pos);
    }

    @Override
    public boolean isCloseToNextNode(float distance) {
        final Vec3 nextNodePos = this.getEntityPosAtNode(this.getPath().getNextNodeIndex());

        if(this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()
            && Birds.shouldLandAtDestination((FlyingBirdEntity) this.mob, this.getTargetPos())
        ) {
            return this.getTempMobPos().closerThan(nextNodePos, 0.5);
        }
        return this.getTempMobPos().closerThan(nextNodePos, distance);
    }

    protected int getClosestVerticalTraversal(int safeSurfaceHeight) {
        final int nodesLength = this.path.getNodeCount();

        for(int nodeIndex = this.path.getNextNodeIndex(); nodeIndex < nodesLength; nodeIndex++) {
            if(this.path.getNode(nodeIndex).y != safeSurfaceHeight) {
                return nodeIndex;
            }
        }

        return nodesLength;
    }

    @Override
    public float getMaxDistanceToWaypoint() {
        return NODE_REACH_RADIUS;
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return true;
    }
}
