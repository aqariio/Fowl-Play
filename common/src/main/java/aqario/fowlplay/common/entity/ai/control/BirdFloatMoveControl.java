package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;

public class BirdFloatMoveControl extends BirdMoveControl {
    public BirdFloatMoveControl(BirdEntity bird) {
        super(bird);
    }

    @Override
    public void tick() {
        if(this.bird.isWaterAboveFloatHeight()) {
//            Vec3 velocity = this.bird.getDeltaMovement();
//            double floatVelocity = this.bird.getGravity() / 16.0 + 0.07 * Math.pow(Mth.clamp(1 - this.bird.getEyeHeight() + this.bird.getBoundingBox().getYsize() * 0.5, 0, 1), 3);
//            System.out.println(Math.pow(Mth.clamp(1 - this.bird.getEyeHeight() + this.bird.getBoundingBox().getYsize() * 0.5, 0, 1), 3));
//            this.bird.setDeltaMovement(velocity.add(0.0, floatVelocity, 0.0));
//            if (this.bird.isUnderWater()) {
//                velocity = this.bird.getDeltaMovement();
//                this.bird.setDeltaMovement(velocity.add(0.0, 0.05, 0.0));
//            }
        }
        super.tick();
    }
}
