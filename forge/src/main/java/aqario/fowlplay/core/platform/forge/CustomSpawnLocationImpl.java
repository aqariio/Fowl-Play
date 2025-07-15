package aqario.fowlplay.core.platform.forge;

import aqario.fowlplay.core.platform.CustomSpawnLocation;
import net.minecraft.entity.SpawnRestriction;

public final class CustomSpawnLocationImpl {
    public static SpawnRestriction.Location GROUND;
    public static SpawnRestriction.Location SEMIAQUATIC;
    public static SpawnRestriction.Location AQUATIC;

    public static SpawnRestriction.Location ground() {
        return SpawnRestriction.Location.create(
            CustomSpawnLocation.GROUND_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnRestriction.Location semiaquatic() {
        return SpawnRestriction.Location.create(
            CustomSpawnLocation.SEMIAQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }

    public static SpawnRestriction.Location aquatic() {
        return SpawnRestriction.Location.create(
            CustomSpawnLocation.AQUATIC_INTERNAL_NAME,
            (world, pos, entity) -> true
        );
    }
}
