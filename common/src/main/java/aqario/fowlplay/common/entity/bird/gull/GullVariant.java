package aqario.fowlplay.common.entity.bird.gull;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record GullVariant(ResourceLocation texture) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GullVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.GULL_VARIANT);
    public static final ResourceKey<GullVariant> HERRING = register("herring");
    public static final ResourceKey<GullVariant> RING_BILLED = register("ring_billed");
    public static final ResourceKey<GullVariant> BLACK_BACKED = register("black_backed");

    private static ResourceKey<GullVariant> register(String id) {
        ResourceKey<GullVariant> key = ResourceKey.create(FowlPlayRegistries.GULL_VARIANT, FowlPlay.id(id));
        ResourceLocation texture = FowlPlay.id("textures/entity/gull/" + key.location().getPath() + "_gull.png");
        PlatformHelper.registerVariant(id, key, () -> new GullVariant(texture));
        return key;
    }

    public static void init() {
    }
}
