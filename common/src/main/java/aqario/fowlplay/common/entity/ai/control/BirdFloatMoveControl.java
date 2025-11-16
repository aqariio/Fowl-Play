package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.util.math.Vec3d;

public class BirdFloatMoveControl extends BirdMoveControl {
    public BirdFloatMoveControl(BirdEntity bird) {
        super(bird);
    }

    @Override
    public void tick() {
        if (this.bird.isBelowWaterline()) {
            Vec3d velocity = this.bird.getVelocity();
            this.bird.setVelocity(velocity.add(0.0, 0.05, 0.0));
            if (this.bird.isSubmergedInWater()) {
                velocity = this.bird.getVelocity();
                this.bird.setVelocity(velocity.add(0.0, 0.1, 0.0));
            }
            velocity = this.bird.getVelocity();
            this.bird.setVelocity(velocity.getX(), Math.max(velocity.getY(), 0), velocity.getZ());
        }
        super.tick();
    }
}
