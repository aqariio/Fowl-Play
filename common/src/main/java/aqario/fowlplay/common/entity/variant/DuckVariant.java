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

import java.util.Optional;

public record DuckVariant(
    String id,
    ModelType modelType,
    Optional<ResourceLocation> domesticId
) {
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DuckVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.DUCK_VARIANT);
    public static final ResourceKey<DuckVariant> GREEN_HEADED = registerWild("green_headed", "pekin");
    public static final ResourceKey<DuckVariant> BROWN = registerWild("brown", "pekin");
    public static final ResourceKey<DuckVariant> PEKIN = registerDomestic("pekin");

    public ResourceLocation texture(boolean isBaby) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/duck/")
            .addIf("baby_", isBaby)
            .add(this.id)
            .add("_duck.png")
        );
    }

    private static ResourceKey<DuckVariant> registerWild(String id) {
        return register(id, ModelType.WILD, Optional.empty());
    }

    private static ResourceKey<DuckVariant> registerWild(String id, String domesticId) {
        return register(id, ModelType.WILD, Optional.of(domesticId));
    }

    private static ResourceKey<DuckVariant> registerDomestic(String id) {
        return register(id, ModelType.DOMESTIC, Optional.empty());
    }

    private static ResourceKey<DuckVariant> register(String id, ModelType modelType, Optional<String> domesticId) {
        ResourceKey<DuckVariant> key = ResourceKey.create(FowlPlayRegistries.DUCK_VARIANT, FowlPlay.id(id));
        PlatformHelper.registerVariant(id, key, () -> new DuckVariant(
            id,
            modelType,
            domesticId.map(FowlPlay::id)
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
