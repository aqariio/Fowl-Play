package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.registry.FowlPlayRegistries;
import net.minecraft.registry.Registry;

public record ChickenVariant(String id) {
    public static final ChickenVariant WHITE = register("white");
    public static final ChickenVariant RED_JUNGLEFOWL = register("red_junglefowl");

    private static ChickenVariant register(String id) {
        return Registry.register(FowlPlayRegistries.CHICKEN_VARIANT, id, new ChickenVariant(id));
    }

    public static void init() {
    }
}
