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

public record GooseVariant(
    String id,
    boolean domesticatable
) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GooseVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.GOOSE_VARIANT);
    public static final ResourceKey<GooseVariant> CANADA = register("canada", false);
    public static final ResourceKey<GooseVariant> GREYLAG = register("greylag", true);
    public static final ResourceKey<GooseVariant> SWAN = register("swan", true);

    public ResourceLocation texture(boolean isBaby, boolean isDomestic) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/goose/")
            .addIf("baby_", isBaby)
            .addIf("domestic_", this.domesticatable && isDomestic)
            .add(this.id)
            .add("_goose.png")
        );
    }

    public ModelType modelType(boolean isDomestic) {
        return this.domesticatable && isDomestic
            ? ModelType.DOMESTIC
            : ModelType.WILD;
    }

    private static ResourceKey<GooseVariant> register(String id, boolean domesticatable) {
        ResourceKey<GooseVariant> key = ResourceKey.create(FowlPlayRegistries.GOOSE_VARIANT, FowlPlay.id(id));
        PlatformHelper.registerVariant(id, key, () -> new GooseVariant(
            id,
            domesticatable
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
