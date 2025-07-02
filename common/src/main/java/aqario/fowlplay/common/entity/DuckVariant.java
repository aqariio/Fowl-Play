package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public record DuckVariant(Identifier texture) {
    public static final Supplier<DuckVariant> GREEN_HEADED = register("green_headed");
    public static final Supplier<DuckVariant> BROWN = register("brown");

    private static Supplier<DuckVariant> register(String id) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/duck/" + id + "_duck.png");
        return PlatformHelper.registerVariant(id, () -> new DuckVariant(texture));
    }

    public static void init() {
    }
}
