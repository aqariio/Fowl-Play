package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.FPSoundSource;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// credit to hybrid aquatic for the code
@Mixin(SoundSource.class)
public class SoundSourceMixin {
    @SuppressWarnings("unused")
    SoundSourceMixin(String enumname, int ordinal, String name) {
        throw new AssertionError();
    }

    // Vanilla Spawn Groups array
    @Shadow
    @Mutable
    @Final
    private static SoundSource[] $VALUES;

    @Unique
    private static SoundSource fowlplay$createSoundSource(String enumname, int ordinal, FPSoundSource soundSource) {
        return ((SoundSource) (Object) new SoundSourceMixin(enumname, ordinal, soundSource.getName()));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/sounds/SoundSource;$VALUES:[Lnet/minecraft/sounds/SoundSource;", shift = At.Shift.AFTER))
    private static void fowlplay$addCustomGroups(CallbackInfo ci) {
        int vanillaMobCategoriesLength = $VALUES.length;
        FPSoundSource[] categories = FPSoundSource.values();
        $VALUES = Arrays.copyOf($VALUES, vanillaMobCategoriesLength + categories.length);

        for(int i = 0; i < categories.length; i++) {
            int pos = vanillaMobCategoriesLength + i;
            FPSoundSource mobCategory = categories[i];
            mobCategory.soundSource = $VALUES[pos] = fowlplay$createSoundSource(mobCategory.name(), pos, mobCategory);
        }
    }
}