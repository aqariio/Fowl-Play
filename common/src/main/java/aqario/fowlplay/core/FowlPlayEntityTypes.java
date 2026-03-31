package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.CustomMobCategory;
import aqario.fowlplay.common.entity.ScarecrowEntity;
import aqario.fowlplay.common.entity.bird.dove.PigeonEntity;
import aqario.fowlplay.common.entity.bird.passerine.*;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import aqario.fowlplay.common.entity.bird.raptor.HawkEntity;
import aqario.fowlplay.common.entity.bird.shorebird.GullEntity;
import aqario.fowlplay.common.entity.bird.waterfowl.DuckEntity;
import aqario.fowlplay.common.entity.bird.waterfowl.GooseEntity;
import aqario.fowlplay.common.util.EntityTypeBuilder;
import aqario.fowlplay.common.world.gen.CustomSpawnPlacementTypes;
import aqario.fowlplay.common.world.gen.SpawnPredicates;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public final class FowlPlayEntityTypes {
    public static final Holder<EntityType<BlueJayEntity>> BLUE_JAY = register("blue_jay",
        EntityTypeBuilder.of(
                BlueJayEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(BlueJayEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Holder<EntityType<CardinalEntity>> CARDINAL = register("cardinal",
        EntityTypeBuilder.of(
                CardinalEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(CardinalEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Holder<EntityType<ChickadeeEntity>> CHICKADEE = register("chickadee",
        EntityTypeBuilder.of(
                ChickadeeEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(ChickadeeEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final Holder<EntityType<CrowEntity>> CROW = register("crow",
        EntityTypeBuilder.of(
                CrowEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(CrowEntity::createCrowAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.5f, 0.6f)
            .eyeHeight(0.55f)
    );

    public static final Holder<EntityType<DuckEntity>> DUCK = register("duck",
        EntityTypeBuilder.of(
                DuckEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(DuckEntity::createDuckAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.AQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnWaterfowl
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Holder<EntityType<GooseEntity>> GOOSE = register("goose",
        EntityTypeBuilder.of(
                GooseEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(GooseEntity::createGooseAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.AQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnWaterfowl
            )
            .dimensions(0.7f, 1.1f)
            .eyeHeight(1.0f)
    );

    public static final Holder<EntityType<GullEntity>> GULL = register("gull",
        EntityTypeBuilder.of(
                GullEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(GullEntity::createGullAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.SEMIAQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SpawnPredicates::canSpawnShorebirds
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Holder<EntityType<HawkEntity>> HAWK = register("hawk",
        EntityTypeBuilder.of(
                HawkEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(HawkEntity::createHawkAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Holder<EntityType<PenguinEntity>> PENGUIN = register("penguin",
        EntityTypeBuilder.of(
                PenguinEntity::new,
                MobCategory.CREATURE
            )
            .attributes(PenguinEntity::createPenguinAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.SEMIAQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PenguinEntity::canSpawnPenguins
            )
            .dimensions(0.5f, 1.4f)
            .eyeHeight(1.35f)
            .passengerAttachments(new Vec3(0, 0.75, -0.1))
    );

    public static final Holder<EntityType<PigeonEntity>> PIGEON = register("pigeon",
        EntityTypeBuilder.of(
                PigeonEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(PigeonEntity::createPigeonAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnShorebirds
            )
            .dimensions(0.5f, 0.6f)
            .eyeHeight(0.5f)
    );

    public static final Holder<EntityType<RavenEntity>> RAVEN = register("raven",
        EntityTypeBuilder.of(
                RavenEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(RavenEntity::createRavenAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Holder<EntityType<RobinEntity>> ROBIN = register("robin",
        EntityTypeBuilder.of(
                RobinEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(RobinEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Holder<EntityType<SparrowEntity>> SPARROW = register("sparrow",
        EntityTypeBuilder.of(
                SparrowEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(SparrowEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                SpawnPredicates::canSpawnPasserines
            )
            .dimensions(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final Holder<EntityType<ScarecrowEntity>> SCARECROW = register("scarecrow",
        EntityTypeBuilder.of(
                ScarecrowEntity::new,
                MobCategory.MISC
            )
            .attributes(ScarecrowEntity::createScarecrowAttributes)
            .dimensions(0.6f, 2.0f)
            .eyeHeight(1.72f)
    );

    private static <T extends Entity> Holder<EntityType<T>> register(String id, EntityTypeBuilder<T> builder) {
        var result = builder.build();
        return FowlPlay.ENTITY_REGISTRAR.register(id, result::builder)
            .withDefaultAttributes(result.attributeBuilder())
            .withSpawnPlacement(
                result.spawnPlacement(),
                result.heightmap(),
                result::spawnPredicate
            )
            .asHolder();
    }

    public static void init() {
    }
}
