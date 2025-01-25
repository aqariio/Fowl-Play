package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.registry.FowlPlayRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public record GullVariant(Identifier texture) {
    public static final GullVariant HERRING = register("herring");
    public static final GullVariant RING_BILLED = register("ring_billed");

    private static GullVariant register(String id) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/gull/" + id + "_gull.png");
        return Registry.register(FowlPlayRegistries.GULL_VARIANT, id, new GullVariant(texture));
    }

    public static void init() {
    }
}
