//? if forge {
/*package aqario.fowlplay.forge.common.worldgen;

import aqario.fowlplay.common.worldgen.FPSpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;

public final class FPSpawnPlacementTypeImpl {
    public static SpawnPlacements.Type GROUND;
    public static SpawnPlacements.Type SEMIAQUATIC;
    public static SpawnPlacements.Type AQUATIC;

    public static SpawnPlacements.Type ground() {
        return SpawnPlacements.Type.create(
            FPSpawnPlacementType.GROUND_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnPlacements.Type semiaquatic() {
        return SpawnPlacements.Type.create(
            FPSpawnPlacementType.SEMIAQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnPlacements.Type aquatic() {
        return SpawnPlacements.Type.create(
            FPSpawnPlacementType.AQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }
}
*///?}