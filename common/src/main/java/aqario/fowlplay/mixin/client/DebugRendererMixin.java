package aqario.fowlplay.mixin.client;

import aqario.fowlplay.client.render.debug.FPDebugRenderers;
import net.minecraft.client.renderer.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {
    @Inject(method = "clear", at = @At("HEAD"))
    private void fowlplay$clearDebugRenderers(CallbackInfo info) {
        FPDebugRenderers.clear();
    }
}
