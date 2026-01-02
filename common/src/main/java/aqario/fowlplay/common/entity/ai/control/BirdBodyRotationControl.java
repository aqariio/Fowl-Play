package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class BirdBodyRotationControl extends BodyRotationControl {
    private final BirdEntity entity;
    private static final int BODY_KEEP_UP_THRESHOLD = 15;
    private float lastHeadYaw;
    private int bodyAdjustTicks;

    public BirdBodyRotationControl(BirdEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void clientTick() {
        if (this.isMoving()) {
            this.entity.yBodyRot = this.entity.getYRot();
            this.keepUpHead();
            this.lastHeadYaw = this.entity.yHeadRot;
        }
        else if (this.isIndependent()) {
//            if (Math.abs(this.entity.headYaw - this.lastHeadYaw) > BODY_KEEP_UP_THRESHOLD) {
//                this.lastHeadYaw = this.entity.headYaw;
//                this.keepUpBody();
//            }
        }
    }

    private void keepUpBody() {
        this.entity.yBodyRot = Mth.rotateIfNecessary(this.entity.yBodyRot, this.entity.yHeadRot, (float) this.entity.getMaxHeadYRot());
    }

    private void keepUpHead() {
        this.entity.yHeadRot = Mth.rotateIfNecessary(this.entity.yHeadRot, this.entity.yBodyRot, (float) this.entity.getMaxHeadYRot());
    }

    private boolean isIndependent() {
        return !(this.entity.getFirstPassenger() instanceof Mob);
    }

    private boolean isMoving() {
        double d = this.entity.getX() - this.entity.xo;
        double e = this.entity.getZ() - this.entity.zo;
        return d * d + e * e > 2.5000003E-7F;
    }
}
