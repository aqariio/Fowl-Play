package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.function.Supplier;

public record DuckVariant(
    String id
) {
    public static final CommonRegister<DuckVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.DUCK_VARIANT,
        FowlPlay.ID
    );
    public static final Supplier<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final Supplier<DuckVariant> BROWN = register("brown");

    public ResourceLocation texture(boolean isBaby, boolean isDomestic) {
        return FowlPlay.id(new ResourcePathBuilder()
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

    private static Supplier<DuckVariant> register(String id) {
        return REGISTRAR.register(id, () -> new DuckVariant(id));
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
