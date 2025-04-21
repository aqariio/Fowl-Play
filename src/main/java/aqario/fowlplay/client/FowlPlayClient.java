package aqario.fowlplay.client;

import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlayEntityType;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class FowlPlayClient {
    public static boolean DEBUG_BIRD = false;

    public static void init() {
        registerEntityRenderers();

        //? if fabric && 1.21.1 {
        /*if(FowlPlay.isDebugUtilsLoaded()) {
            Identifier debugBirdId = Identifier.of(FowlPlay.ID, "debug/bird");
            RegisterDebugRenderers.registerCustomDebugRenderer(debugBirdId, BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugBirdId);
            RegisterDebugRenderers.registerClientHandler(debugBirdId, b -> FowlPlayClient.DEBUG_BIRD = b);

            NetworkManager.registerS2CPayloadType(DebugBirdCustomPayload.ID, DebugBirdCustomPayload.CODEC);
            NetworkManager.registerReceiver(NetworkManager.Side.S2C, DebugBirdCustomPayload.ID, DebugBirdCustomPayload.CODEC, (payload, context) ->
                DebugBirdCustomPayload.onReceive(payload)
            );
        }
        *///?}
    }

    public static void registerEntityRenderers() {
        EntityModelLayerRegistry.register(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);

        EntityModelLayerRegistry.register(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);

        EntityModelLayerRegistry.register(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);

        EntityModelLayerRegistry.register(CrowEntityModel.MODEL_LAYER, CrowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.CROW, CrowEntityRenderer::new);

        EntityModelLayerRegistry.register(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.DUCK, DuckEntityRenderer::new);

        EntityModelLayerRegistry.register(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.GULL, GullEntityRenderer::new);

        EntityModelLayerRegistry.register(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.HAWK, HawkEntityRenderer::new);

        EntityModelLayerRegistry.register(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(BabyPenguinEntityModel.MODEL_LAYER, BabyPenguinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);

        EntityModelLayerRegistry.register(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);

        EntityModelLayerRegistry.register(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);

        EntityModelLayerRegistry.register(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);

        EntityModelLayerRegistry.register(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(() -> FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            EntityModelLayerRegistry.register(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.register(CustomBabyChickenEntityModel.MODEL_LAYER, CustomBabyChickenEntityModel::getTexturedModelData);
            EntityRendererRegistry.register(() -> EntityType.CHICKEN, CustomChickenEntityRenderer::new);
        }
    }
}
