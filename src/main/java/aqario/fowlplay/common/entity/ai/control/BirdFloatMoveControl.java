package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.util.math.Vec3d;

public class BirdFloatMoveControl extends BirdMoveControl {
    public BirdFloatMoveControl(BirdEntity bird) {
        super(bird);
    }

    @Override
    public void tick() {
        Vec3d velocity = this.entity.getVelocity();
        if (((BirdEntity) this.entity).isBelowWaterline()) {
            this.entity.setVelocity(velocity.add(0.0, 0.05, 0.0));
            if (this.entity.isSubmergedInWater()) {
                velocity = this.entity.getVelocity();
                this.entity.setVelocity(velocity.add(0.0, 0.1, 0.0));
            }
            velocity = this.entity.getVelocity();
            this.entity.setVelocity(velocity.getX(), Math.max(velocity.getY(), 0), velocity.getZ());
        }
        super.tick();
    }
}
