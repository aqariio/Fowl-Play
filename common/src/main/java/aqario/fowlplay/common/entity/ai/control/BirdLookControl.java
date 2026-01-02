package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class BirdLookControl extends LookControl {
    private final int maxYawDifference;

    public BirdLookControl(Mob entity, int maxYawDifference) {
        super(entity);
        this.maxYawDifference = maxYawDifference;
    }

    @Override
    public void setLookAt(double x, double y, double z, float yawSpeed, float pitchSpeed) {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        this.yMaxRotSpeed = yawSpeed;
        this.xMaxRotAngle = pitchSpeed;
        this.lookAtCooldown = Mth.randomBetweenInclusive(this.mob.getRandom(), 2, 8);
    }

    @Override
    public void tick() {
        if(this.mob instanceof FlyingBirdEntity bird && bird.isFlying()) {
            this.tickFlying();
        }
        else {
            this.tickOnGround();
        }
    }

    private void tickFlying() {
//        if(this.lookAtCooldown > 0) {
//            this.lookAtCooldown--;
//            this.getYRotD().ifPresent(yaw -> this.mob.setYHeadRot(this.rotateTowards(this.mob.getYHeadRot(), yaw, this.yMaxRotSpeed)));
//            this.getXRotD().ifPresent(pitch -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), pitch, this.xMaxRotAngle)));
//        }
//        else {
//            if(this.mob.getNavigation().isDone()) {
//                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
//            }
//            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
//        }
        this.mob.yBodyRot = this.mob.yHeadRot;
    }

    private void tickOnGround() {
        if(this.lookAtCooldown > 0) {
            this.lookAtCooldown--;
            this.getYRotD().ifPresent(yaw -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.calculateYaw(this.mob.yHeadRot, yaw), this.yMaxRotSpeed));
            this.getXRotD().ifPresent(pitch -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), pitch, this.xMaxRotAngle)));
        }
        else {
            if(this.mob.getNavigation().isDone()) {
                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
            }
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
        }

        float yawDif = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
        if(yawDif < (float) (-this.maxYawDifference)) {
            this.mob.yBodyRot -= 4.0F;
        }
        else if(yawDif > (float) this.maxYawDifference) {
            this.mob.yBodyRot += 4.0F;
        }
    }

    private float calculateYaw(float curYaw, float targetYaw) {
        float plus60 = Mth.wrapDegrees(targetYaw + 60.0F);
        float minus60 = Mth.wrapDegrees(targetYaw - 60.0F);

        float diffPlus = Math.abs(Mth.wrapDegrees(plus60 - curYaw));
        float diffMinus = Math.abs(Mth.wrapDegrees(minus60 - curYaw));

        return diffPlus < diffMinus ? plus60 : minus60;
    }
}
