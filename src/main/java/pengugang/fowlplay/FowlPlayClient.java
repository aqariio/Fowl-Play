package pengugang.fowlplay;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import pengugang.fowlplay.client.PenguinRenderer;
import pengugang.fowlplay.client.SeagullRenderer;
import pengugang.fowlplay.entity.InitEntity;

public class FowlPlayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(InitEntity.PENGUIN, PenguinRenderer::new);
        EntityRendererRegistry.register(InitEntity.SEAGULL, SeagullRenderer::new);
    }
}
