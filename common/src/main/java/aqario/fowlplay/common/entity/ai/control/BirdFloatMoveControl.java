package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.world.phys.Vec3;

public class BirdFloatMoveControl extends BirdMoveControl {
    public BirdFloatMoveControl(BirdEntity bird) {
        super(bird);
    }

    @Override
    public void tick() {
        if (this.bird.isBelowWaterline()) {
            Vec3 velocity = this.bird.getDeltaMovement();
            this.bird.setDeltaMovement(velocity.add(0.0, 0.05, 0.0));
            if (this.bird.isUnderWater()) {
                velocity = this.bird.getDeltaMovement();
                this.bird.setDeltaMovement(velocity.add(0.0, 0.1, 0.0));
            }
            velocity = this.bird.getDeltaMovement();
            this.bird.setDeltaMovement(velocity.x(), Math.max(velocity.y(), 0), velocity.z());
        }
        super.tick();
    }
}
