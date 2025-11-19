package aqario.fowlplay.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.MobCategory;

// credit to friendsandfoes for the platform agnostic enum extension implementation
public final class CustomMobCategory {
    public static final String AMBIENT_BIRDS_INTERNAL_NAME = "AMBIENT_BIRDS";
    public static final String AMBIENT_BIRDS_NAME = "ambient_birds";
    public static final int AMBIENT_BIRDS_SPAWN_CAP = 15;
    public static final boolean AMBIENT_BIRDS_PEACEFUL = true;
    public static final boolean AMBIENT_BIRDS_RARE = false;
    public static final int AMBIENT_BIRDS_IMMEDIATE_DESPAWN_RANGE = 96;

    public static final String BIRDS_INTERNAL_NAME = "BIRDS";
    public static final String BIRDS_NAME = "birds";
    public static final int BIRDS_SPAWN_CAP = 20;
    public static final boolean BIRDS_PEACEFUL = true;
    public static final boolean BIRDS_RARE = false;
    public static final int BIRDS_IMMEDIATE_DESPAWN_RANGE = 96;

    @ExpectPlatform
    public static MobCategory ambientBirds() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MobCategory birds() {
        throw new AssertionError();
    }

    private CustomMobCategory() {
    }
}
