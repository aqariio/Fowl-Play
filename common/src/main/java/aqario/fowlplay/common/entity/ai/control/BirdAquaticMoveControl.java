package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class BirdAquaticMoveControl extends BirdMoveControl {
    private static final float field_40123 = 10.0F;
    private final int pitchChange;
    private final int yawChange;
    private final float speedInWater;
    private final float speedInAir;
    private final boolean buoyant;

    public BirdAquaticMoveControl(BirdEntity bird, int pitchChange, int yawChange, float speedInWater, float speedInAir, boolean buoyant) {
        super(bird);
        this.pitchChange = pitchChange;
        this.yawChange = yawChange;
        this.speedInWater = speedInWater;
        this.speedInAir = speedInAir;
        this.buoyant = buoyant;
    }

    @Override
    public void tick() {
        if(this.bird.isInWaterOrBubble() && !this.bird.isBaby()) {
            this.tickSwimming();
        }
        else {
            super.tick();
        }
    }

    private void tickSwimming() {
        if(this.buoyant && this.mob.isInWater()) {
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, 0.005, 0.0));
        }

        if(this.operation == MoveControl.Operation.MOVE_TO && !this.mob.getNavigation().isDone()) {
            double d = this.wantedX - this.mob.getX();
            double e = this.wantedY - this.mob.getY();
            double f = this.wantedZ - this.mob.getZ();
            double g = d * d + e * e + f * f;
            if(g < 2.5000003E-7F) {
                this.mob.setZza(0.0F);
            }
            else {
                float h = (float) (Mth.atan2(f, d) * 180.0F / (float) Math.PI) - 90.0F;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), h, this.yawChange));
                this.mob.yBodyRot = this.mob.getYRot();
                this.mob.yHeadRot = this.mob.getYRot();
                float speed = (float) (/*this.speed * */this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * BirdUtils.SWIM_SPEED);
                if(this.mob.isInWater()) {
                    this.mob.setSpeed(speed * this.speedInWater);
                    double j = Math.sqrt(d * d + f * f);
                    if(Math.abs(e) > 1.0E-5F || Math.abs(j) > 1.0E-5F) {
                        float k = -((float) (Mth.atan2(e, j) * 180.0F / (float) Math.PI));
                        k = Mth.clamp(Mth.wrapDegrees(k), (float) (-this.pitchChange), (float) this.pitchChange);
                        this.mob.setXRot(this.rotlerp(this.mob.getXRot(), k, 5.0F));
                    }

                    float k = Mth.cos(this.mob.getXRot() * (float) (Math.PI / 180.0));
                    float l = Mth.sin(this.mob.getXRot() * (float) (Math.PI / 180.0));
                    this.mob.zza = k * speed;
                    this.mob.yya = -l * speed;
                }
                else {
                    float m = Math.abs(Mth.wrapDegrees(this.mob.getYRot() - h));
                    float n = method_45335(m);
                    this.mob.setSpeed(speed * this.speedInAir * n);
                }
            }
        }
        else {
            this.mob.setSpeed(0.0F);
            this.mob.setXxa(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }

    private static float method_45335(float f) {
        return 1.0F - Mth.clamp((f - field_40123) / 50.0F, 0.0F, 1.0F);
    }
}
