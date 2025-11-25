package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.common.util.EntityTypeBuilder;
import aqario.fowlplay.common.world.gen.CustomSpawnPlacementTypes;
import aqario.fowlplay.core.platform.PlatformHelper;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public final class FowlPlayEntityTypes {
    public static final Supplier<EntityType<BlueJayEntity>> BLUE_JAY = register("blue_jay",
        EntityTypeBuilder.of(
                BlueJayEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(BlueJayEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Supplier<EntityType<CardinalEntity>> CARDINAL = register("cardinal",
        EntityTypeBuilder.of(
                CardinalEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(CardinalEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Supplier<EntityType<ChickadeeEntity>> CHICKADEE = register("chickadee",
        EntityTypeBuilder.of(
                ChickadeeEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(ChickadeeEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final Supplier<EntityType<CrowEntity>> CROW = register("crow",
        EntityTypeBuilder.of(
                CrowEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(CrowEntity::createCrowAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.5f, 0.6f)
            .eyeHeight(0.55f)
    );

    public static final Supplier<EntityType<DuckEntity>> DUCK = register("duck",
        EntityTypeBuilder.of(
                DuckEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(DuckEntity::createDuckAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.AQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnWaterfowl
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<GooseEntity>> GOOSE = register("goose",
        EntityTypeBuilder.of(
                GooseEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(GooseEntity::createGooseAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.AQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnWaterfowl
            )
            .sized(0.7f, 1.1f)
            .eyeHeight(1.0f)
    );

    public static final Supplier<EntityType<GullEntity>> GULL = register("gull",
        EntityTypeBuilder.of(
                GullEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(GullEntity::createGullAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.SEMIAQUATIC,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                FlyingBirdEntity::canSpawnShorebirds
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<HawkEntity>> HAWK = register("hawk",
        EntityTypeBuilder.of(
                HawkEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(HawkEntity::createHawkAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<PenguinEntity>> PENGUIN = register("penguin",
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
            .sized(0.5f, 1.4f)
            .eyeHeight(1.35f)
            .passengerAttachments(new Vec3(0, 0.75, -0.1))
    );

    public static final Supplier<EntityType<PigeonEntity>> PIGEON = register("pigeon",
        EntityTypeBuilder.of(
                PigeonEntity::new,
                CustomMobCategory.BIRDS.mobCategory
            )
            .attributes(PigeonEntity::createPigeonAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnShorebirds
            )
            .sized(0.5f, 0.6f)
            .eyeHeight(0.5f)
    );

    public static final Supplier<EntityType<RavenEntity>> RAVEN = register("raven",
        EntityTypeBuilder.of(
                RavenEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(RavenEntity::createRavenAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.6f, 0.8f)
            .eyeHeight(0.7f)
    );

    public static final Supplier<EntityType<RobinEntity>> ROBIN = register("robin",
        EntityTypeBuilder.of(
                RobinEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(RobinEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.4f, 0.55f)
            .eyeHeight(0.475f)
    );

    public static final Supplier<EntityType<SparrowEntity>> SPARROW = register("sparrow",
        EntityTypeBuilder.of(
                SparrowEntity::new,
                CustomMobCategory.AMBIENT_BIRDS.mobCategory
            )
            .attributes(SparrowEntity::createFlyingBirdAttributes)
            .spawnRestriction(
                CustomSpawnPlacementTypes.GROUND,
                Heightmap.Types.MOTION_BLOCKING,
                FlyingBirdEntity::canSpawnPasserines
            )
            .sized(0.3f, 0.45f)
            .eyeHeight(0.4f)
    );

    public static final Supplier<EntityType<ScarecrowEntity>> SCARECROW = register("scarecrow",
        EntityTypeBuilder.of(
                ScarecrowEntity::new,
                MobCategory.MISC
            )
            .attributes(ScarecrowEntity::createScarecrowAttributes)
            .sized(0.6f, 2.0f)
            .eyeHeight(1.72f)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityTypeBuilder<T> builder) {
        return PlatformHelper.registerEntityType(id, () -> builder.build(id));
    }

    public static void init() {
        // Spawn Weights
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_BLUE_JAYS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.BLUE_JAY,
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_CARDINALS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.CARDINAL,
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_CHICKADEES,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.CHICKADEE,
            FowlPlayConfig.getInstance().chickadeeSpawnWeight,
            FowlPlayConfig.getInstance().chickadeeMinGroupSize,
            FowlPlayConfig.getInstance().chickadeeMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_CROWS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.CROW,
            FowlPlayConfig.getInstance().crowSpawnWeight,
            FowlPlayConfig.getInstance().crowMinGroupSize,
            FowlPlayConfig.getInstance().crowMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.DUCK,
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_GEESE,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.GOOSE,
            FowlPlayConfig.getInstance().gooseSpawnWeight,
            FowlPlayConfig.getInstance().gooseMinGroupSize,
            FowlPlayConfig.getInstance().gooseMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_GULLS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.GULL,
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_HAWKS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.HAWK,
            FowlPlayConfig.getInstance().hawkSpawnWeight,
            FowlPlayConfig.getInstance().hawkMinGroupSize,
            FowlPlayConfig.getInstance().hawkMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_PENGUINS,
            MobCategory.CREATURE,
            FowlPlayEntityTypes.PENGUIN,
            FowlPlayConfig.getInstance().penguinSpawnWeight,
            FowlPlayConfig.getInstance().penguinMinGroupSize,
            FowlPlayConfig.getInstance().penguinMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_PIGEONS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.PIGEON,
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_RAVENS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.RAVEN,
            FowlPlayConfig.getInstance().ravenSpawnWeight,
            FowlPlayConfig.getInstance().ravenMinGroupSize,
            FowlPlayConfig.getInstance().ravenMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_ROBINS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.ROBIN,
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        addSpawn(
            FowlPlayBiomeTags.SPAWNS_SPARROWS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.SPARROW,
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );

        // Spawn Costs
        setSpawnCost(
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            FowlPlayEntityTypes.DUCK,
            0.8,
            0.1
        );
        setSpawnCost(
            FowlPlayBiomeTags.SPAWNS_GULLS,
            FowlPlayEntityTypes.GULL,
            1,
            0.07
        );
    }

    // TODO: use biome property based spawning to more accurately reflect real life habitats
    // doesn't work on neoforge apparently
    public static <T extends Entity> void addSpawn(TagKey<Biome> tag, MobCategory spawnGroup, Supplier<EntityType<T>> type, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addProperties(
            context -> context.hasTag(tag),
            (context, mutable) -> mutable.getSpawnProperties().addSpawn(
                spawnGroup,
                new MobSpawnSettings.SpawnerData(
                    type.get(),
                    weight,
                    minGroupSize,
                    maxGroupSize
                )
            )
        );
    }

    public static <T extends Entity> void setSpawnCost(TagKey<Biome> tag, Supplier<EntityType<T>> type, double gravityLimit, double mass) {
        BiomeModifications.addProperties(
            context -> context.hasTag(tag),
            (context, mutable) -> mutable.getSpawnProperties().setSpawnCost(
                type.get(),
                new MobSpawnSettings.MobSpawnCost(
                    gravityLimit,
                    mass
                )
            )
        );
    }
}
