package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.world.entity.ai.control.JumpControl;

public class HoppingBirdJumpControl extends JumpControl {
    private final BirdEntity bird;
    private boolean canJump;

    public HoppingBirdJumpControl(BirdEntity bird) {
        super(bird);
        this.bird = bird;
    }

    public boolean wantJump() {
        return this.jump;
    }

    public boolean canJump() {
        return this.canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void tick() {
        if(this.jump) {
//            this.bird.startJumping();
            this.jump = false;
        }
    }
}
