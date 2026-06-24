package aqario.fowlplay.common.entity;

import net.minecraft.sounds.SoundSource;

public enum CustomSoundSource {
    BIRDS("birds");

    public SoundSource soundSource;
    private final String name;

    CustomSoundSource(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
