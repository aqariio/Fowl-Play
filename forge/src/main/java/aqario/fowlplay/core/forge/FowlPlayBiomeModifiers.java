package aqario.fowlplay.core.forge;

import aqario.fowlplay.common.world.gen.forge.AddBirdsBiomeModifier;
import aqario.fowlplay.core.FowlPlay;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FowlPlayBiomeModifiers {
    public static DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(
        ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
        FowlPlay.ID
    );

    public static final RegistryObject<Codec<AddBirdsBiomeModifier>> ADD_BIRDS_CODEC = BIOME_MODIFIER_SERIALIZERS.register("add_birds", () -> Codec.unit(AddBirdsBiomeModifier::new));
}
