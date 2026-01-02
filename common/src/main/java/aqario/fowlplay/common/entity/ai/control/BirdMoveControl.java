package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

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
            this.tickFlying(flyingBird);
        }
        else {
//            this.tickWalking();
            super.tick();
        }
    }

    private void tickFlying(FlyingBirdEntity bird) {
        this.operation = Operation.MOVE_TO;

        // vector pointing to target
        Vec3 distance = new Vec3(this.wantedX - bird.getX(), this.wantedY - bird.getY(), this.wantedZ - bird.getZ());
        if(distance.lengthSqr() < 2.5000003E-7F) {
            bird.setZza(0.0F);
            return;
        }

        // yaw
        float yaw = (float) (Mth.atan2(distance.z, distance.x) * 180.0F / (float) Math.PI) - 90.0F;
        bird.setYRot(this.rotlerp(bird.getYRot(), yaw, /*bird.getMaxYawChange()*/25));
        bird.yBodyRot = bird.getYRot();
        bird.yHeadRot = bird.getYRot();

        // speed
        float speed = (float) bird.getAttributeValue(Attributes.FLYING_SPEED) * BirdUtils.FLY_SPEED;
        BlockPos destination;
        // decelerate when landing
        if((destination = bird.getNavigation().getTargetPos()) != null
            && BirdUtils.shouldLandAtDestination(bird, destination)
        ) {
            double dist = bird.distanceToSqr(Vec3.atBottomCenterOf(destination));
            if(dist < DECELERATE_DISTANCE * DECELERATE_DISTANCE) {
                speed *= (float) decelerate(dist);
            }
        }
        bird.setSpeed(speed);
        double lateralDistance = distance.horizontalDistance();

        // pitch
        if(Math.abs(distance.length()) > 1.0E-5F) {
            float pitch = -(float) (Mth.atan2(distance.y, lateralDistance) * 180.0F / Math.PI);
            pitch = Mth.clamp(Mth.wrapDegrees(pitch), -bird.getMaxHeadXRot(), bird.getMaxHeadXRot());
            bird.setXRot(this.rotlerp(bird.getXRot(), pitch, /*bird.getMaxPitchChange()*/25));
        }

        // pitch to movement
        float x = Mth.cos(bird.getXRot() * (float) (Math.PI / 180.0));
        float y = Mth.sin(bird.getXRot() * (float) (Math.PI / 180.0));
        bird.zza = x * speed;
        bird.yya = -y * speed;
    }

    private static double decelerate(double x) {
        return Math.max(1 / (DECELERATE_DISTANCE * DECELERATE_DISTANCE) * x, 0.25);
    }

    private void tickWalking() {
        if(this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            Vec3 distance = new Vec3(this.wantedX - this.bird.getX(), this.wantedY - this.bird.getY(), this.wantedZ - this.bird.getZ());
            if(distance.lengthSqr() < 2.5000003E-7F) {
                this.bird.setZza(0.0F);
                this.operation = Operation.WAIT;
                return;
            }
            float angle = (float) (Mth.atan2(distance.z, distance.x) * 180.0F / (float) Math.PI) - 90.0F;
            this.bird.setYRot(this.rotlerp(this.bird.getYRot(), angle, 15.0F));
            this.bird.setSpeed((float) (this.speedModifier * this.bird.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            BlockPos pos = this.bird.blockPosition();
            BlockState state = this.bird.level().getBlockState(pos);
            VoxelShape collisionShape = state.getCollisionShape(this.bird.level(), pos);
            double horizontalSqDistance = distance.x * distance.x + distance.z * distance.z;
            if(distance.y > (double) this.bird.maxUpStep() && horizontalSqDistance < (double) Math.max(1.0F, this.bird.getBbWidth())
                || !collisionShape.isEmpty()
                && this.bird.getY() < collisionShape.max(Direction.Axis.Y) + (double) pos.getY()
                && !state.is(BlockTags.DOORS)
                && !state.is(BlockTags.FENCES)) {
                this.bird.getJumpControl().jump();
                this.operation = Operation.JUMPING;
            }
            if(distance.y < (double) this.bird.maxUpStep() && horizontalSqDistance < (double) Math.max(1.0F, this.bird.getBbWidth())
                || !collisionShape.isEmpty()
                && this.bird.getY() > collisionShape.max(Direction.Axis.Y) + (double) pos.getY()
                && !state.is(BlockTags.DOORS)
                && !state.is(BlockTags.FENCES)) {
                this.bird.setShiftKeyDown(true);
            }
        }
        else if(this.operation == Operation.JUMPING) {
            this.bird.setSpeed((float) (this.speedModifier * this.bird.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if(this.bird.onGround()) {
                this.operation = Operation.WAIT;
            }
        }
        else if(this.operation == Operation.STRAFE) {
            this.operation = Operation.WAIT;
        }
        else {
            this.bird.setZza(0.0F);
        }
    }
}
