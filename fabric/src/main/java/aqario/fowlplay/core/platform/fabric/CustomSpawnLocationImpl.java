package aqario.fowlplay.core.platform.fabric;

import net.minecraft.entity.SpawnRestriction;

public final class CustomSpawnLocationImpl {
    static {
        SpawnRestriction.Location.values();
    }

    public static SpawnRestriction.Location GROUND;
    public static SpawnRestriction.Location SEMIAQUATIC;
    public static SpawnRestriction.Location AQUATIC;

    public static SpawnRestriction.Location ground() {
        return GROUND;
    }

    public static SpawnRestriction.Location semiaquatic() {
        return SEMIAQUATIC;
    }

    public static SpawnRestriction.Location aquatic() {
        return AQUATIC;
    }
}
