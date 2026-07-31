//~ expect_platform

package aqario.fowlplay.common.registry;

import aqario.fowlplay.fabric.common.registry.SpawnPlacementsRegistryImpl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class SpawnPlacementsRegistry {
    public static <T extends Mob> void register(
        Supplier<EntityType<T>> type,
        SpawnPlacements.Type spawnPlacementType,
        Heightmap.Types heightmap,
        SpawnPlacements.SpawnPredicate<T> spawnPredicate
    ) {
        SpawnPlacementsRegistryImpl.register(type, spawnPlacementType, heightmap, spawnPredicate);
    }
}
