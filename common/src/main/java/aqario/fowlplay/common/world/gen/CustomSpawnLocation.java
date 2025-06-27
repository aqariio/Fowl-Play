package aqario.fowlplay.common.world.gen;

import net.minecraft.entity.SpawnRestriction;

public enum CustomSpawnLocation {
    GROUND,
    SEMIAQUATIC,
    AQUATIC;

    public SpawnRestriction.Location location;
}
