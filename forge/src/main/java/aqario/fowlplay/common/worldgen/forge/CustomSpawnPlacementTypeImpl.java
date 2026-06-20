package aqario.fowlplay.common.worldgen.forge;

import aqario.fowlplay.common.worldgen.CustomSpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;

public final class CustomSpawnPlacementTypeImpl {
    public static SpawnPlacements.Type GROUND;
    public static SpawnPlacements.Type SEMIAQUATIC;
    public static SpawnPlacements.Type AQUATIC;

    public static SpawnPlacements.Type ground() {
        return SpawnPlacements.Type.create(
            CustomSpawnPlacementType.GROUND_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnPlacements.Type semiaquatic() {
        return SpawnPlacements.Type.create(
            CustomSpawnPlacementType.SEMIAQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnPlacements.Type aquatic() {
        return SpawnPlacements.Type.create(
            CustomSpawnPlacementType.AQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }
}
