package aqario.fowlplay.common.sound;

import net.minecraft.sound.SoundCategory;

public enum FowlPlaySoundCategory {
    BIRDS("birds");

    public final String name;
    public SoundCategory soundCategory;

    FowlPlaySoundCategory(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
