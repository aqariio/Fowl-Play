//? if fabric {
package aqario.fowlplay.fabric.common.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class SpawnPlacementsRegistryImpl {
    public static <T extends Mob> void register(
        Supplier<EntityType<T>> type,
        SpawnPlacementType spawnPlacementType,
        Heightmap.Types heightmap,
        SpawnPlacements.SpawnPredicate<T> spawnPredicate
    ) {
        SpawnPlacements.register(type.get(), spawnPlacementType, heightmap, spawnPredicate);
    }
}
//?}