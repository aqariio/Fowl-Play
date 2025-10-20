package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public record GooseVariant(Identifier texture, boolean domestic) {
    public static final Supplier<GooseVariant> GREYLAG = register("greylag", false);
    public static final Supplier<GooseVariant> CANADA = register("canada", false);
    public static final Supplier<GooseVariant> SWAN = register("swan", false);
    public static final Supplier<GooseVariant> EMDEN = register("emden", true);
    public static final Supplier<GooseVariant> CHINESE = register("chinese", true);

    private static Supplier<GooseVariant> register(String id, boolean domestic) {
        Identifier texture = Identifier.of(FowlPlay.ID, "textures/entity/goose/" + id + "_goose.png");
        return PlatformHelper.registerVariant(id, () -> new GooseVariant(texture, domestic));
    }

    public static void init() {
    }
}
