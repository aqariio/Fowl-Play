package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record SparrowVariant(ResourceLocation texture) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SparrowVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(
        FowlPlayRegistries.SPARROW_VARIANT
    );
    public static final CommonRegister<SparrowVariant> REGISTRAR = CommonRegister.create(
        FowlPlayRegistries.SPARROW_VARIANT,
        FowlPlay.ID
    );
    public static final ResourceKey<SparrowVariant> BROWN = register("brown");
    public static final ResourceKey<SparrowVariant> PALE = register("pale");

    private static ResourceKey<SparrowVariant> register(String id) {
        ResourceKey<SparrowVariant> key = ResourceKey.create(FowlPlayRegistries.SPARROW_VARIANT, FowlPlay.id(id));
        ResourceLocation texture = FowlPlay.id("textures/entity/sparrow/" + key.location().getPath() + "_sparrow.png");
        REGISTRAR.register(id, () -> new SparrowVariant(texture));
        return key;
    }
}
