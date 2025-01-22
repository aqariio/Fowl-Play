package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.world.Heightmap;

public final class FowlPlayWorldGen {
    public static void init() {
        SpawnRestriction.register(FowlPlayEntityType.BLUE_JAY, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        SpawnRestriction.register(FowlPlayEntityType.CHICKADEE, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        SpawnRestriction.register(FowlPlayEntityType.DUCK, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canSpawn
        );
        SpawnRestriction.register(FowlPlayEntityType.GULL, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GullEntity::canSpawn
        );
        SpawnRestriction.register(FowlPlayEntityType.HAWK, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, HawkEntity::canSpawn
        );
        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn
        );
        SpawnRestriction.register(FowlPlayEntityType.PIGEON, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, PigeonEntity::canSpawn
        );
        SpawnRestriction.register(FowlPlayEntityType.RAVEN, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        SpawnRestriction.register(FowlPlayEntityType.ROBIN, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        SpawnRestriction.register(FowlPlayEntityType.SPARROW, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );

        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_BLUE_JAYS),
            SpawnGroup.AMBIENT,
            FowlPlayEntityType.BLUE_JAY,
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.AMBIENT,
            FowlPlayEntityType.CARDINAL,
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CHICKADEES),
            SpawnGroup.AMBIENT,
            FowlPlayEntityType.CHICKADEE,
            FowlPlayConfig.getInstance().chickadeeSpawnWeight,
            FowlPlayConfig.getInstance().chickadeeMinGroupSize,
            FowlPlayConfig.getInstance().chickadeeMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_DUCKS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.DUCK,
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_GULLS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.GULL,
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_HAWKS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.HAWK,
            FowlPlayConfig.getInstance().hawkSpawnWeight,
            FowlPlayConfig.getInstance().hawkMinGroupSize,
            FowlPlayConfig.getInstance().hawkMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.PENGUIN,
            FowlPlayConfig.getInstance().penguinSpawnWeight,
            FowlPlayConfig.getInstance().penguinMinGroupSize,
            FowlPlayConfig.getInstance().penguinMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.PIGEON,
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_RAVENS),
            SpawnGroup.CREATURE,
            FowlPlayEntityType.RAVEN,
            FowlPlayConfig.getInstance().ravenSpawnWeight,
            FowlPlayConfig.getInstance().ravenMinGroupSize,
            FowlPlayConfig.getInstance().ravenMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.AMBIENT,
            FowlPlayEntityType.ROBIN,
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_SPARROWS),
            SpawnGroup.AMBIENT,
            FowlPlayEntityType.SPARROW,
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );
    }
}
