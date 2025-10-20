package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.util.Birds;
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
    protected final BirdEntity bird;
    private static final double DECELERATE_DISTANCE = 3.0;

    public BirdMoveControl(BirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    @Override
    public void tick() {
        if(this.bird instanceof FlyingBirdEntity flyingBird && flyingBird.isFlying()) {
            this.tickFlying();
        }
        else {
//            this.tickWalking();
            super.tick();
        }
    }

    private void tickFlying() {
        this.state = State.MOVE_TO;
        FlyingBirdEntity bird = (FlyingBirdEntity) this.bird;

        // distance to target
        Vec3d distance = new Vec3d(this.targetX - bird.getX(), this.targetY - bird.getY(), this.targetZ - bird.getZ());
        double squaredDistance = distance.lengthSquared();
        if(squaredDistance < 2.5000003E-7F) {
            bird.setForwardSpeed(0.0F);
            return;
        }

        // yaw
        float yaw = (float) (MathHelper.atan2(distance.z, distance.x) * 180.0F / (float) Math.PI) - 90.0F;
        bird.setYaw(this.wrapDegrees(bird.getYaw(), yaw, /*bird.getMaxYawChange()*/25));
        bird.bodyYaw = bird.getYaw();
        bird.headYaw = bird.getYaw();

        // speed
        float speed = (float) bird.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED) * Birds.FLY_SPEED;
        BlockPos destination;
        // decelerate when landing
        if((destination = bird.getNavigation().getTargetPos()) != null
            && Birds.shouldLandAtDestination(bird, destination)
        ) {
            double dist = bird.squaredDistanceTo(Vec3d.ofBottomCenter(destination));
            if(dist < DECELERATE_DISTANCE * DECELERATE_DISTANCE) {
                speed *= (float) decelerate(dist);
            }
        }
        bird.setMovementSpeed(speed);
        double horizontalDistance = Math.sqrt(distance.x * distance.x + distance.z * distance.z);

        // pitch
        if(Math.abs(distance.y) > 1.0E-5F || Math.abs(horizontalDistance) > 1.0E-5F) {
            float pitch = -(float) (MathHelper.atan2(distance.y, horizontalDistance) * 180.0F / Math.PI);
            pitch = MathHelper.clamp(MathHelper.wrapDegrees(pitch), -bird.getMaxLookPitchChange(), bird.getMaxLookPitchChange());
            bird.setPitch(this.wrapDegrees(bird.getPitch(), pitch, /*bird.getMaxPitchChange()*/25));
        }

        // pitch to movement
        float x = MathHelper.cos(bird.getPitch() * (float) (Math.PI / 180.0));
        float y = MathHelper.sin(bird.getPitch() * (float) (Math.PI / 180.0));
        bird.forwardSpeed = x * speed;
        bird.upwardSpeed = -y * speed;
    }

    private static double decelerate(double x) {
        return Math.max(1 / (DECELERATE_DISTANCE * DECELERATE_DISTANCE) * x, 0.25);
    }

    private void tickWalking() {
        if(this.state == State.MOVE_TO) {
            this.state = State.WAIT;
            Vec3d distance = new Vec3d(this.targetX - this.bird.getX(), this.targetY - this.bird.getY(), this.targetZ - this.bird.getZ());
            if(distance.lengthSquared() < 2.5000003E-7F) {
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
            if(distance.y > (double) this.bird.getStepHeight() && horizontalSqDistance < (double) Math.max(1.0F, this.bird.getWidth())
                || !collisionShape.isEmpty()
                && this.bird.getY() < collisionShape.getMax(Direction.Axis.Y) + (double) pos.getY()
                && !state.isIn(BlockTags.DOORS)
                && !state.isIn(BlockTags.FENCES)) {
                this.bird.getJumpControl().setActive();
                this.state = State.JUMPING;
            }
            if(distance.y < (double) this.bird.getStepHeight() && horizontalSqDistance < (double) Math.max(1.0F, this.bird.getWidth())
                || !collisionShape.isEmpty()
                && this.bird.getY() > collisionShape.getMax(Direction.Axis.Y) + (double) pos.getY()
                && !state.isIn(BlockTags.DOORS)
                && !state.isIn(BlockTags.FENCES)) {
                this.bird.setSneaking(true);
            }
        }
        else if(this.state == State.JUMPING) {
            this.bird.setMovementSpeed((float) (this.speed * this.bird.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            if(this.bird.isOnGround()) {
                this.state = State.WAIT;
            }
        }
        else if(this.state == State.STRAFE) {
            this.state = State.WAIT;
        }
        else {
            this.bird.setForwardSpeed(0.0F);
        }
    }
}
