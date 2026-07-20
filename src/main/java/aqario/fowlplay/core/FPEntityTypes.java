package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.FPMobCategory;
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
import aqario.fowlplay.common.worldgen.CustomSpawnPlacementTypes;
import aqario.fowlplay.common.worldgen.SpawnPredicates;
import aqario.fowlplay.core.tags.FPBiomeTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public final class FPEntityTypes {
    public static final CommonRegister<EntityType<?>> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.ENTITY_TYPE,
        FowlPlay.ID
    );

    public static final Supplier<EntityType<BlueJayEntity>> BLUE_JAY = register("blue_jay",
        EntityTypeBuilder.of(
                BlueJayEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.4f, 0.55f)
            .eyeHeight(0.475f)
            .attributes(BlueJayEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_BLUE_JAYS,
                FPConfig.getInstance().blueJaySpawnWeight,
                FPConfig.getInstance().blueJayMinGroupSize,
                FPConfig.getInstance().blueJayMaxGroupSize
            )
    );

    public static final Supplier<EntityType<CardinalEntity>> CARDINAL = register("cardinal",
        EntityTypeBuilder.of(
                CardinalEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.4f, 0.55f)
            .eyeHeight(0.475f)
            .attributes(CardinalEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_CARDINALS,
                FPConfig.getInstance().cardinalSpawnWeight,
                FPConfig.getInstance().cardinalMinGroupSize,
                FPConfig.getInstance().cardinalMaxGroupSize
            )
    );

    public static final Supplier<EntityType<ChickadeeEntity>> CHICKADEE = register("chickadee",
        EntityTypeBuilder.of(
                ChickadeeEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.3f, 0.45f)
            .eyeHeight(0.4f)
            .attributes(ChickadeeEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_CHICKADEES,
                FPConfig.getInstance().chickadeeSpawnWeight,
                FPConfig.getInstance().chickadeeMinGroupSize,
                FPConfig.getInstance().chickadeeMaxGroupSize
            )
    );

    public static final Supplier<EntityType<CrowEntity>> CROW = register("crow",
        EntityTypeBuilder.of(
                CrowEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.5f, 0.6f)
            .eyeHeight(0.55f)
            .attributes(CrowEntity::createCrowAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_CROWS,
                FPConfig.getInstance().crowSpawnWeight,
                FPConfig.getInstance().crowMinGroupSize,
                FPConfig.getInstance().crowMaxGroupSize
            )
    );

    public static final Supplier<EntityType<DuckEntity>> DUCK = register("duck",
        EntityTypeBuilder.of(
                DuckEntity::new,
                FPMobCategory.BIRDS.mobCategory
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
            .attributes(DuckEntity::createDuckAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.AQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnWaterfowl
            )
            .spawn(
                FPBiomeTags.SPAWNS_DUCKS,
                FPConfig.getInstance().duckSpawnWeight,
                FPConfig.getInstance().duckMinGroupSize,
                FPConfig.getInstance().duckMaxGroupSize
            )
            .spawnCost(
                FPBiomeTags.SPAWNS_DUCKS,
                0.7,
                0.1
            )
    );

    public static final Supplier<EntityType<GooseEntity>> GOOSE = register("goose",
        EntityTypeBuilder.of(
                GooseEntity::new,
                FPMobCategory.BIRDS.mobCategory
            )
            .sized(0.7f, 1.1f)
            .eyeHeight(1.0f)
            .attributes(GooseEntity::createGooseAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.AQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnWaterfowl
            )
            .spawn(
                FPBiomeTags.SPAWNS_GEESE,
                FPConfig.getInstance().gooseSpawnWeight,
                FPConfig.getInstance().gooseMinGroupSize,
                FPConfig.getInstance().gooseMaxGroupSize
            )
            .spawnCost(
                FPBiomeTags.SPAWNS_GEESE,
                0.9,
                0.07
            )
    );

    public static final Supplier<EntityType<GullEntity>> GULL = register("gull",
        EntityTypeBuilder.of(
                GullEntity::new,
                FPMobCategory.BIRDS.mobCategory
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
            .attributes(GullEntity::createGullAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.SEMIAQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnShorebirds
            )
            .spawn(
                FPBiomeTags.SPAWNS_GULLS,
                FPConfig.getInstance().gullSpawnWeight,
                FPConfig.getInstance().gullMinGroupSize,
                FPConfig.getInstance().gullMaxGroupSize
            )
            .spawnCost(
                FPBiomeTags.SPAWNS_GULLS,
                1,
                0.1
            )
    );

    public static final Supplier<EntityType<HawkEntity>> HAWK = register("hawk",
        EntityTypeBuilder.of(
                HawkEntity::new,
                FPMobCategory.BIRDS.mobCategory
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
            .attributes(HawkEntity::createHawkAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_HAWKS,
                FPConfig.getInstance().hawkSpawnWeight,
                FPConfig.getInstance().hawkMinGroupSize,
                FPConfig.getInstance().hawkMaxGroupSize
            )
    );

    public static final Supplier<EntityType<PenguinEntity>> PENGUIN = register("penguin",
        EntityTypeBuilder.of(
                PenguinEntity::new,
                MobCategory.CREATURE
            )
            .sized(0.5f, 1.4f)
            .eyeHeight(1.35f)
            .passengerAttachments(new Vec3(0, 0.75, -0.1))
            .attributes(PenguinEntity::createPenguinAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.SEMIAQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PenguinEntity::canSpawnPenguins
            )
            .spawn(
                FPBiomeTags.SPAWNS_PENGUINS,
                FPConfig.getInstance().penguinSpawnWeight,
                FPConfig.getInstance().penguinMinGroupSize,
                FPConfig.getInstance().penguinMaxGroupSize
            )
    );

    public static final Supplier<EntityType<PigeonEntity>> PIGEON = register("pigeon",
        EntityTypeBuilder.of(
                PigeonEntity::new,
                FPMobCategory.BIRDS.mobCategory
            )
            .sized(0.5f, 0.6f)
            .eyeHeight(0.5f)
            .attributes(PigeonEntity::createPigeonAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnShorebirds
            )
            .spawn(
                FPBiomeTags.SPAWNS_PIGEONS,
                FPConfig.getInstance().pigeonSpawnWeight,
                FPConfig.getInstance().pigeonMinGroupSize,
                FPConfig.getInstance().pigeonMaxGroupSize
            )
    );

    public static final Supplier<EntityType<RavenEntity>> RAVEN = register("raven",
        EntityTypeBuilder.of(
                RavenEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
            .attributes(RavenEntity::createRavenAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_RAVENS,
                FPConfig.getInstance().ravenSpawnWeight,
                FPConfig.getInstance().ravenMinGroupSize,
                FPConfig.getInstance().ravenMaxGroupSize
            )
    );

    public static final Supplier<EntityType<RobinEntity>> ROBIN = register("robin",
        EntityTypeBuilder.of(
                RobinEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.4f, 0.55f)
            .eyeHeight(0.475f)
            .attributes(RobinEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_ROBINS,
                FPConfig.getInstance().robinSpawnWeight,
                FPConfig.getInstance().robinMinGroupSize,
                FPConfig.getInstance().robinMaxGroupSize
            )
    );

    public static final Supplier<EntityType<SparrowEntity>> SPARROW = register("sparrow",
        EntityTypeBuilder.of(
                SparrowEntity::new,
                FPMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .sized(0.3f, 0.45f)
            .eyeHeight(0.4f)
            .attributes(SparrowEntity::createFlyingBirdAttributes)
            .spawnPlacement(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .spawn(
                FPBiomeTags.SPAWNS_SPARROWS,
                FPConfig.getInstance().sparrowSpawnWeight,
                FPConfig.getInstance().sparrowMinGroupSize,
                FPConfig.getInstance().sparrowMaxGroupSize
            )
    );

    public static final Supplier<EntityType<ScarecrowEntity>> SCARECROW = register("scarecrow",
        EntityTypeBuilder.of(
                ScarecrowEntity::new,
                MobCategory.MISC
            )
            .sized(0.6f, 2.0f)
            .eyeHeight(1.72f)
            .attributes(ScarecrowEntity::createScarecrowAttributes)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityTypeBuilder<T> builder) {
        return REGISTRAR.register(id, () -> builder.build(id));
    }
}
