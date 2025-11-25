package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record DuckVariant(ResourceLocation texture) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DuckVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.DUCK_VARIANT);
    public static final ResourceKey<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final ResourceKey<DuckVariant> BROWN = register("brown");

    private static ResourceKey<DuckVariant> register(String id) {
        ResourceKey<DuckVariant> key = ResourceKey.create(FowlPlayRegistries.DUCK_VARIANT, FowlPlay.id(id));
        ResourceLocation texture = FowlPlay.id("textures/entity/duck/" + key.location().getPath() + "_duck.png");
        PlatformHelper.registerVariant(id, key, () -> new DuckVariant(texture));
        return key;
    }

    public static void init() {
    }
}
