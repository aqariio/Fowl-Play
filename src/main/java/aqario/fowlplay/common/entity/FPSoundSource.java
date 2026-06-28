package aqario.fowlplay.common.entity;

import net.minecraft.sounds.SoundSource;

public enum FPSoundSource {
    FOWLPLAY_BIRDS("fowlplay_birds");

    public static final FPSoundSource BIRDS = FOWLPLAY_BIRDS;

    public SoundSource soundSource;
    public final String name;

    FPSoundSource(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
