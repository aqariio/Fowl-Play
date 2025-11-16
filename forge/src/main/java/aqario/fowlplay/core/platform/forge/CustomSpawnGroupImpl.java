package aqario.fowlplay.core.platform.forge;

import aqario.fowlplay.core.platform.CustomSpawnGroup;
import net.minecraft.world.entity.MobCategory;

public final class CustomSpawnGroupImpl {
    public static MobCategory AMBIENT_BIRDS;
    public static MobCategory BIRDS;

    public static MobCategory ambientBirds() {
        MobCategory spawnGroup = MobCategory.byName(CustomSpawnGroup.AMBIENT_BIRDS_NAME);

        if(spawnGroup == null) {
            spawnGroup = MobCategory.create(
                CustomSpawnGroup.AMBIENT_BIRDS_NAME,
                CustomSpawnGroup.AMBIENT_BIRDS_NAME,
                CustomSpawnGroup.AMBIENT_BIRDS_SPAWN_CAP,
                CustomSpawnGroup.AMBIENT_BIRDS_PEACEFUL,
                CustomSpawnGroup.AMBIENT_BIRDS_RARE,
                CustomSpawnGroup.AMBIENT_BIRDS_IMMEDIATE_DESPAWN_RANGE
            );
        }

        return spawnGroup;
    }

    public static MobCategory birds() {
        MobCategory spawnGroup = MobCategory.byName(CustomSpawnGroup.BIRDS_NAME);

        if(spawnGroup == null) {
            spawnGroup = MobCategory.create(
                CustomSpawnGroup.BIRDS_NAME,
                CustomSpawnGroup.BIRDS_NAME,
                CustomSpawnGroup.BIRDS_SPAWN_CAP,
                CustomSpawnGroup.BIRDS_PEACEFUL,
                CustomSpawnGroup.BIRDS_RARE,
                CustomSpawnGroup.BIRDS_IMMEDIATE_DESPAWN_RANGE
            );
        }

        return spawnGroup;
    }
}
