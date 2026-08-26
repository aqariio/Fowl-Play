package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public record SparrowVariant(Identifier texture) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SparrowVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(
        FPRegistries.SPARROW_VARIANT
    );
    public static final CommonRegister<SparrowVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.SPARROW_VARIANT,
        FowlPlay.ID
    );
    public static final ResourceKey<SparrowVariant> BROWN = register("brown");
    public static final ResourceKey<SparrowVariant> PALE = register("pale");

    private static ResourceKey<SparrowVariant> register(String id) {
        ResourceKey<SparrowVariant> key = ResourceKey.create(FPRegistries.SPARROW_VARIANT, FowlPlay.id(id));
        Identifier texture = FowlPlay.id("textures/entity/sparrow/" + key.location().getPath() + "_sparrow.png");
        REGISTRAR.register(id, () -> new SparrowVariant(texture));
        return key;
    }
}
