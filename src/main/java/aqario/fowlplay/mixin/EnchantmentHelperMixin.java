package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.Aquatic;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    // unfortunately, water movement speed is not an attribute in 1.20
    @Inject(at = @At(value = "HEAD"), method = "getDepthStrider", cancellable = true)
    private static void getDepthStrider(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (entity instanceof PenguinEntity) {
            cir.setReturnValue(3);
            return;
        }
        if (entity instanceof Aquatic) {
            cir.setReturnValue(1);
        }
    }
}