package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class BirdMoveControl extends MoveControl {
    private final BirdEntity bird;

    public BirdMoveControl(BirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    public void tick() {
        if (this.state == State.MOVE_TO) {
            this.state = State.WAIT;
            Vec3d distance = new Vec3d(this.targetX - this.bird.getX(), this.targetY - this.bird.getY(), this.targetZ - this.bird.getZ());
            if (distance.lengthSquared() < 2.5000003E-7F) {
                this.bird.setForwardSpeed(0.0F);
                this.state = State.WAIT;
                return;
            }

            float angle = (float) (MathHelper.atan2(distance.z, distance.x) * 180.0F / (float) Math.PI) - 90.0F;
            this.bird.setYaw(this.wrapDegrees(this.bird.getYaw(), angle, 15.0F));
            this.bird.setMovementSpeed((float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            BlockPos blockPos = this.bird.getBlockPos();
            BlockState blockState = this.bird.getWorld().getBlockState(blockPos);
            VoxelShape voxelShape = blockState.getCollisionShape(this.bird.getWorld(), blockPos);
            if (distance.y > (double) this.bird.getStepHeight() && distance.x * distance.x + distance.z * distance.z < (double) Math.max(1.0F, this.bird.getWidth())
                || !voxelShape.isEmpty()
                && this.bird.getY() < voxelShape.getMax(Direction.Axis.Y) + (double) blockPos.getY()
                && !blockState.isIn(BlockTags.DOORS)
                && !blockState.isIn(BlockTags.FENCES)) {
                this.bird.getJumpControl().setActive();
                this.state = MoveControl.State.JUMPING;
            }
            if (distance.y < (double) this.bird.getStepHeight() && distance.x * distance.x + distance.z * distance.z < (double) Math.max(1.0F, this.bird.getWidth())
                || !voxelShape.isEmpty()
                && this.bird.getY() > voxelShape.getMax(Direction.Axis.Y) + (double) blockPos.getY()
                && !blockState.isIn(BlockTags.DOORS)
                && !blockState.isIn(BlockTags.FENCES)) {
                this.bird.setSneaking(true);
            }
        }
        else if (this.state == MoveControl.State.JUMPING) {
            this.bird.setMovementSpeed((float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            if (this.bird.isOnGround()) {
                this.state = MoveControl.State.WAIT;
            }
        }
        else if (this.state == MoveControl.State.STRAFE) {
            this.state = State.WAIT;
        }
        else {
            this.bird.setForwardSpeed(0.0F);
        }
    }
}
