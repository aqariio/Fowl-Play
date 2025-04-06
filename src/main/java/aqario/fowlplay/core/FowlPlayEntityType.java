package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.ExtendedFabricMobBuilder;
import aqario.fowlplay.common.world.gen.FowlPlaySpawnLocation;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

public final class FowlPlayEntityType {
    public static final EntityType<BlueJayEntity> BLUE_JAY = register("blue_jay",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(BlueJayEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(BlueJayEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<CardinalEntity> CARDINAL = register("cardinal",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(CardinalEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(CardinalEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<ChickadeeEntity> CHICKADEE = register("chickadee",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(ChickadeeEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(ChickadeeEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.3f, 0.45f))
    );

    public static final EntityType<CrowEntity> CROW = register("crow",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(CrowEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(CrowEntity::createCrowAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.5f, 0.6f))
    );

    public static final EntityType<DuckEntity> DUCK = register("duck",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(DuckEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD.spawnGroup)
            .defaultAttributes(DuckEntity::createDuckAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.AQUATIC.location,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnWaterfowl
            )
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<GullEntity> GULL = register("gull",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(GullEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD.spawnGroup)
            .defaultAttributes(GullEntity::createGullAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.SEMIAQUATIC.location,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnShorebirds
            )
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<HawkEntity> HAWK = register("hawk",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(HawkEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD.spawnGroup)
            .defaultAttributes(HawkEntity::createHawkAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<PenguinEntity> PENGUIN = register("penguin",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(PenguinEntity::new)
            .spawnGroup(SpawnGroup.CREATURE)
            .defaultAttributes(PenguinEntity::createPenguinAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.SEMIAQUATIC.location,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                PenguinEntity::canSpawnPenguins
            )
            .dimensions(EntityDimensions.changing(0.5f, 1.4f))
    );

    public static final EntityType<PigeonEntity> PIGEON = register("pigeon",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(PigeonEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD.spawnGroup)
            .defaultAttributes(PigeonEntity::createPigeonAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnShorebirds
            )
            .dimensions(EntityDimensions.changing(0.5f, 0.6f))
    );

    public static final EntityType<RavenEntity> RAVEN = register("raven",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(RavenEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(RavenEntity::createRavenAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.6f, 0.8f))
    );

    public static final EntityType<RobinEntity> ROBIN = register("robin",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(RobinEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(RobinEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    public static final EntityType<SparrowEntity> SPARROW = register("sparrow",
        ExtendedFabricMobBuilder.createExtended()
            .entityFactory(SparrowEntity::new)
            .spawnGroup(FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup)
            .defaultAttributes(SparrowEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                FowlPlaySpawnLocation.GROUND.location,
                Heightmap.Type.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .dimensions(EntityDimensions.changing(0.4f, 0.55f))
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(FowlPlay.ID, id), builder.build());
    }

    public static void init() {
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
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_CROWS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
            FowlPlayEntityType.CROW,
            FowlPlayConfig.getInstance().crowSpawnWeight,
            FowlPlayConfig.getInstance().crowMinGroupSize,
            FowlPlayConfig.getInstance().crowMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_DUCKS),
            FowlPlaySpawnGroup.BIRD.spawnGroup,
            FowlPlayEntityType.DUCK,
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_GULLS),
            FowlPlaySpawnGroup.BIRD.spawnGroup,
            FowlPlayEntityType.GULL,
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
//        BiomeModifications.addSpawn(
//            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_HAWKS),
//            FowlPlaySpawnGroup.BIRD.spawnGroup,
//            FowlPlayEntityType.HAWK,
//            FowlPlayConfig.getInstance().hawkSpawnWeight,
//            FowlPlayConfig.getInstance().hawkMinGroupSize,
//            FowlPlayConfig.getInstance().hawkMaxGroupSize
//        );
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
            FowlPlaySpawnGroup.BIRD.spawnGroup,
            FowlPlayEntityType.PIGEON,
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        BiomeModifications.addSpawn(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_RAVENS),
            FowlPlaySpawnGroup.BIRD_AMBIENT.spawnGroup,
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

//        addSpawnCost(
//            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_DUCKS),
//            FowlPlayEntityType.DUCK,
//            1,
//            0.07
//        );
        addSpawnCost(
            BiomeSelectors.tag(FowlPlayBiomeTags.SPAWNS_GULLS),
            FowlPlayEntityType.GULL,
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

        // We need the entity entity to be registered, or we cannot deduce an ID otherwise
        Identifier id = Registries.ENTITY_TYPE.getId(entityType);
        Preconditions.checkState(Registries.ENTITY_TYPE.getKey(entityType).isPresent(), "Unregistered entity entity: %s", entityType);

        // Add a new spawn cost to the chosen biome
        BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, biomeSelector, context ->
            context.getSpawnSettings().setSpawnCost(entityType, mass, gravityLimit)
        );
    }
}
