package aqario.fowlplay.client.render.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

import java.util.ArrayList;
import java.util.List;

public class FPDebugRenderers {
    private static final List<LerpedDebugRenderer> RENDERERS = new ArrayList<>();

    public static synchronized void register(LerpedDebugRenderer renderer) {
        RENDERERS.add(renderer);
    }

    public static void render(PoseStack poseStack, MultiBufferSource bufferSource, double camX, double camY, double camZ, double partialTick) {
        RENDERERS.forEach(r -> r.render(poseStack, bufferSource, camX, camY, camZ, partialTick));
    }

    public static void clear() {
        RENDERERS.forEach(LerpedDebugRenderer::clear);
    }

    public interface LerpedDebugRenderer {
        void render(PoseStack poseStack, MultiBufferSource bufferSource, double camX, double camY, double camZ, double partialTick);

        default void clear() {
        }
    }
}
