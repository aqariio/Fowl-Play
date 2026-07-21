package aqario.fowlplay.common.worldgen.fabric;

import net.minecraft.world.entity.SpawnPlacements;

public final class CustomSpawnPlacementTypeImpl {
    static {
        SpawnPlacements.Type.values();
    }

    public static SpawnPlacements.Type GROUND;
    public static SpawnPlacements.Type SEMIAQUATIC;
    public static SpawnPlacements.Type AQUATIC;

    public static SpawnPlacements.Type ground() {
        return GROUND;
    }

    public static SpawnPlacements.Type semiaquatic() {
        return SEMIAQUATIC;
    }

    public static SpawnPlacements.Type aquatic() {
        return AQUATIC;
    }
}
