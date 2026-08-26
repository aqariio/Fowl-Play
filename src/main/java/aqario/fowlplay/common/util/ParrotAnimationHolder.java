package aqario.fowlplay.common.util;

import net.minecraft.world.entity.AnimationState;

public interface ParrotAnimationHolder {
    AnimationState fowlplay$getStandingState();

    AnimationState fowlplay$getPerchingState();

    AnimationState fowlplay$getGlidingState();

    AnimationState fowlplay$getSwimmingState();
}
