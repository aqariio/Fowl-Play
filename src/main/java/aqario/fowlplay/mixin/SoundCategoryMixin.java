package aqario.fowlplay.mixin;

import aqario.fowlplay.common.sound.FowlPlaySoundCategory;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

// I have no idea how this works, props to Hybrid Aquatic for the code
@Mixin(SoundCategory.class)
public class SoundCategoryMixin {
    @SuppressWarnings("unused")
    SoundCategoryMixin(String enumname, int ordinal, String name) {
        throw new AssertionError();
    }

    // Vanilla Spawn Category array
    @Shadow
    @Mutable
    @Final
    private static SoundCategory[] field_15255;

    @Unique
    private static SoundCategory createSoundCategory(String enumname, int ordinal, FowlPlaySoundCategory soundCategory) {
        return ((SoundCategory) (Object) new SoundCategoryMixin(enumname, ordinal, soundCategory.name));
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/sound/SoundCategory;field_15255:[Lnet/minecraft/sound/SoundCategory;", shift = At.Shift.AFTER))
    private static void addCategories(CallbackInfo ci) {
        int vanillaSoundCategorysLength = field_15255.length;
        FowlPlaySoundCategory[] groups = FowlPlaySoundCategory.values();
        field_15255 = Arrays.copyOf(field_15255, vanillaSoundCategorysLength + groups.length);

        for (int i = 0; i < groups.length; i++) {
            int pos = vanillaSoundCategorysLength + i;
            FowlPlaySoundCategory soundCategory = groups[i];
            soundCategory.soundCategory = field_15255[pos] = createSoundCategory(soundCategory.name(), pos, soundCategory);
        }
    }
}