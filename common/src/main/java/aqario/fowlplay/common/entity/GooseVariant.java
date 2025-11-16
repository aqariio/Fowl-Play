package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public record GooseVariant(ResourceLocation texture, ModelType modelType) {
    public static final Supplier<GooseVariant> GREYLAG = register("greylag", ModelType.WILD);
    public static final Supplier<GooseVariant> CANADA = register("canada", ModelType.WILD);
    public static final Supplier<GooseVariant> SWAN = register("swan", ModelType.WILD);
    public static final Supplier<GooseVariant> EMDEN = register("emden", ModelType.DOMESTIC);
    public static final Supplier<GooseVariant> CHINESE = register("chinese", ModelType.DOMESTIC);

    private static Supplier<GooseVariant> register(String id, ModelType modelType) {
        ResourceLocation texture = FowlPlay.id("textures/entity/goose/" + id + "_goose.png");
        return PlatformHelper.registerVariant(id, () -> new GooseVariant(texture, modelType));
    }

    public static void init() {
    }

    public enum ModelType implements StringRepresentable {
        WILD("wild"),
        DOMESTIC("modelType");

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
