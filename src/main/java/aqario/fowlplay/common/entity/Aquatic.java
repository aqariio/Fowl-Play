package aqario.fowlplay.common.entity;

public interface Aquatic {
    boolean isFloating();

    // how much of the hitbox the water should cover (from the bottom)
    float getMaxWaterHeight();
}
