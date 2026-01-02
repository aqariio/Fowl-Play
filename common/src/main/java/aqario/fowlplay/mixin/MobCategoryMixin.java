package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.bird.CustomMobCategory;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// credit to hybrid aquatic for the code
@Mixin(MobCategory.class)
public class MobCategoryMixin {
    @SuppressWarnings("unused")
    MobCategoryMixin(String enumname, int ordinal, String name, int spawnCap, boolean peaceful, boolean rare, int immediateDespawnRange) {
        throw new AssertionError();
    }

    // Vanilla Spawn Groups array
    @Shadow
    @Mutable
    @Final
    private static MobCategory[] $VALUES;

    @Unique
    private static MobCategory fowlplay$createMobCategory(String enumname, int ordinal, CustomMobCategory spawnGroup) {
        return ((MobCategory) (Object) new MobCategoryMixin(enumname, ordinal, spawnGroup.name, spawnGroup.spawnCap, spawnGroup.peaceful, spawnGroup.rare, spawnGroup.immediateDespawnRange));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;", shift = At.Shift.AFTER))
    private static void fowlplay$addCustomGroups(CallbackInfo ci) {
        int vanillaMobCategoriesLength = $VALUES.length;
        CustomMobCategory[] categories = CustomMobCategory.values();
        $VALUES = Arrays.copyOf($VALUES, vanillaMobCategoriesLength + categories.length);

        for(int i = 0; i < categories.length; i++) {
            int pos = vanillaMobCategoriesLength + i;
            CustomMobCategory mobCategory = categories[i];
            mobCategory.mobCategory = $VALUES[pos] = fowlplay$createMobCategory(mobCategory.name(), pos, mobCategory);
        }
    }
}