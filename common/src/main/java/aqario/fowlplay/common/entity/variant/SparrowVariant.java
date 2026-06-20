package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record SparrowVariant(ResourceLocation texture) {
    public static final CommonRegister<SparrowVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.SPARROW_VARIANT,
        FowlPlay.ID
    );
    public static final Supplier<SparrowVariant> BROWN = register("brown");
    public static final Supplier<SparrowVariant> PALE = register("pale");

    private static Supplier<SparrowVariant> register(String id) {
        ResourceLocation texture = FowlPlay.id("textures/entity/sparrow/" + id + "_sparrow.png");
        return REGISTRAR.register(id, () -> new SparrowVariant(texture));
    }
}
