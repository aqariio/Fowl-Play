package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public record SparrowVariant(Identifier texture) {
    public static final Supplier<SparrowVariant> BROWN = register("brown");
    public static final Supplier<SparrowVariant> PALE = register("pale");

    private static Supplier<SparrowVariant> register(String id) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/sparrow/" + id + "_sparrow.png");
        return PlatformHelper.registerVariant(id, () -> new SparrowVariant(texture));
    }

    public static void init() {
    }
}
