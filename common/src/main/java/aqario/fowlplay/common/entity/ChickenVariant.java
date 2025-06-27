package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.platform.PlatformHelper;

public record ChickenVariant(String id) {
    public static final ChickenVariant WHITE = register("white");
    public static final ChickenVariant RED_JUNGLEFOWL = register("red_junglefowl");

    private static ChickenVariant register(String id) {
        return PlatformHelper.registerVariant(id, () -> new ChickenVariant(id));
    }

    public static void init() {
    }
}
