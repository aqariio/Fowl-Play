package aqario.fowlplay.client;

import aqario.fowlplay.client.model.*;
import aqario.fowlplay.client.render.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.FowlPlayEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;

@SuppressWarnings("unused")
public class FowlPlayClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            FowlPlayConfig.loadConfig();
        }

        EntityModelLayerRegistry.registerModelLayer(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.DUCK, DuckEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.GULL, GullEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.HAWK, HawkEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);

        if (FowlPlayConfig.customChickenModel) {
            EntityModelLayerRegistry.registerModelLayer(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            EntityRendererRegistry.register(EntityType.CHICKEN, CustomChickenEntityRenderer::new);
        }
    }
}
