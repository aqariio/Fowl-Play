package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record DuckVariant(ResourceLocation texture) {
    public static final Supplier<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final Supplier<DuckVariant> BROWN = register("brown");

    private static Supplier<DuckVariant> register(String id) {
        ResourceLocation texture = FowlPlay.id("textures/entity/duck/" + id + "_duck.png");
        return PlatformHelper.registerVariant(id, () -> new DuckVariant(texture));
    }

    public static void init() {
    }
}
