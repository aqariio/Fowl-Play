//? if forge {
/*package aqario.fowlplay.forge.common.entity;

import aqario.fowlplay.common.entity.FPMobCategory;
import net.minecraft.world.entity.MobCategory;

public final class FPMobCategoryImpl {
    public static MobCategory AMBIENT_BIRDS;
    public static MobCategory BIRDS;

    public static MobCategory ambientBirds() {
        MobCategory category = MobCategory.byName(FPMobCategory.AMBIENT_BIRDS_NAME);

        if(category == null) {
            category = MobCategory.create(
                FPMobCategory.AMBIENT_BIRDS_NAME,
                FPMobCategory.AMBIENT_BIRDS_NAME,
                FPMobCategory.AMBIENT_BIRDS_MAX,
                FPMobCategory.AMBIENT_BIRDS_IS_FRIENDLY,
                FPMobCategory.AMBIENT_BIRDS_IS_PERSISTENT,
                FPMobCategory.AMBIENT_BIRDS_DESPAWN_DISTANCE
            );
        }

        return category;
    }

    public static MobCategory birds() {
        MobCategory category = MobCategory.byName(FPMobCategory.BIRDS_NAME);

        if(category == null) {
            category = MobCategory.create(
                FPMobCategory.BIRDS_NAME,
                FPMobCategory.BIRDS_NAME,
                FPMobCategory.BIRDS_MAX,
                FPMobCategory.BIRDS_IS_FRIENDLY,
                FPMobCategory.BIRDS_IS_PERSISTENT,
                FPMobCategory.BIRDS_DESPAWN_DISTANCE
            );
        }

        return category;
    }
}
*///?}