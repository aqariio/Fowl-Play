package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.CustomMobCategory;
import aqario.fowlplay.common.entity.ScarecrowEntity;
import aqario.fowlplay.common.entity.bird.dove.PigeonEntity;
import aqario.fowlplay.common.entity.bird.passerine.*;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import aqario.fowlplay.common.entity.bird.raptor.HawkEntity;
import aqario.fowlplay.common.entity.bird.shorebird.GullEntity;
import aqario.fowlplay.common.entity.bird.waterfowl.DuckEntity;
import aqario.fowlplay.common.entity.bird.waterfowl.GooseEntity;
import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.util.EntityTypeBuilder;
import aqario.fowlplay.common.worldgen.CustomSpawnPlacementType;
import aqario.fowlplay.common.worldgen.SpawnPredicates;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public final class FPEntityTypes {
    public static final CommonRegister<EntityType<?>> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.ENTITY_TYPE,
        FowlPlay.ID
    );

    public static final Supplier<EntityType<BlueJayEntity>> BLUE_JAY = register("blue_jay",
        EntityTypeBuilder.of(
                BlueJayEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.4f, 0.55f)
            .attributes(BlueJayEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_BLUE_JAYS,
                FowlPlayConfig.getInstance().blueJaySpawnWeight,
                FowlPlayConfig.getInstance().blueJayMinGroupSize,
                FowlPlayConfig.getInstance().blueJayMaxGroupSize
            )
    );

    public static final Supplier<EntityType<CardinalEntity>> CARDINAL = register("cardinal",
        EntityTypeBuilder.of(
                CardinalEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.4f, 0.55f)
            .attributes(CardinalEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_CARDINALS,
                FowlPlayConfig.getInstance().cardinalSpawnWeight,
                FowlPlayConfig.getInstance().cardinalMinGroupSize,
                FowlPlayConfig.getInstance().cardinalMaxGroupSize
            )
    );

    public static final Supplier<EntityType<ChickadeeEntity>> CHICKADEE = register("chickadee",
        EntityTypeBuilder.of(
                ChickadeeEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.3f, 0.45f)
            .attributes(ChickadeeEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_CHICKADEES,
                FowlPlayConfig.getInstance().chickadeeSpawnWeight,
                FowlPlayConfig.getInstance().chickadeeMinGroupSize,
                FowlPlayConfig.getInstance().chickadeeMaxGroupSize
            )
    );

    public static final Supplier<EntityType<CrowEntity>> CROW = register("crow",
        EntityTypeBuilder.of(
                CrowEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.5f, 0.6f)
            .attributes(CrowEntity::createCrowAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_CROWS,
                FowlPlayConfig.getInstance().crowSpawnWeight,
                FowlPlayConfig.getInstance().crowMinGroupSize,
                FowlPlayConfig.getInstance().crowMaxGroupSize
            )
    );

    public static final Supplier<EntityType<DuckEntity>> DUCK = register("duck",
        EntityTypeBuilder.of(
                DuckEntity::new,
                CustomMobCategory.birds()
            )
            .sized(0.6f, 0.8f)
            .attributes(DuckEntity::createDuckAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.aquatic(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnWaterfowl
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_DUCKS,
                FowlPlayConfig.getInstance().duckSpawnWeight,
                FowlPlayConfig.getInstance().duckMinGroupSize,
                FowlPlayConfig.getInstance().duckMaxGroupSize
            )
            .spawnCost(
                FowlPlayBiomeTags.SPAWNS_DUCKS,
                0.7,
                0.1
            )
    );

    public static final Supplier<EntityType<GooseEntity>> GOOSE = register("goose",
        EntityTypeBuilder.of(
                GooseEntity::new,
                CustomMobCategory.birds()
            )
            .sized(0.7f, 1.1f)
            .attributes(GooseEntity::createGooseAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.aquatic(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnWaterfowl
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_GEESE,
                FowlPlayConfig.getInstance().gooseSpawnWeight,
                FowlPlayConfig.getInstance().gooseMinGroupSize,
                FowlPlayConfig.getInstance().gooseMaxGroupSize
            )
            .spawnCost(
                FowlPlayBiomeTags.SPAWNS_GEESE,
                0.9,
                0.07
            )
    );

    public static final Supplier<EntityType<GullEntity>> GULL = register("gull",
        EntityTypeBuilder.of(
                GullEntity::new,
                CustomMobCategory.birds()
            )
            .sized(0.6f, 0.8f)
            .attributes(GullEntity::createGullAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.semiaquatic(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnShorebirds
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_GULLS,
                FowlPlayConfig.getInstance().gullSpawnWeight,
                FowlPlayConfig.getInstance().gullMinGroupSize,
                FowlPlayConfig.getInstance().gullMaxGroupSize
            )
            .spawnCost(
                FowlPlayBiomeTags.SPAWNS_GULLS,
                1,
                0.1
            )
    );

    public static final Supplier<EntityType<HawkEntity>> HAWK = register("hawk",
        EntityTypeBuilder.of(
                HawkEntity::new,
                CustomMobCategory.birds()
            )
            .sized(0.6f, 0.8f)
            .attributes(HawkEntity::createHawkAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_HAWKS,
                FowlPlayConfig.getInstance().hawkSpawnWeight,
                FowlPlayConfig.getInstance().hawkMinGroupSize,
                FowlPlayConfig.getInstance().hawkMaxGroupSize
            )
    );

    public static final Supplier<EntityType<PenguinEntity>> PENGUIN = register("penguin",
        EntityTypeBuilder.of(
                PenguinEntity::new,
                MobCategory.CREATURE
            )
            .sized(0.5f, 1.4f)
            .attributes(PenguinEntity::createPenguinAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.semiaquatic(),
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PenguinEntity::canSpawnPenguins
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_PENGUINS,
                FowlPlayConfig.getInstance().penguinSpawnWeight,
                FowlPlayConfig.getInstance().penguinMinGroupSize,
                FowlPlayConfig.getInstance().penguinMaxGroupSize
            )
    );

    public static final Supplier<EntityType<PigeonEntity>> PIGEON = register("pigeon",
        EntityTypeBuilder.of(
                PigeonEntity::new,
                CustomMobCategory.birds()
            )
            .sized(0.5f, 0.6f)
            .attributes(PigeonEntity::createPigeonAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnShorebirds
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_PIGEONS,
                FowlPlayConfig.getInstance().pigeonSpawnWeight,
                FowlPlayConfig.getInstance().pigeonMinGroupSize,
                FowlPlayConfig.getInstance().pigeonMaxGroupSize
            )
    );

    public static final Supplier<EntityType<RavenEntity>> RAVEN = register("raven",
        EntityTypeBuilder.of(
                RavenEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.6f, 0.8f)
            .attributes(RavenEntity::createRavenAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_RAVENS,
                FowlPlayConfig.getInstance().ravenSpawnWeight,
                FowlPlayConfig.getInstance().ravenMinGroupSize,
                FowlPlayConfig.getInstance().ravenMaxGroupSize
            )
    );

    public static final Supplier<EntityType<RobinEntity>> ROBIN = register("robin",
        EntityTypeBuilder.of(
                RobinEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.4f, 0.55f)
            .attributes(RobinEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_ROBINS,
                FowlPlayConfig.getInstance().robinSpawnWeight,
                FowlPlayConfig.getInstance().robinMinGroupSize,
                FowlPlayConfig.getInstance().robinMaxGroupSize
            )
    );

    public static final Supplier<EntityType<SparrowEntity>> SPARROW = register("sparrow",
        EntityTypeBuilder.of(
                SparrowEntity::new,
                CustomMobCategory.ambientBirds()
            )
            .sized(0.3f, 0.45f)
            .attributes(SparrowEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementType.ground(),
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FowlPlayBiomeTags.SPAWNS_SPARROWS,
                FowlPlayConfig.getInstance().sparrowSpawnWeight,
                FowlPlayConfig.getInstance().sparrowMinGroupSize,
                FowlPlayConfig.getInstance().sparrowMaxGroupSize
            )
    );

    public static final Supplier<EntityType<ScarecrowEntity>> SCARECROW = register("scarecrow",
        EntityTypeBuilder.of(
                ScarecrowEntity::new,
                MobCategory.MISC
            )
            .sized(0.6f, 2.0f)
            .attributes(ScarecrowEntity::createScarecrowAttributes)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityTypeBuilder<T> builder) {
        return REGISTRAR.register(id, () -> builder.build(id));
    }
}
