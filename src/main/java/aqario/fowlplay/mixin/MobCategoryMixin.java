package aqario.fowlplay.mixin;

import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobCategory.class)
public enum MobCategoryMixin {
    FOWLPLAY_AMBIENT_BIRDS("ambient_birds", 15, true, false, 96),
    FOWLPLAY_BIRDS("birds", 20, true, false, 96);

    @Shadow
    MobCategoryMixin(String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
    }
}