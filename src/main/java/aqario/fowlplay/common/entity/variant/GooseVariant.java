package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public record GooseVariant(
    String id,
    boolean domesticatable
) {
    public static final CommonRegister<GooseVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.GOOSE_VARIANT,
        FowlPlay.ID
    );
    public static final Supplier<GooseVariant> CANADA = register("canada", false);
    public static final Supplier<GooseVariant> GREYLAG = register("greylag", true);
    public static final Supplier<GooseVariant> SWAN = register("swan", true);

    public ResourceLocation texture(boolean isBaby, boolean isDomestic) {
        return FowlPlay.id(new ResourcePathBuilder()
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

    private static Supplier<GooseVariant> register(String id, boolean domesticatable) {
        return REGISTRAR.register(id, () -> new GooseVariant(id, domesticatable));
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
