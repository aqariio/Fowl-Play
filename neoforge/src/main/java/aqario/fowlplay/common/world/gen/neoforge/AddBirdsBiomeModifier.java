package aqario.fowlplay.common.world.gen.neoforge;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.bird.CustomMobCategory;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.neoforge.FowlPlayBiomeModifiers;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

public class AddBirdsBiomeModifier implements BiomeModifier {
    @Override
    public void modify(@NotNull Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if(!phase.equals(Phase.ADD)) {
            return;
        }

        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_BLUE_JAYS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.BLUE_JAY.get(),
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_CARDINALS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.CARDINAL.get(),
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_CHICKADEES,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.CHICKADEE.get(),
            FowlPlayConfig.getInstance().chickadeeSpawnWeight,
            FowlPlayConfig.getInstance().chickadeeMinGroupSize,
            FowlPlayConfig.getInstance().chickadeeMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_CROWS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.CROW.get(),
            FowlPlayConfig.getInstance().crowSpawnWeight,
            FowlPlayConfig.getInstance().crowMinGroupSize,
            FowlPlayConfig.getInstance().crowMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.DUCK.get(),
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_GEESE,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.GOOSE.get(),
            FowlPlayConfig.getInstance().gooseSpawnWeight,
            FowlPlayConfig.getInstance().gooseMinGroupSize,
            FowlPlayConfig.getInstance().gooseMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_GULLS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.GULL.get(),
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_HAWKS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.HAWK.get(),
            FowlPlayConfig.getInstance().hawkSpawnWeight,
            FowlPlayConfig.getInstance().hawkMinGroupSize,
            FowlPlayConfig.getInstance().hawkMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_PENGUINS,
            MobCategory.CREATURE,
            FowlPlayEntityTypes.PENGUIN.get(),
            FowlPlayConfig.getInstance().penguinSpawnWeight,
            FowlPlayConfig.getInstance().penguinMinGroupSize,
            FowlPlayConfig.getInstance().penguinMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_PIGEONS,
            CustomMobCategory.BIRDS.mobCategory,
            FowlPlayEntityTypes.PIGEON.get(),
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_RAVENS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.RAVEN.get(),
            FowlPlayConfig.getInstance().ravenSpawnWeight,
            FowlPlayConfig.getInstance().ravenMinGroupSize,
            FowlPlayConfig.getInstance().ravenMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_ROBINS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.ROBIN.get(),
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_SPARROWS,
            CustomMobCategory.AMBIENT_BIRDS.mobCategory,
            FowlPlayEntityTypes.SPARROW.get(),
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );

        // Spawn Costs
        setSpawnCost(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            FowlPlayEntityTypes.DUCK.get(),
            0.8,
            0.1
        );
        setSpawnCost(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_GULLS,
            FowlPlayEntityTypes.GULL.get(),
            1,
            0.07
        );
    }

    private static void addSpawn(ModifiableBiomeInfo.BiomeInfo.Builder builder, Holder<Biome> biome, TagKey<Biome> tag, MobCategory spawnGroup, EntityType<?> entityType, int weight, int minGroupSize, int maxGroupSize) {
        if(biome.is(tag)) {
            builder.getMobSpawnSettings().addSpawn(spawnGroup, new MobSpawnSettings.SpawnerData(entityType, weight, minGroupSize, maxGroupSize));
        }
    }

    private static void setSpawnCost(ModifiableBiomeInfo.BiomeInfo.Builder builder, Holder<Biome> biome, TagKey<Biome> tag, EntityType<?> entityType, double gravityLimit, double mass) {
        if(biome.is(tag)) {
            builder.getMobSpawnSettings().addMobCharge(entityType, gravityLimit, mass);
        }
    }

    @NotNull
    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return FowlPlayBiomeModifiers.ADD_BIRDS_CODEC.get();
    }
}
