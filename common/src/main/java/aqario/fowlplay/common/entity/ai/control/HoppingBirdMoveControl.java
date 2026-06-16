package aqario.fowlplay.common.entity.ai.control;

import aqario.fowlplay.common.entity.bird.BirdEntity;

public class HoppingBirdMoveControl extends BirdMoveControl {
    public HoppingBirdMoveControl(BirdEntity bird) {
        super(bird);
    }

    @Override
    protected void tickWalking() {
        if(this.bird.onGround() && !this.bird.isJumping() && !((HoppingBirdJumpControl) this.bird.getJumpControl()).wantJump()) {
//            this.bird.setSpeedModifier((double) 0.0F);
        }
        else if(this.hasWanted()) {
//            this.bird.setSpeedModifier(this.nextJumpSpeed);
        }
        super.tickWalking();
    }
}
