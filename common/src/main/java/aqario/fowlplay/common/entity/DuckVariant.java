package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.util.PathBuilder;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;
import java.util.function.Supplier;

public record DuckVariant(
    String id,
    ModelType modelType,
    Optional<ResourceLocation> domesticId
) {
    public static final Supplier<DuckVariant> GREEN_HEADED = registerWild("green_headed", "pekin");
    public static final Supplier<DuckVariant> BROWN = registerWild("brown", "pekin");
    public static final Supplier<DuckVariant> PEKIN = registerDomestic("pekin");

    public ResourceLocation texture(boolean isBaby) {
        return FowlPlay.id(new PathBuilder()
            .add("textures/entity/duck/")
            .addIf("baby_", isBaby)
            .add(this.id)
            .add("_duck.png")
        );
    }

    private static Supplier<DuckVariant> registerWild(String id) {
        return register(id, ModelType.WILD, Optional.empty());
    }

    private static Supplier<DuckVariant> registerWild(String id, String domesticId) {
        return register(id, ModelType.WILD, Optional.of(domesticId));
    }

    private static Supplier<DuckVariant> registerDomestic(String id) {
        return register(id, ModelType.DOMESTIC, Optional.empty());
    }

    private static Supplier<DuckVariant> register(String id, ModelType modelType, Optional<String> domesticId) {
        return PlatformHelper.registerVariant(id, () -> new DuckVariant(
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
