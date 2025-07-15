package aqario.fowlplay.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.SpawnRestriction;

// credit to friendsandfoes for the platform agnostic enum extension implementation
public final class CustomSpawnLocation {
    public static final String GROUND_INTERNAL_NAME = "GROUND";
    public static final String SEMIAQUATIC_INTERNAL_NAME = "SEMIAQUATIC";
    public static final String AQUATIC_INTERNAL_NAME = "AQUATIC";

    @ExpectPlatform
    public static SpawnRestriction.Location ground() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SpawnRestriction.Location semiaquatic() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SpawnRestriction.Location aquatic() {
        throw new AssertionError();
    }

    private CustomSpawnLocation() {
    }
}
