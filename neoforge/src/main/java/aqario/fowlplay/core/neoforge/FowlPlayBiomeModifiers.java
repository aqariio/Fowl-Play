package aqario.fowlplay.core.neoforge;

import aqario.fowlplay.common.world.gen.neoforge.AddBirdsBiomeModifier;
import aqario.fowlplay.core.FowlPlay;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FowlPlayBiomeModifiers {
    public static DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(
        NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
        FowlPlay.ID
    );

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<AddBirdsBiomeModifier>> ADD_BIRDS_CODEC = BIOME_MODIFIER_SERIALIZERS.register(
        "add_birds",
        () -> MapCodec.unit(AddBirdsBiomeModifier::new)
    );
}
