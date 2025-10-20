package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCache;
import net.tslat.smartbrainlib.api.core.navigation.ExtendedNavigator;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class FlightNavigation extends MobNavigation implements ExtendedNavigator {
    private static final int NODE_DISTANCE = 3;
    private static final float NODE_REACH_RADIUS = 2;
    private final FlyingBirdEntity bird;

    public FlightNavigation(FlyingBirdEntity bird, World world) {
        super(bird, world);
        this.bird = bird;
    }

    @Override
    public MobEntity getMob() {
        return this.bird;
    }

    @Nullable
    @Override
    public Path getCurrentPath() {
        return super.getCurrentPath();
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int maxVisitedNodes) {
        this.nodeMaker = new BirdPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);

        return new PathNodeNavigator(this.nodeMaker, maxVisitedNodes) {
            @Nullable
            @Override
            public Path findPathToAny(ChunkCache navigationRegion, MobEntity mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
                final Path path = super.findPathToAny(navigationRegion, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);

                return FlightNavigation.this.patchPath(path);
            }
        };
    }

    public @Nullable Path patchPath(@Nullable Path path) {
        if(path == null) {
            return null;
        }

        Path newPath = new Path(path.nodes, path.getTarget(), path.reachesTarget()) {
            @Override
            public Vec3d getNodePosition(Entity entity1, int nodeIndex) {
                return FlightNavigation.this.getEntityPosAtNode(nodeIndex);
            }
        };
        PathNode[] debugNodes = path.getDebugNodes();
        PathNode[] debugSecondNodes = path.getDebugSecondNodes();
        Set<TargetPathNode> debugTargetNodes = path.debugTargetNodes;
        newPath.setDebugInfo(debugNodes, debugSecondNodes, debugTargetNodes);

        return newPath;
    }

    @Override
    public boolean startMovingTo(double x, double y, double z, double speed) {
        this.bird.getMoveControl().moveTo(x, y, z, speed);
        return true;
    }

    @Override
    public boolean startMovingTo(Entity entity, double speed) {
        this.bird.getMoveControl().moveTo(entity.getX(), entity.getY(), entity.getZ(), speed);
        return true;
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return doesNotCollide(this.bird, origin, target, true);
    }

    @Override
    protected boolean isAtValidPosition() {
        return this.canSwim() && this.isInLiquid() || !this.bird.hasVehicle();
    }

    @Override
    protected Vec3d getPos() {
        return this.getMob().getPos();
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        return pos.y;
    }

    @Nullable
    public Path findPathTo(BlockPos target, int distance) {
        return this.findPathTo(ImmutableSet.of(target), 48, false, distance);
    }

    @Override
    public void tick() {
        this.tickCount++;
        if(this.inRecalculationCooldown) {
            this.recalculatePath();
        }

        if(!this.isIdle()) {
            if(this.isAtValidPosition()) {
                this.continueFollowingPath();
            }
            else if(this.currentPath != null && !this.currentPath.isFinished()) {
                Vec3d pos = this.getPos();
                Vec3d nodePos = this.currentPath.getNodePosition(this.bird);
                if(pos.y > nodePos.y
                    && !this.bird.isOnGround()
                    && MathHelper.floor(pos.x) == MathHelper.floor(nodePos.x)
                    && MathHelper.floor(pos.z) == MathHelper.floor(nodePos.z)) {
                    this.currentPath.next();
                }
            }
            if(this.currentPath != null
                && this.currentPath.isFinished()
                && this.getTargetPos() != null
                && Birds.shouldLandAtDestination(this.bird, this.getTargetPos())
            ) {
                this.bird.stopFlying();
            }

            DebugInfoSender.sendPathfindingData(this.world, this.getMob(), this.getCurrentPath(), 0.1f);
            if(!this.isIdle()) {
                // noinspection ConstantConditions
                Vec3d vec3d = this.currentPath.getNodePosition(this.bird);
                this.bird.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
            }
        }
    }

    @Override
    public Vec3d getEntityPosAtNode(int nodeIndex) {
        MobEntity mob = this.getMob();
        Path path = this.getCurrentPath();
        double lateralOffset = MathHelper.floor(mob.getWidth() + 1.0F) / 2.0F;
        return Vec3d.of(path.getNodePos(nodeIndex)).add(lateralOffset, 0.5F, lateralOffset);
    }

    @Override
    protected void continueFollowingPath() {
        final Vec3d safeSurfacePos = this.getPos();
        final int shortcutNode = this.getClosestVerticalTraversal(MathHelper.floor(safeSurfacePos.y));
        this.nodeReachProximity = this.bird.getWidth() > 0.75f ? this.bird.getWidth() / 2f : 0.75f - this.bird.getWidth() / 2f;

//        if (!this.attemptShortcut(shortcutNode, safeSurfacePos)) {
        if(this.isCloseToNextNode(NODE_REACH_RADIUS)/* || this.isAboutToTraverseVertically() && this.isCloseToNextNode(this.getNodeReachProximity())*/) {
            int nextNodeIndex = this.currentPath.getCurrentNodeIndex() + NODE_DISTANCE;
            if(this.currentPath.getCurrentNodeIndex() < this.currentPath.getLength() - 1 && nextNodeIndex >= this.currentPath.getLength()) {
                this.currentPath.setCurrentNodeIndex(this.currentPath.getLength() - 1);
            }
            else {
                this.currentPath.setCurrentNodeIndex(nextNodeIndex);
            }
        }
//        }

        this.checkTimeouts(safeSurfacePos);
    }

    @Override
    public boolean isCloseToNextNode(float distance) {
        final Vec3d nextNodePos = this.getEntityPosAtNode(this.getCurrentPath().getCurrentNodeIndex());

        if(this.currentPath.getCurrentNodeIndex() + 1 >= this.currentPath.getLength()) {
            return this.getPos().isInRange(nextNodePos, 0.5);
        }
        return this.getPos().isInRange(nextNodePos, distance);
    }

    protected int getClosestVerticalTraversal(int safeSurfaceHeight) {
        final int nodesLength = this.currentPath.getLength();

        for(int nodeIndex = this.currentPath.getCurrentNodeIndex(); nodeIndex < nodesLength; nodeIndex++) {
            if(this.currentPath.getNode(nodeIndex).y != safeSurfaceHeight) {
                return nodeIndex;
            }
        }

        return nodesLength;
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return true;
    }
}
