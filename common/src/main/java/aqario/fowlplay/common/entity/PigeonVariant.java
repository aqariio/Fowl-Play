package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.util.Identifier;

public record PigeonVariant(Identifier texture) {
    public static final PigeonVariant BANDED = register("banded");
    public static final PigeonVariant CHECKERED = register("checkered");
    public static final PigeonVariant GRAY = register("gray");
    public static final PigeonVariant RUSTY = register("rusty");
    public static final PigeonVariant WHITE = register("white");

    private static PigeonVariant register(String id) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/pigeon/" + id + "_pigeon.png");
        return PlatformHelper.registerVariant(id, () -> new PigeonVariant(texture));
    }

    public static void init() {
    }
}
