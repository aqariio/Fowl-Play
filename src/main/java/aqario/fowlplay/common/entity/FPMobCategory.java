//~ expect_platform
package aqario.fowlplay.common.entity;

import aqario.fowlplay.fabric.common.entity.FPMobCategoryImpl;
import net.minecraft.world.entity.MobCategory;

// credit to friendsandfoes for the platform agnostic enum extension implementation
public final class FPMobCategory {
    public static final String AMBIENT_BIRDS_INTERNAL_NAME = "FOWLPLAY_AMBIENT_BIRDS";
    public static final String AMBIENT_BIRDS_NAME = "fowlplay_ambient_birds";
    public static final int AMBIENT_BIRDS_MAX = 15;
    public static final boolean AMBIENT_BIRDS_IS_FRIENDLY = true;
    public static final boolean AMBIENT_BIRDS_IS_PERSISTENT = false;
    public static final int AMBIENT_BIRDS_DESPAWN_DISTANCE = 96;

    public static final String BIRDS_INTERNAL_NAME = "FOWLPLAY_BIRDS";
    public static final String BIRDS_NAME = "fowlplay_birds";
    public static final int BIRDS_MAX = 20;
    public static final boolean BIRDS_IS_FRIENDLY = true;
    public static final boolean BIRDS_IS_PERSISTENT = false;
    public static final int BIRDS_DESPAWN_DISTANCE = 96;

    public static MobCategory ambientBirds() {
        return FPMobCategoryImpl.ambientBirds();
    }

    public static MobCategory birds() {
        return FPMobCategoryImpl.birds();
    }

    private FPMobCategory() {
    }
}
