package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;
import java.util.function.Supplier;

public record GooseVariant(
    String id,
    ModelType modelType,
    Optional<ResourceLocation> domesticId
) {
    public static final Supplier<GooseVariant> CANADA = registerWild("canada");
    public static final Supplier<GooseVariant> GREYLAG = registerWild("greylag", "emden");
    public static final Supplier<GooseVariant> SWAN = registerWild("swan", "chinese");
    public static final Supplier<GooseVariant> EMDEN = registerDomestic("emden");
    public static final Supplier<GooseVariant> CHINESE = registerDomestic("chinese");

    public ResourceLocation texture(boolean isBaby) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/goose/")
            .addIf("baby_", isBaby)
            .add(this.id)
            .add("_goose.png")
        );
    }

    private static Supplier<GooseVariant> registerWild(String id) {
        return register(id, ModelType.WILD, Optional.empty());
    }

    private static Supplier<GooseVariant> registerWild(String id, String domesticId) {
        return register(id, ModelType.WILD, Optional.of(domesticId));
    }

    private static Supplier<GooseVariant> registerDomestic(String id) {
        return register(id, ModelType.DOMESTIC, Optional.empty());
    }

    private static Supplier<GooseVariant> register(String id, ModelType modelType, Optional<String> domesticId) {
        return PlatformHelper.registerVariant(id, () -> new GooseVariant(
            id,
            modelType,
            domesticId.map(FowlPlay::id)
        ));
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
