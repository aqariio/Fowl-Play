package aqario.fowlplay.common.entity.variant;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.core.FPRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record GullVariant(ResourceLocation texture) {
    public static final CommonRegister<GullVariant> REGISTRAR = CommonRegister.create(
        FPRegistries.GULL_VARIANT,
        FowlPlay.ID
    );
    public static final Supplier<GullVariant> HERRING = register("herring");
    public static final Supplier<GullVariant> RING_BILLED = register("ring_billed");
    public static final Supplier<GullVariant> BLACK_BACKED = register("black_backed");

    private static Supplier<GullVariant> register(String id) {
        ResourceLocation texture = FowlPlay.id("textures/entity/gull/" + id + "_gull.png");
        return REGISTRAR.register(id, () -> new GullVariant(texture));
    }
}
