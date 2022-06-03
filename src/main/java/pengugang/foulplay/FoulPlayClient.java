package pengugang.foulplay;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import pengugang.foulplay.client.PenguinRenderer;
import pengugang.foulplay.entity.InitEntity;

public class FoulPlayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(InitEntity.PENGUIN, PenguinRenderer::new);
    }
}
