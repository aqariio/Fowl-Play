package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.Aquatic;
import aqario.fowlplay.common.entity.BirdEntity;

public class BirdFloatMoveControl extends BirdMoveControl {
    public BirdFloatMoveControl(BirdEntity bird) {
        super(bird);
        if (!(bird instanceof Aquatic)) {
            throw new IllegalArgumentException("BirdFloatMoveControl can only be used with Aquatic birds");
        }
    }

    @Override
    public void tick() {
        if (((Aquatic) this.entity).isFloating()) {
            this.entity.setVelocity(this.entity.getVelocity().add(0.0, 0.05, 0.0));
        }
        super.tick();
    }
}
