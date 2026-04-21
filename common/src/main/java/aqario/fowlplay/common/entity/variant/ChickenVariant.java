package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record ChickenVariant(String id) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ChickenVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(
        FPRegistries.CHICKEN_VARIANT
    );
    public static final CommonRegister<ChickenVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.CHICKEN_VARIANT,
        FowlPlay.ID
    );
    public static final ResourceKey<ChickenVariant> WHITE = register("white");
    public static final ResourceKey<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    public ResourceLocation texture(boolean isBaby) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/chicken/")
            .addIf("baby_", isBaby)
            .add(this.id)
            .add("_chicken.png")
        );
    }

    private static ResourceKey<ChickenVariant> register(String id) {
        ResourceKey<ChickenVariant> key = ResourceKey.create(FPRegistries.CHICKEN_VARIANT, FowlPlay.id(id));
        REGISTRAR.register(id, () -> new ChickenVariant(id));
        return key;
    }
}
