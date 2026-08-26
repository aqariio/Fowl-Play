package aqario.fowlplay.mixin;

import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.FPMobCategory;
import net.minecraft.world.entity.MobCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    private static MobCategory fowlplay$createMobCategory(String enumname, int ordinal, FPMobCategory spawnGroup) {
        return ((MobCategory) (Object) new MobCategoryMixin(enumname, ordinal, spawnGroup.name, spawnGroup.spawnCap, spawnGroup.peaceful, spawnGroup.rare, spawnGroup.immediateDespawnRange));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/MobCategory;$VALUES:[Lnet/minecraft/world/entity/MobCategory;", shift = At.Shift.AFTER))
    private static void fowlplay$addCustomGroups(CallbackInfo ci) {
        int vanillaMobCategoriesLength = $VALUES.length;
        FPMobCategory[] categories = FPMobCategory.values();
        $VALUES = Arrays.copyOf($VALUES, vanillaMobCategoriesLength + categories.length);

        for(int i = 0; i < categories.length; i++) {
            int pos = vanillaMobCategoriesLength + i;
            FPMobCategory mobCategory = categories[i];
            mobCategory.mobCategory = $VALUES[pos] = fowlplay$createMobCategory(mobCategory.name(), pos, mobCategory);
        }
    }

    @Inject(
        method = "getMaxInstancesPerChunk",
        at = @At("HEAD"),
        cancellable = true
    )
    private void fowlplay$getConfiguredSpawnCap(CallbackInfoReturnable<Integer> cir) {
        MobCategory category = (MobCategory) (Object) this;

        if(category == FPMobCategory.AMBIENT_BIRDS.mobCategory) {
            cir.setReturnValue(FPConfig.get().ambientBirdsSpawnCap);
        }
        else if(category == FPMobCategory.BIRDS.mobCategory) {
            cir.setReturnValue(FPConfig.get().birdsSpawnCap);
        }
    }
}