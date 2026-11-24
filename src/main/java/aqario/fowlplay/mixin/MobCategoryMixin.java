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

@Mixin(MobCategory.class)
public class MobCategoryMixin {
    @Inject(
        method = "getMaxInstancesPerChunk",
        at = @At("HEAD"),
        cancellable = true
    )
    private void fowlplay$getConfiguredSpawnCap(CallbackInfoReturnable<Integer> cir) {
        MobCategory category = (MobCategory) (Object) this;

        if(category == FPMobCategory.ambientBirds()) {
            cir.setReturnValue(FPConfig.getInstance().ambientBirdsSpawnCap);
        }
        else if(category == FPMobCategory.birds()) {
            cir.setReturnValue(FPConfig.getInstance().birdsSpawnCap);
        }
    }
}