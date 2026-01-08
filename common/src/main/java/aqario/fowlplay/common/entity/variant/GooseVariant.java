package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;

public class GooseVariant extends EntityVariant {
    private final ModelType modelType;
    private final Optional<ResourceLocation> domesticId;
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GooseVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(FowlPlayRegistries.GOOSE_VARIANT);
    public static final ResourceKey<GooseVariant> CANADA = registerWild("canada");
    public static final ResourceKey<GooseVariant> GREYLAG = registerWild("greylag", "emden");
    public static final ResourceKey<GooseVariant> SWAN = registerWild("swan", "chinese");
    public static final ResourceKey<GooseVariant> EMDEN = registerDomestic("emden");
    public static final ResourceKey<GooseVariant> CHINESE = registerDomestic("chinese");

    public GooseVariant(EntityType<?> entityType, String name, ModelType modelType, Optional<ResourceLocation> domesticId) {
        super(entityType, name);
        this.modelType = modelType;
        this.domesticId = domesticId;
    }

    public ModelType modelType() {
        return this.modelType;
    }

    public Optional<ResourceLocation> domesticId() {
        return this.domesticId;
    }

    public ResourceLocation texture(boolean isBaby) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/goose/")
            .addIf("baby_", isBaby)
            .add(this.name())
            .add("_goose.png")
        );
    }

    private static ResourceKey<GooseVariant> registerWild(String id) {
        return register(id, ModelType.WILD, Optional.empty());
    }

    private static ResourceKey<GooseVariant> registerWild(String id, String domesticId) {
        return register(id, ModelType.WILD, Optional.of(domesticId));
    }

    private static ResourceKey<GooseVariant> registerDomestic(String id) {
        return register(id, ModelType.DOMESTIC, Optional.empty());
    }

    private static ResourceKey<GooseVariant> register(String id, ModelType modelType, Optional<String> domesticId) {
        ResourceKey<GooseVariant> key = ResourceKey.create(FowlPlayRegistries.GOOSE_VARIANT, FowlPlay.id(id));
        PlatformHelper.registerVariant(id, key, () -> new GooseVariant(
            FowlPlayEntityTypes.GOOSE.get(),
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
