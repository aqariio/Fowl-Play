package aqario.fowlplay.core.platform.fabric;

import net.minecraft.world.entity.MobCategory;

public final class CustomMobCategoryImpl {
    static {
        MobCategory.values();
    }

    public static MobCategory AMBIENT_BIRDS;
    public static MobCategory BIRDS;

    public static MobCategory ambientBirds() {
        return AMBIENT_BIRDS;
    }

    public static MobCategory birds() {
        return BIRDS;
    }
}
