package aqario.fowlplay.common.world.gen;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.SpawnRestriction.Location;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

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
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlyingBirdEntity::canSpawnShorebirds
        );
        SpawnRestriction.register(FowlPlayEntityType.HAWK, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnPasserines
        );
        SpawnRestriction.register(FowlPlayEntityType.PENGUIN, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawnPenguins
        );
        SpawnRestriction.register(FowlPlayEntityType.PIGEON, Location.NO_RESTRICTIONS,
            Heightmap.Type.MOTION_BLOCKING, FlyingBirdEntity::canSpawnShorebirds
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
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.BLUE_JAY,
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CARDINALS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.CARDINAL,
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CHICKADEES),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
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
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.ROBIN,
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_SPARROWS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.SPARROW,
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );

        addSpawnCost(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_DUCKS),
            FowlPlayEntityType.DUCK,
            1,
            0.07
        );
    }

    public static void addSpawnCost(
        Predicate<BiomeSelectionContext> biomeSelector,
        EntityType<?> entityType,
        double mass,
        double gravityLimit
    ) {
        // See constructor of SpawnSettings.SpawnEntry for context
        Preconditions.checkArgument(entityType.getSpawnGroup() != SpawnGroup.MISC,
            "Cannot add spawns for entities with spawnGroup=MISC since they'd be replaced by pigs.");

        // We need the entity type to be registered, or we cannot deduce an ID otherwise
        Identifier id = Registries.ENTITY_TYPE.getId(entityType);
        Preconditions.checkState(Registries.ENTITY_TYPE.getKey(entityType).isPresent(), "Unregistered entity type: %s", entityType);

        // Add a new spawn cost to the chosen biome
        BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, biomeSelector, context -> {
            context.getSpawnSettings().setSpawnCost(entityType, mass, gravityLimit);
        });
    }
}
