package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.platform.PlatformHelper;

import java.util.function.Supplier;

public record ChickenVariant(String id) {
    public static final Supplier<ChickenVariant> WHITE = register("white");
    public static final Supplier<ChickenVariant> RED_JUNGLEFOWL = register("red_junglefowl");

    private static Supplier<ChickenVariant> register(String id) {
        return PlatformHelper.registerVariant(id, () -> new ChickenVariant(id));
    }

    public static void init() {
    }
}
