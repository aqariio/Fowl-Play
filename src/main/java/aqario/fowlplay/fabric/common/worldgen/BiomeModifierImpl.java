//? if fabric {
package aqario.fowlplay.fabric.common.worldgen;

import aqario.fowlplay.common.worldgen.BiomeModifier;
import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BiomeModifierImpl extends BiomeModifier {
    public static void register() {
        BiomeModifications.create(FowlPlay.id("biome_modifier")).add(
            ModificationPhase.ADDITIONS,
            context -> true,
            (selector, modifier) -> applyModifications(
                new FabricContext(selector),
                new FabricModifier(modifier)
            )
        );
    }

    public static class FabricContext implements Context {
        private final BiomeSelectionContext selector;

        private FabricContext(BiomeSelectionContext selector) {
            this.selector = selector;
        }

        @Override
        public ResourceKey<Biome> key() {
            return this.selector.getBiomeKey();
        }

        @Override
        public boolean is(TagKey<Biome> tag) {
            return this.selector.hasTag(tag);
        }

        @Override
        public boolean is(ResourceKey<Biome> biome) {
            return this.key() == biome;
        }
    }

    public static class FabricModifier extends Modifier {
        private final BiomeModificationContext modifier;

        private FabricModifier(BiomeModificationContext modifier) {
            this.modifier = modifier;
        }

        @Override
        public void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData data) {
            this.modifier.getSpawnSettings().addSpawn(category, data);
        }

        @Override
        public void removeSpawn(EntityType<?> type) {
            this.modifier.getSpawnSettings().removeSpawnsOfEntityType(type);
        }

        @Override
        public void setSpawnCost(EntityType<?> type, MobSpawnSettings.MobSpawnCost cost) {
            this.modifier.getSpawnSettings().setSpawnCost(type, cost.charge(), cost.energyBudget());
        }
    }
}
//?}