package aqario.fowlplay.core.platform.forge;

import aqario.fowlplay.core.platform.CustomSpawnLocation;
import net.minecraft.world.entity.SpawnPlacements;

public final class CustomSpawnLocationImpl {
    public static SpawnPlacements.Type GROUND;
    public static SpawnPlacements.Type SEMIAQUATIC;
    public static SpawnPlacements.Type AQUATIC;

    public static SpawnPlacements.Type ground() {
        return SpawnPlacements.Type.create(
            CustomSpawnLocation.GROUND_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnPlacements.Type semiaquatic() {
        return SpawnPlacements.Type.create(
            CustomSpawnLocation.SEMIAQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnPlacements.Type aquatic() {
        return SpawnPlacements.Type.create(
            CustomSpawnLocation.AQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }
}
