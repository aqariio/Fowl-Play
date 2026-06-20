package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record PigeonVariant(ResourceLocation texture) {
    public static final CommonRegister<PigeonVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.PIGEON_VARIANT,
        FowlPlay.ID
    );
    public static final Supplier<PigeonVariant> BANDED = register("banded");
    public static final Supplier<PigeonVariant> CHECKERED = register("checkered");
    public static final Supplier<PigeonVariant> GRAY = register("gray");
    public static final Supplier<PigeonVariant> RUSTY = register("rusty");
    public static final Supplier<PigeonVariant> WHITE = register("white");

    private static Supplier<PigeonVariant> register(String id) {
        ResourceLocation texture = FowlPlay.id("textures/entity/pigeon/" + id + "_pigeon.png");
        return REGISTRAR.register(id, () -> new PigeonVariant(texture));
    }
}
