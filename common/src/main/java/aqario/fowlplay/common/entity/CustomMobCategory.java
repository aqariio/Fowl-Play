package aqario.fowlplay.common.entity;

import net.minecraft.world.entity.MobCategory;

public enum CustomMobCategory {
    AMBIENT_BIRDS("ambient_birds", 15, true, false, 96),
    BIRDS("birds", 20, true, false, 96);

    public MobCategory mobCategory;
    public final String name;
    public final int spawnCap;
    public final boolean peaceful;
    public final boolean rare;
    public final int immediateDespawnRange;

    CustomMobCategory(String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        this.name = name;
        this.spawnCap = spawnCap;
        this.peaceful = peaceful;
        this.rare = rare;
        this.immediateDespawnRange = immediateDespawnRange;
    }
}
