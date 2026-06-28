package aqario.fowlplay.common.entity;

import net.minecraft.world.entity.MobCategory;

public enum FPMobCategory {
    FOWLPLAY_AMBIENT_BIRDS("fowlplay_ambient_birds", 15, true, false, 96),
    FOWLPLAY_BIRDS("fowlplay_birds", 20, true, false, 96);

    public static final FPMobCategory AMBIENT_BIRDS = FOWLPLAY_AMBIENT_BIRDS;
    public static final FPMobCategory BIRDS = FOWLPLAY_BIRDS;

    public MobCategory mobCategory;
    public final String name;
    public final int spawnCap;
    public final boolean peaceful;
    public final boolean rare;
    public final int immediateDespawnRange;

    FPMobCategory(String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        this.name = name;
        this.spawnCap = spawnCap;
        this.peaceful = peaceful;
        this.rare = rare;
        this.immediateDespawnRange = immediateDespawnRange;
    }
}
