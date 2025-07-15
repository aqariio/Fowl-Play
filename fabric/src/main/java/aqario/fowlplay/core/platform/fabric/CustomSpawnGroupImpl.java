package aqario.fowlplay.core.platform.fabric;

import net.minecraft.entity.SpawnGroup;

public final class CustomSpawnGroupImpl {
    static {
        SpawnGroup.values();
    }

    public static SpawnGroup AMBIENT_BIRDS;
    public static SpawnGroup BIRDS;

    public static SpawnGroup ambientBirds() {
        return AMBIENT_BIRDS;
    }

    public static SpawnGroup birds() {
        return BIRDS;
    }
}
