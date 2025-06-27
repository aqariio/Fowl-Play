package aqario.fowlplay.client;

import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlayEntityType;
import com.google.common.base.Suppliers;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

@SuppressWarnings("unused")
public class FowlPlayClient {
    public static void init() {
    }

    public static void registerModelLayers() {
        EntityModelLayerRegistry.register(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CrowEntityModel.MODEL_LAYER, CrowEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(BabyPenguinEntityModel.MODEL_LAYER, BabyPenguinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.register(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            EntityModelLayerRegistry.register(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            EntityModelLayerRegistry.register(CustomBabyChickenEntityModel.MODEL_LAYER, CustomBabyChickenEntityModel::getTexturedModelData);
        }
    }

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.CROW, CrowEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.DUCK, DuckEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.GULL, GullEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.HAWK, HawkEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);
        EntityRendererRegistry.register(FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            EntityRendererRegistry.register(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenEntityRenderer::new);
        }
    }

    // TODO: Fix cross-platform particle registration
    public static void registerParticleFactories() {
//        ParticleProviderRegistry.register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
//        PlatformHelper.registerParticleFactory(FowlPlayParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Factory::new);
    }
}
