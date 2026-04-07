package aqario.fowlplay.common.worldgen.neoforge;

import aqario.fowlplay.core.FowlPlay;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Objects;

public class BiomeModifierImpl extends aqario.fowlplay.common.worldgen.BiomeModifier /* >:( */ {
    public static DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(
        NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
        FowlPlay.ID
    );
    private static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<CommonBiomeModifier>> CODEC = BIOME_MODIFIER_SERIALIZERS.register(
        "biome_modifier",
        () -> MapCodec.unit(CommonBiomeModifier::new)
    );

    public static void register() {
        BIOME_MODIFIER_SERIALIZERS.register(Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()));
    }

    public static class CommonBiomeModifier implements BiomeModifier {
        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if(phase == Phase.ADD) {
                applyModifications(new NeoForgeContext(biome), new NeoForgeModifier(builder));
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CODEC.get();
        }
    }

    public static class NeoForgeContext implements Context {
        private final Holder<Biome> biome;

        private NeoForgeContext(Holder<Biome> biome) {
            this.biome = biome;
        }

        @Override
        public ResourceKey<Biome> key() {
            return this.biome.unwrapKey().orElseThrow();
        }

        @Override
        public boolean is(TagKey<Biome> tag) {
            return this.biome.is(tag);
        }

        @Override
        public boolean is(ResourceKey<Biome> biome) {
            return this.key() == biome;
        }
    }

    public static class NeoForgeModifier extends Modifier {
        private final ModifiableBiomeInfo.BiomeInfo.Builder builder;

        private NeoForgeModifier(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            this.builder = builder;
        }

        @Override
        public void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData data) {
            this.builder.getMobSpawnSettings().addSpawn(category, data);
        }

        @Override
        public void removeSpawn(EntityType<?> type) {
            this.builder.getMobSpawnSettings()
                .getSpawner(type.getCategory())
                .removeIf(spawner -> spawner.type == type);
        }

        @Override
        public void setSpawnCost(EntityType<?> type, MobSpawnSettings.MobSpawnCost cost) {
            this.builder.getMobSpawnSettings().addMobCharge(type, cost.charge(), cost.energyBudget());
        }
    }
}
