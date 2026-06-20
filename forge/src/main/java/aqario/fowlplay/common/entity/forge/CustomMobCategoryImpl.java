package aqario.fowlplay.common.entity.forge;

import aqario.fowlplay.common.entity.CustomMobCategory;
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
                CustomMobCategory.AMBIENT_BIRDS_MAX,
                CustomMobCategory.AMBIENT_BIRDS_IS_FRIENDLY,
                CustomMobCategory.AMBIENT_BIRDS_IS_PERSISTENT,
                CustomMobCategory.AMBIENT_BIRDS_DESPAWN_DISTANCE
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
                CustomMobCategory.BIRDS_MAX,
                CustomMobCategory.BIRDS_IS_FRIENDLY,
                CustomMobCategory.BIRDS_IS_PERSISTENT,
                CustomMobCategory.BIRDS_DESPAWN_DISTANCE
            );
        }

        return category;
    }
}
