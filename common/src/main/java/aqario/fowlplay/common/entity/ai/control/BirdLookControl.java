package aqario.fowlplay.common.entity.ai.control;

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
        this.lookX = x;
        this.lookY = y;
        this.lookZ = z;
        this.yawSpeed = yawSpeed;
        this.pitchSpeed = pitchSpeed;
        this.lookAtCooldown = Math.max(2, this.entity.getRandom().nextInt(8));
    }

    @Override
    public void tick() {
        if (this.lookAtCooldown > 0) {
            this.lookAtCooldown--;
            this.getTargetYaw().ifPresent(yaw -> this.entity.headYaw = this.changeAngle(this.entity.headYaw, yaw + 20.0F, this.yawSpeed));
            this.getTargetPitch().ifPresent(pitch -> this.entity.setPitch(this.changeAngle(this.entity.getPitch(), pitch + 10.0F, this.pitchSpeed)));
        }
        else {
            if (this.entity.getNavigation().isIdle()) {
                this.entity.setPitch(this.changeAngle(this.entity.getPitch(), 0.0F, 5.0F));
            }

            this.entity.headYaw = this.changeAngle(this.entity.headYaw, this.entity.bodyYaw, this.yawSpeed);
        }

        float f = MathHelper.wrapDegrees(this.entity.headYaw - this.entity.bodyYaw);
        if (f < (float) (-this.maxYawDifference)) {
            this.entity.bodyYaw -= 4.0F;
        }
        else if (f > (float) this.maxYawDifference) {
            this.entity.bodyYaw += 4.0F;
        }
    }
}
