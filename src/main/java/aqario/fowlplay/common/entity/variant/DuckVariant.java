package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public record DuckVariant(
    String name
) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DuckVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(
        FPRegistries.DUCK_VARIANT
    );
    public static final CommonRegister<DuckVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.DUCK_VARIANT,
        FowlPlay.ID
    );
    public static final ResourceKey<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final ResourceKey<DuckVariant> BROWN = register("brown");

    public ResourceLocation texture(boolean isBaby, boolean isDomestic) {
        return FowlPlay.id(new ResourcePathBuilder()
            .add("textures/entity/duck/")
            .addIf("baby_", isBaby)
            .addIf("pekin_", isDomestic)
            .addIf(this.name, !isDomestic)
            .add("_duck.png")
        );
    }

    public ModelType modelType(boolean isDomestic) {
        return isDomestic
            ? ModelType.DOMESTIC
            : ModelType.WILD;
    }

    private static ResourceKey<DuckVariant> register(String name) {
        ResourceKey<DuckVariant> key = ResourceKey.create(FPRegistries.DUCK_VARIANT, FowlPlay.id(name));
        REGISTRAR.register(name, () -> new DuckVariant(name));
        return key;
    }

    public enum ModelType implements StringRepresentable {
        WILD("wild"),
        DOMESTIC("domestic");

        private final String name;

        ModelType(final String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
