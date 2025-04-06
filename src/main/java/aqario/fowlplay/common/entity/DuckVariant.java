package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public record DuckVariant(Identifier texture) {
    public static final DuckVariant GREEN_HEADED = register("green_headed");
    public static final DuckVariant BROWN = register("brown");

    private static DuckVariant register(String id) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/duck/" + id + "_duck.png");
        return Registry.register(FowlPlayRegistries.DUCK_VARIANT, id, new DuckVariant(texture));
    }

    public static void init() {
    }
}
