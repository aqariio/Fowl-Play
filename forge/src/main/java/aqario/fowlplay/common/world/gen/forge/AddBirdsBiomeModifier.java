package aqario.fowlplay.common.world.gen.forge;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.forge.FowlPlayBiomeModifiers;
import aqario.fowlplay.core.platform.CustomSpawnGroup;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class AddBirdsBiomeModifier implements BiomeModifier {
    @Override
    public void modify(RegistryEntry<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if(!phase.equals(Phase.ADD)) {
            return;
        }

        // Spawns
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_BLUE_JAYS,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.BLUE_JAY.get(),
            FowlPlayConfig.getInstance().blueJaySpawnWeight,
            FowlPlayConfig.getInstance().blueJayMinGroupSize,
            FowlPlayConfig.getInstance().blueJayMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_CARDINALS,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.CARDINAL.get(),
            FowlPlayConfig.getInstance().cardinalSpawnWeight,
            FowlPlayConfig.getInstance().cardinalMinGroupSize,
            FowlPlayConfig.getInstance().cardinalMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_CHICKADEES,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.CHICKADEE.get(),
            FowlPlayConfig.getInstance().chickadeeSpawnWeight,
            FowlPlayConfig.getInstance().chickadeeMinGroupSize,
            FowlPlayConfig.getInstance().chickadeeMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_CROWS,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.CROW.get(),
            FowlPlayConfig.getInstance().crowSpawnWeight,
            FowlPlayConfig.getInstance().crowMinGroupSize,
            FowlPlayConfig.getInstance().crowMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            CustomSpawnGroup.birds(),
            FowlPlayEntityType.DUCK.get(),
            FowlPlayConfig.getInstance().duckSpawnWeight,
            FowlPlayConfig.getInstance().duckMinGroupSize,
            FowlPlayConfig.getInstance().duckMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_GULLS,
            CustomSpawnGroup.birds(),
            FowlPlayEntityType.GULL.get(),
            FowlPlayConfig.getInstance().gullSpawnWeight,
            FowlPlayConfig.getInstance().gullMinGroupSize,
            FowlPlayConfig.getInstance().gullMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_HAWKS,
            CustomSpawnGroup.birds(),
            FowlPlayEntityType.HAWK.get(),
            FowlPlayConfig.getInstance().hawkSpawnWeight,
            FowlPlayConfig.getInstance().hawkMinGroupSize,
            FowlPlayConfig.getInstance().hawkMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_PENGUINS,
            SpawnGroup.CREATURE,
            FowlPlayEntityType.PENGUIN.get(),
            FowlPlayConfig.getInstance().penguinSpawnWeight,
            FowlPlayConfig.getInstance().penguinMinGroupSize,
            FowlPlayConfig.getInstance().penguinMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_PIGEONS,
            CustomSpawnGroup.birds(),
            FowlPlayEntityType.PIGEON.get(),
            FowlPlayConfig.getInstance().pigeonSpawnWeight,
            FowlPlayConfig.getInstance().pigeonMinGroupSize,
            FowlPlayConfig.getInstance().pigeonMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_RAVENS,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.RAVEN.get(),
            FowlPlayConfig.getInstance().ravenSpawnWeight,
            FowlPlayConfig.getInstance().ravenMinGroupSize,
            FowlPlayConfig.getInstance().ravenMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_ROBINS,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.ROBIN.get(),
            FowlPlayConfig.getInstance().robinSpawnWeight,
            FowlPlayConfig.getInstance().robinMinGroupSize,
            FowlPlayConfig.getInstance().robinMaxGroupSize
        );
        addSpawn(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_SPARROWS,
            CustomSpawnGroup.ambientBirds(),
            FowlPlayEntityType.SPARROW.get(),
            FowlPlayConfig.getInstance().sparrowSpawnWeight,
            FowlPlayConfig.getInstance().sparrowMinGroupSize,
            FowlPlayConfig.getInstance().sparrowMaxGroupSize
        );

        // Spawn Costs
        setSpawnCost(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_DUCKS,
            FowlPlayEntityType.DUCK.get(),
            0.8,
            0.1
        );
        setSpawnCost(
            builder,
            biome,
            FowlPlayBiomeTags.SPAWNS_GULLS,
            FowlPlayEntityType.GULL.get(),
            1,
            0.07
        );
    }

    private static void addSpawn(ModifiableBiomeInfo.BiomeInfo.Builder builder, RegistryEntry<Biome> biome, TagKey<Biome> tag, SpawnGroup spawnGroup, EntityType<?> entityType, int weight, int minGroupSize, int maxGroupSize) {
        if(biome.isIn(tag)) {
            builder.getMobSpawnSettings().spawn(spawnGroup, new SpawnSettings.SpawnEntry(entityType, weight, minGroupSize, maxGroupSize));
        }
    }

    private static void setSpawnCost(ModifiableBiomeInfo.BiomeInfo.Builder builder, RegistryEntry<Biome> biome, TagKey<Biome> tag, EntityType<?> entityType, double gravityLimit, double mass) {
        if(biome.isIn(tag)) {
            builder.getMobSpawnSettings().spawnCost(entityType, gravityLimit, mass);
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return FowlPlayBiomeModifiers.ADD_BIRDS_CODEC.get();
    }
}
