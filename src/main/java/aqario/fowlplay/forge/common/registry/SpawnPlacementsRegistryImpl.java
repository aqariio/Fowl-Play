//? if forge {
/*package aqario.fowlplay.forge.common.registry;

import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

import java.util.function.Supplier;

public class SpawnPlacementsRegistryImpl {
    public static <T extends Mob> void register(
        Supplier<EntityType<T>> type,
        SpawnPlacements.Type spawnPlacementType,
        Heightmap.Types heightmap,
        SpawnPlacements.SpawnPredicate<T> spawnPredicate
    ) {
        FowlPlayForge.eventBus().<SpawnPlacementRegisterEvent>addListener(event ->
            event.register(type.get(), spawnPlacementType, heightmap, spawnPredicate, SpawnPlacementRegisterEvent.Operation.OR)
        );
    }
}
*///?}