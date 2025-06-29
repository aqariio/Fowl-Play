package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.util.Identifier;

public record GullVariant(Identifier texture) {
    public static final GullVariant HERRING = register("herring");
    public static final GullVariant RING_BILLED = register("ring_billed");
    public static final GullVariant BLACK_BACKED = register("black_backed");

    private static GullVariant register(String id) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/gull/" + id + "_gull.png");
        return PlatformHelper.registerVariant(id, () -> new GullVariant(texture));
    }

    public static void init() {
    }
}
