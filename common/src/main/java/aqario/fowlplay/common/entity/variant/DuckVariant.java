package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public record DuckVariant(
    String id
) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DuckVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.DUCK_VARIANT);
    public static final ResourceKey<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final ResourceKey<DuckVariant> BROWN = register("brown");

    public ResourceLocation texture(boolean isBaby, boolean isDomestic) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/duck/")
            .addIf("baby_", isBaby)
            .addIf("pekin_", isDomestic)
            .addIf(this.id, !isDomestic)
            .add("_duck.png")
        );
    }

    public ModelType modelType(boolean isDomestic) {
        return isDomestic
            ? ModelType.DOMESTIC
            : ModelType.WILD;
    }

    private static ResourceKey<DuckVariant> register(String id) {
        ResourceKey<DuckVariant> key = ResourceKey.create(FowlPlayRegistries.DUCK_VARIANT, FowlPlay.id(id));
        PlatformHelper.registerVariant(id, key, () -> new DuckVariant(
            id
        ));
        return key;
    }

    public static void init() {
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
