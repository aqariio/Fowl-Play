//? if neoforge {
/*package aqario.fowlplay.neoforge.common.registry;

import aqario.fowlplay.neoforge.core.FowlPlayNeoForge;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.function.Supplier;

public class SpawnPlacementsRegistryImpl {
    public static <T extends Mob> void register(
        Supplier<EntityType<T>> type,
        SpawnPlacementType spawnPlacementType,
        Heightmap.Types heightmap,
        SpawnPlacements.SpawnPredicate<T> spawnPredicate
    ) {
        FowlPlayNeoForge.eventBus().<RegisterSpawnPlacementsEvent>addListener(event ->
            event.register(type.get(), spawnPlacementType, heightmap, spawnPredicate, RegisterSpawnPlacementsEvent.Operation.OR)
        );
    }
}
*///?}