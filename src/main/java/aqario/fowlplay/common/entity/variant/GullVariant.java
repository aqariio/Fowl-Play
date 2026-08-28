package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

public record GullVariant(String name) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GullVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(
        FPRegistries.GULL_VARIANT
    );
    public static final CommonRegister<GullVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.GULL_VARIANT,
        FowlPlay.ID
    );
    public static final ResourceKey<GullVariant> HERRING = register("herring");
    public static final ResourceKey<GullVariant> RING_BILLED = register("ring_billed");
    public static final ResourceKey<GullVariant> BLACK_BACKED = register("black_backed");

    private static ResourceKey<GullVariant> register(String name) {
        ResourceKey<GullVariant> key = ResourceKey.create(FPRegistries.GULL_VARIANT, FowlPlay.id(name));
        REGISTRAR.register(name, () -> new GullVariant(name));
        return key;
    }
}
