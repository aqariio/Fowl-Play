package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public final class FowlPlayWorldGen {
    public static void init() {
        SpawnRestriction.register(FowlPlayEntityType.BLUE_JAY, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.BLUE_JAY, FowlPlayConfig.blueJaySpawnWeight, FowlPlayConfig.blueJayMinGroupSize, FowlPlayConfig.blueJayMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.CARDINAL, FowlPlayConfig.cardinalSpawnWeight, FowlPlayConfig.cardinalMinGroupSize, FowlPlayConfig.cardinalMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.CHICKADEE, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_CHICKADEES),
            SpawnGroup.AMBIENT, FowlPlayEntityType.CHICKADEE, FowlPlayConfig.chickadeeSpawnWeight, FowlPlayConfig.chickadeeMinGroupSize, FowlPlayConfig.chickadeeMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.DUCK, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_DUCKS),
            SpawnGroup.CREATURE, FowlPlayEntityType.DUCK, FowlPlayConfig.duckSpawnWeight, FowlPlayConfig.duckMinGroupSize, FowlPlayConfig.duckMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.GULL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GullEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_GULLS),
            SpawnGroup.CREATURE, FowlPlayEntityType.GULL, FowlPlayConfig.gullSpawnWeight, FowlPlayConfig.gullMinGroupSize, FowlPlayConfig.gullMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PENGUIN, FowlPlayConfig.penguinSpawnWeight, FowlPlayConfig.penguinMinGroupSize, FowlPlayConfig.penguinMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.PIGEON, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, PigeonEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PIGEON, FowlPlayConfig.pigeonSpawnWeight, FowlPlayConfig.pigeonMinGroupSize, FowlPlayConfig.pigeonMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.RAVEN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_RAVENS),
            SpawnGroup.CREATURE, FowlPlayEntityType.RAVEN, FowlPlayConfig.ravenSpawnWeight, FowlPlayConfig.ravenMinGroupSize, FowlPlayConfig.ravenMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.ROBIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.ROBIN, FowlPlayConfig.robinSpawnWeight, FowlPlayConfig.robinMinGroupSize, FowlPlayConfig.robinMaxGroupSize
        );

        SpawnRestriction.register(FowlPlayEntityType.SPARROW, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_SPARROWS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.SPARROW, FowlPlayConfig.sparrowSpawnWeight, FowlPlayConfig.sparrowMinGroupSize, FowlPlayConfig.sparrowMaxGroupSize
        );
    }
}
