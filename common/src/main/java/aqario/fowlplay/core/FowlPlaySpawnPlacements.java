package aqario.fowlplay.core;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.CustomMobCategory;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.world.BalmWorldGen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class FowlPlaySpawnPlacements {
    private static final BalmWorldGen WORLD_GEN = Balm.getWorldGen();

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
    public static <T extends Entity> void addSpawn(TagKey<Biome> tag, MobCategory spawnGroup, Holder<EntityType<T>> type, int weight, int minGroupSize, int maxGroupSize) {
        WORLD_GEN.modifyBiome(
            BuiltInRegistries.ENTITY_TYPE.getKey(type.value()),
            (key, biome) -> biome.is(tag),
            (biome, builder) -> builder.addSpawn(
                spawnGroup,
                new MobSpawnSettings.SpawnerData(
                    type.value(),
                    weight,
                    minGroupSize,
                    maxGroupSize
                )
            )
        );
    }

    public static <T extends Entity> void setSpawnCost(TagKey<Biome> tag, Holder<EntityType<T>> type, double gravityLimit, double mass) {
        WORLD_GEN.modifyBiome(
            BuiltInRegistries.ENTITY_TYPE.getKey(type.value()),
            (key, biome) -> biome.is(tag),
            (biome, builder) -> builder.setSpawnCost(
                type.value(),
                mass,
                gravityLimit
            )
        );
    }
}
