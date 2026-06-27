package aqario.fowlplay.mixin;

import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundSource.class)
public enum SoundSourceMixin {
    FOWLPLAY_BIRDS("birds");

    @Shadow
    SoundSourceMixin(String name) {
    }
}