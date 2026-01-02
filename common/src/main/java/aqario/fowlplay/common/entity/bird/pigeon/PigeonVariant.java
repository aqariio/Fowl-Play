package aqario.fowlplay.common.entity.bird.pigeon;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record PigeonVariant(ResourceLocation texture) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PigeonVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.PIGEON_VARIANT);
    public static final ResourceKey<PigeonVariant> BANDED = register("banded");
    public static final ResourceKey<PigeonVariant> CHECKERED = register("checkered");
    public static final ResourceKey<PigeonVariant> GRAY = register("gray");
    public static final ResourceKey<PigeonVariant> RUSTY = register("rusty");
    public static final ResourceKey<PigeonVariant> WHITE = register("white");

    private static ResourceKey<PigeonVariant> register(String id) {
        ResourceKey<PigeonVariant> key = ResourceKey.create(FowlPlayRegistries.PIGEON_VARIANT, FowlPlay.id(id));
        ResourceLocation texture = FowlPlay.id("textures/entity/pigeon/" + key.location().getPath() + "_pigeon.png");
        PlatformHelper.registerVariant(id, key, () -> new PigeonVariant(texture));
        return key;
    }

    public static void init() {
    }
}
