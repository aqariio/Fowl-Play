package aqario.fowlplay.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.SpawnPlacements;

// credit to friendsandfoes for the platform agnostic enum extension implementation
public final class CustomSpawnPlacementType {
    public static final String GROUND_INTERNAL_NAME = "GROUND";
    public static final String SEMIAQUATIC_INTERNAL_NAME = "SEMIAQUATIC";
    public static final String AQUATIC_INTERNAL_NAME = "AQUATIC";

    @ExpectPlatform
    public static SpawnPlacements.Type ground() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SpawnPlacements.Type semiaquatic() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static SpawnPlacements.Type aquatic() {
        throw new AssertionError();
    }

    private CustomSpawnPlacementType() {
    }
}
