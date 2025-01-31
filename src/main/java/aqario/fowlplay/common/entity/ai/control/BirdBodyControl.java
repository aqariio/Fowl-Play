package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class BirdBodyControl extends BodyControl {
    private final BirdEntity entity;
    private float lastHeadYaw;

    public BirdBodyControl(BirdEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (this.isMoving()) {
            this.entity.bodyYaw = this.entity.getYaw();
            this.keepUpHead();
            this.lastHeadYaw = this.entity.headYaw;
        }
        else {
            if (this.isIndependent()) {
                if (Math.abs(this.entity.headYaw - this.lastHeadYaw) > 15.0F) {
                    this.lastHeadYaw = this.entity.headYaw;
                    this.keepUpBody();
                }
            }
        }
    }

    private void keepUpBody() {
        this.entity.bodyYaw = MathHelper.clampAngle(this.entity.bodyYaw, this.entity.headYaw, (float) this.entity.getMaxHeadRotation());
    }

    private void keepUpHead() {
        this.entity.headYaw = MathHelper.clampAngle(this.entity.headYaw, this.entity.bodyYaw, (float) this.entity.getMaxHeadRotation());
    }

    private boolean isIndependent() {
        return !(this.entity.getFirstPassenger() instanceof MobEntity);
    }

    private boolean isMoving() {
        double d = this.entity.getX() - this.entity.prevX;
        double e = this.entity.getZ() - this.entity.prevZ;
        return d * d + e * e > 2.5000003E-7F;
    }
}
