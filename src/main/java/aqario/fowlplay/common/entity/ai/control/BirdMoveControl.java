package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
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

    @Override
    public void tick() {
        if (this.bird instanceof FlyingBirdEntity flyingBird && flyingBird.isFlying()) {
            this.tickFlying();
        }
        else {
//            this.tickWalking();
            super.tick();
        }
    }

    private void tickWalking() {
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
            BlockPos pos = this.bird.getBlockPos();
            BlockState state = this.bird.getWorld().getBlockState(pos);
            VoxelShape collisionShape = state.getCollisionShape(this.bird.getWorld(), pos);
            double horizontalSqDistance = distance.x * distance.x + distance.z * distance.z;
            if (distance.y > (double) this.bird.getStepHeight() && horizontalSqDistance < (double) Math.max(1.0F, this.bird.getWidth())
                || !collisionShape.isEmpty()
                && this.bird.getY() < collisionShape.getMax(Direction.Axis.Y) + (double) pos.getY()
                && !state.isIn(BlockTags.DOORS)
                && !state.isIn(BlockTags.FENCES)) {
                this.bird.getJumpControl().setActive();
                this.state = State.JUMPING;
            }
            if (distance.y < (double) this.bird.getStepHeight() && horizontalSqDistance < (double) Math.max(1.0F, this.bird.getWidth())
                || !collisionShape.isEmpty()
                && this.bird.getY() > collisionShape.getMax(Direction.Axis.Y) + (double) pos.getY()
                && !state.isIn(BlockTags.DOORS)
                && !state.isIn(BlockTags.FENCES)) {
                this.bird.setSneaking(true);
            }
        }
        else if (this.state == State.JUMPING) {
            this.bird.setMovementSpeed((float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            if (this.bird.isOnGround()) {
                this.state = State.WAIT;
            }
        }
        else if (this.state == State.STRAFE) {
            this.state = State.WAIT;
        }
        else {
            this.bird.setForwardSpeed(0.0F);
        }
    }

    private void tickFlying() {
        FlyingBirdEntity flyingBird = (FlyingBirdEntity) this.bird;
        this.state = State.MOVE_TO;

        // distance to target
        Vec3d distance = new Vec3d(this.targetX - flyingBird.getX(), this.targetY - flyingBird.getY(), this.targetZ - flyingBird.getZ());
        double squaredDistance = distance.lengthSquared();
        if (squaredDistance < 2.5000003E-7F) {
            flyingBird.setForwardSpeed(0.0F);
            return;
        }

        // yaw
        float yaw = (float) (MathHelper.atan2(distance.z, distance.x) * 180.0F / (float) Math.PI) - 90.0F;
        flyingBird.setYaw(this.wrapDegrees(flyingBird.getYaw(), yaw, flyingBird.getMaxYawChange()));
        flyingBird.bodyYaw = flyingBird.getYaw();
        flyingBird.headYaw = flyingBird.getYaw();

        // speed
        float speed = (float) (this.speed * flyingBird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
        flyingBird.setMovementSpeed(speed);
        double horizontalDistance = Math.sqrt(distance.x * distance.x + distance.z * distance.z);

        // pitch
        if (Math.abs(distance.y) > 1.0E-5F || Math.abs(horizontalDistance) > 1.0E-5F) {
            float pitch = -(float) (MathHelper.atan2(distance.y, horizontalDistance) * 180.0F / Math.PI);
            pitch = MathHelper.clamp(MathHelper.wrapDegrees(pitch), -flyingBird.getMaxLookPitchChange(), flyingBird.getMaxLookPitchChange());
            flyingBird.setPitch(this.wrapDegrees(flyingBird.getPitch(), pitch, flyingBird.getMaxPitchChange()));
        }

        // pitch to movement
        float x = MathHelper.cos(flyingBird.getPitch() * (float) (Math.PI / 180.0));
        float y = MathHelper.sin(flyingBird.getPitch() * (float) (Math.PI / 180.0));
        flyingBird.forwardSpeed = x * speed;
        flyingBird.upwardSpeed = -y * speed;
    }
}
