package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class BirdLookControl extends LookControl {
    private final int maxYawDifference;

    public BirdLookControl(MobEntity entity, int maxYawDifference) {
        super(entity);
        this.maxYawDifference = maxYawDifference;
    }

    @Override
    public void lookAt(double x, double y, double z, float yawSpeed, float pitchSpeed) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxYawChange = yawSpeed;
        this.maxPitchChange = pitchSpeed;
        this.lookAtTimer = MathHelper.nextBetween(this.entity.getRandom(), 2, 8);
    }

    @Override
    public void tick() {
        if(this.entity instanceof FlyingBirdEntity bird && bird.isFlying()) {
            this.tickFlying();
        }
        else {
            this.tickOnGround();
        }
    }

    private void tickFlying() {
        if(this.lookAtTimer > 0) {
            this.lookAtTimer--;
            this.getTargetYaw().ifPresent(yaw -> this.entity.setHeadYaw(this.changeAngle(this.entity.getHeadYaw(), yaw, this.maxYawChange)));
            this.getTargetPitch().ifPresent(pitch -> this.entity.setPitch(this.changeAngle(this.entity.getPitch(), pitch, this.maxPitchChange)));
        }
        else {
            if(this.entity.getNavigation().isIdle()) {
                this.entity.setPitch(this.changeAngle(this.entity.getPitch(), 0.0F, 5.0F));
            }
            this.entity.headYaw = this.changeAngle(this.entity.headYaw, this.entity.bodyYaw, this.maxYawChange);
        }
        this.entity.bodyYaw = this.entity.headYaw;
    }

    private void tickOnGround() {
        if(this.lookAtTimer > 0) {
            this.lookAtTimer--;
            this.getTargetYaw().ifPresent(yaw -> this.entity.headYaw = this.changeAngle(this.entity.headYaw, this.calculateYaw(this.entity.headYaw, yaw), this.maxYawChange));
            this.getTargetPitch().ifPresent(pitch -> this.entity.setPitch(this.changeAngle(this.entity.getPitch(), pitch, this.maxPitchChange)));
        }
        else {
            if(this.entity.getNavigation().isIdle()) {
                this.entity.setPitch(this.changeAngle(this.entity.getPitch(), 0.0F, 5.0F));
            }
            this.entity.headYaw = this.changeAngle(this.entity.headYaw, this.entity.bodyYaw, this.maxYawChange);
        }

        float yawDif = MathHelper.wrapDegrees(this.entity.headYaw - this.entity.bodyYaw);
        if(yawDif < (float) (-this.maxYawDifference)) {
            this.entity.bodyYaw -= 4.0F;
        }
        else if(yawDif > (float) this.maxYawDifference) {
            this.entity.bodyYaw += 4.0F;
        }
    }

    private float calculateYaw(float curYaw, float targetYaw) {
        float plus60 = MathHelper.wrapDegrees(targetYaw + 60.0F);
        float minus60 = MathHelper.wrapDegrees(targetYaw - 60.0F);

        float diffPlus = Math.abs(MathHelper.wrapDegrees(plus60 - curYaw));
        float diffMinus = Math.abs(MathHelper.wrapDegrees(minus60 - curYaw));

        return diffPlus < diffMinus ? plus60 : minus60;
    }
}
