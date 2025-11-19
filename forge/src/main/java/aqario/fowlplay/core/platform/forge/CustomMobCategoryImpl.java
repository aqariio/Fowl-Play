package aqario.fowlplay.core.platform.forge;

import aqario.fowlplay.core.platform.CustomMobCategory;
import net.minecraft.world.entity.MobCategory;

public final class CustomMobCategoryImpl {
    public static MobCategory AMBIENT_BIRDS;
    public static MobCategory BIRDS;

    public static MobCategory ambientBirds() {
        MobCategory category = MobCategory.byName(CustomMobCategory.AMBIENT_BIRDS_NAME);

        if(category == null) {
            category = MobCategory.create(
                CustomMobCategory.AMBIENT_BIRDS_NAME,
                CustomMobCategory.AMBIENT_BIRDS_NAME,
                CustomMobCategory.AMBIENT_BIRDS_SPAWN_CAP,
                CustomMobCategory.AMBIENT_BIRDS_PEACEFUL,
                CustomMobCategory.AMBIENT_BIRDS_RARE,
                CustomMobCategory.AMBIENT_BIRDS_IMMEDIATE_DESPAWN_RANGE
            );
        }

        return category;
    }

    public static MobCategory birds() {
        MobCategory category = MobCategory.byName(CustomMobCategory.BIRDS_NAME);

        if(category == null) {
            category = MobCategory.create(
                CustomMobCategory.BIRDS_NAME,
                CustomMobCategory.BIRDS_NAME,
                CustomMobCategory.BIRDS_SPAWN_CAP,
                CustomMobCategory.BIRDS_PEACEFUL,
                CustomMobCategory.BIRDS_RARE,
                CustomMobCategory.BIRDS_IMMEDIATE_DESPAWN_RANGE
            );
        }

        return category;
    }
}
