package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public record SparrowVariant(ResourceLocation texture) {
    public static final Supplier<SparrowVariant> BROWN = register("brown");
    public static final Supplier<SparrowVariant> PALE = register("pale");

    private static Supplier<SparrowVariant> register(String id) {
        ResourceLocation texture = FowlPlay.id("textures/entity/sparrow/" + id + "_sparrow.png");
        return PlatformHelper.registerVariant(id, () -> new SparrowVariant(texture));
    }

    public static void init() {
    }
}
