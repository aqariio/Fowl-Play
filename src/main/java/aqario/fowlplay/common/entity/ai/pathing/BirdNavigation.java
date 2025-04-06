package aqario.fowlplay.common.entity.ai.pathing;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.BirdPathNodeMaker;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BirdNavigation extends MobNavigation {
    private final BirdEntity bird;

    public BirdNavigation(BirdEntity bird, World world) {
        super(bird, world);
        this.bird = bird;
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new BirdPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range);
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
        return doesNotCollide(this.entity, origin, target, true);
    }

    @Override
    protected boolean isAtValidPosition() {
        return this.canSwim() && this.entity.isInsideWaterOrBubbleColumn() || !this.entity.hasVehicle();
    }

    @Override
    protected Vec3d getPos() {
        return this.entity.getPos();
    }

    @Override
    protected double adjustTargetY(Vec3d pos) {
        return pos.y;
    }

    @Nullable
    public Path findPathTo(BlockPos target, int distance) {
        return this.findPathTo(ImmutableSet.of(target), 32, false, distance);
    }

    @Override
    public void tick() {
        this.tickCount++;
        if (this.inRecalculationCooldown) {
            this.recalculatePath();
        }

        if (!this.isIdle()) {
            if (this.isAtValidPosition()) {
                this.continueFollowingPath();
            }
            else if (this.currentPath != null && !this.currentPath.isFinished()) {
                Vec3d vec3d = this.currentPath.getNodePosition(this.entity);
                if (this.entity.getBlockX() == MathHelper.floor(vec3d.x)
                    && this.entity.getBlockY() == MathHelper.floor(vec3d.y)
                    && this.entity.getBlockZ() == MathHelper.floor(vec3d.z)) {
                    this.currentPath.next();
                }
            }

            DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
            if (!this.isIdle()) {
                Vec3d vec3d = this.currentPath.getNodePosition(this.entity);
                this.entity.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
            }
        }
    }

    @Override
    protected void continueFollowingPath() {
        Vec3d pos = this.getPos();
        this.nodeReachProximity = 4;
        Vec3i vec3i = this.currentPath.getCurrentNodePos();
        double x = Math.abs(this.bird.getX() - ((double) vec3i.getX() + 0.5));
        double y = Math.abs(this.bird.getY() - (double) vec3i.getY() + 0.5);
        double z = Math.abs(this.bird.getZ() - ((double) vec3i.getZ() + 0.5));
        boolean bl = x < this.nodeReachProximity && z < this.nodeReachProximity && y < this.nodeReachProximity;
        if (bl || this.canJumpToNext(this.currentPath.getCurrentNode().type) && this.shouldJumpToNextNode(pos)) {
            this.currentPath.next();
        }

        this.checkTimeouts(pos);
    }

    private boolean shouldJumpToNextNode(Vec3d currentPos) {
        if (this.currentPath.getCurrentNodeIndex() + 1 >= this.currentPath.getLength()) {
            return false;
        }
        Vec3d vec3d = Vec3d.ofCenter(this.currentPath.getCurrentNodePos());
        if (!currentPos.isInRange(vec3d, 4)) {
            return false;
        }
        Vec3d vec3d1 = Vec3d.ofCenter(this.currentPath.getNodePos(this.currentPath.getCurrentNodeIndex() + 1));
        Vec3d vec3d2 = vec3d1.subtract(vec3d);
        Vec3d vec3d3 = currentPos.subtract(vec3d);
        return vec3d2.dotProduct(vec3d3) > 0.0;

    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return true;
    }
}
