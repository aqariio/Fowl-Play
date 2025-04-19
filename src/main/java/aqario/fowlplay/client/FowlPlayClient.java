package aqario.fowlplay.client;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class FowlPlayClient {
    public static boolean DEBUG_BIRD = false;

    public static void init() {
        registerEntityRenderers();

        if(FowlPlay.isDebugUtilsLoaded()) {
            Identifier debugBirdId = Identifier.of(FowlPlay.ID, "debug/bird");
            RegisterDebugRenderers.registerCustomDebugRenderer(debugBirdId, BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugBirdId);
            RegisterDebugRenderers.registerClientHandler(debugBirdId, b -> FowlPlayClient.DEBUG_BIRD = b);

            PayloadTypeRegistry.playS2C().register(DebugBirdCustomPayload.ID, DebugBirdCustomPayload.CODEC);
            ClientPlayNetworking.registerGlobalReceiver(DebugBirdCustomPayload.ID, (payload, context) ->
                DebugBirdCustomPayload.onReceive(payload)
            );
        }
    }

    public static void registerEntityRenderers() {
        EntityModelLayerRegistry.registerModelLayer(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(CrowEntityModel.MODEL_LAYER, CrowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.CROW, CrowEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.DUCK, DuckEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.GULL, GullEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.HAWK, HawkEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BabyPenguinEntityModel.MODEL_LAYER, BabyPenguinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            EntityModelLayerRegistry.registerModelLayer(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.registerModelLayer(CustomBabyChickenEntityModel.MODEL_LAYER, CustomBabyChickenEntityModel::getTexturedModelData);
            EntityRendererRegistry.register(EntityType.CHICKEN, CustomChickenEntityRenderer::new);
        }
    }
}
