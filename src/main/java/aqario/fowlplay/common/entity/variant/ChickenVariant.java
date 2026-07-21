package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.common.util.ResourcePathBuilder;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record ChickenVariant(String id) {
    public static final CommonRegister<ChickenVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.CHICKEN_VARIANT,
        FowlPlay.ID
    );
    public static final Supplier<ChickenVariant> WHITE = register("white");
    public static final Supplier<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    public ResourceLocation texture(boolean isBaby) {
        return FowlPlay.id(new ResourcePathBuilder()
            .add("textures/entity/chicken/")
            .addIf("baby_", isBaby)
            .add(this.id)
            .add("_chicken.png")
        );
    }

    private static Supplier<ChickenVariant> register(String id) {
        return REGISTRAR.register(id, () -> new ChickenVariant(id));
    }
}
