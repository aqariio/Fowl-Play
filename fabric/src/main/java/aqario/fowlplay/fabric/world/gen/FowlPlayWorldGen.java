package aqario.fowlplay.fabric.world.gen;

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
            SpawnGroup.AMBIENT, FowlPlayEntityType.BLUE_JAY, 25, 1, 2
        );

        SpawnRestriction.register(FowlPlayEntityType.CARDINAL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.CARDINAL, 35, 1, 2
        );

        SpawnRestriction.register(FowlPlayEntityType.GULL, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GullEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_GULLS),
            SpawnGroup.CREATURE, FowlPlayEntityType.GULL, 30, 8, 12
        );

        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_PENGUINS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PENGUIN, 1, 16, 24
        );

        SpawnRestriction.register(FowlPlayEntityType.PIGEON, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, PigeonEntity::canSpawn
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_PIGEONS),
            SpawnGroup.CREATURE, FowlPlayEntityType.PIGEON, 20, 4, 8
        );

        SpawnRestriction.register(FowlPlayEntityType.ROBIN, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_ROBINS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.ROBIN, 50, 3, 5
        );

        SpawnRestriction.register(FowlPlayEntityType.SPARROW, SpawnLocationTypes.UNRESTRICTED,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        BiomeModifications.addSpawn(biome -> biome.getBiomeRegistryEntry().isIn(FowlPlayBiomeTags.SPAWNS_SPARROWS),
            SpawnGroup.AMBIENT, FowlPlayEntityType.SPARROW, 75, 6, 10
        );
    }
}
