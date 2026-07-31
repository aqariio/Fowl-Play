//~ expect_platform
package aqario.fowlplay.common.worldgen;

import net.minecraft.world.entity.SpawnPlacements;

// credit to friendsandfoes for the platform agnostic enum extension implementation
public final class FPSpawnPlacementType {
    public static final String GROUND_INTERNAL_NAME = "GROUND";
    public static final String SEMIAQUATIC_INTERNAL_NAME = "SEMIAQUATIC";
    public static final String AQUATIC_INTERNAL_NAME = "AQUATIC";

    public static SpawnPlacements.Type ground() {
        throw new AssertionError();
    }

    public static SpawnPlacements.Type semiaquatic() {
        throw new AssertionError();
    }

    public static SpawnPlacements.Type aquatic() {
        throw new AssertionError();
    }

    private FPSpawnPlacementType() {
    }
}
