package aqario.fowlplay.client;

import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.platform.PlatformHelper;
import com.google.common.base.Suppliers;
import net.minecraft.client.model.Dilation;
import net.minecraft.entity.EntityType;

@SuppressWarnings("unused")
public class FowlPlayClient {
    private static final Dilation ARMOR_DILATION = new Dilation(1.0F);
    private static final Dilation HAT_DILATION = new Dilation(0.5F);

    public static void init() {
    }

    public static void registerModelLayers() {
        PlatformHelper.registerModelLayer(BlueJayEntityModel.MODEL_LAYER, BlueJayEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(CardinalEntityModel.MODEL_LAYER, CardinalEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ChickadeeEntityModel.MODEL_LAYER, ChickadeeEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(CrowEntityModel.MODEL_LAYER, CrowEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(DuckEntityModel.MODEL_LAYER, DuckEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(GooseEntityModel.MODEL_LAYER, GooseEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(DomesticGooseEntityModel.MODEL_LAYER, DomesticGooseEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(GullEntityModel.MODEL_LAYER, GullEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(HawkEntityModel.MODEL_LAYER, HawkEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(PenguinEntityModel.MODEL_LAYER, PenguinEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(BabyPenguinEntityModel.MODEL_LAYER, BabyPenguinEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(PigeonEntityModel.MODEL_LAYER, PigeonEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(RavenEntityModel.MODEL_LAYER, RavenEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(RobinEntityModel.MODEL_LAYER, RobinEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(SparrowEntityModel.MODEL_LAYER, SparrowEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ScarecrowEntityModel.MODEL_LAYER, ScarecrowEntityModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ScarecrowEntityModel.INNER_ARMOR, () -> ScarecrowArmorEntityModel.getTexturedModelData(HAT_DILATION));
        PlatformHelper.registerModelLayer(ScarecrowEntityModel.OUTER_ARMOR, () -> ScarecrowArmorEntityModel.getTexturedModelData(ARMOR_DILATION));

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerModelLayer(CustomChickenEntityModel.MODEL_LAYER, CustomChickenEntityModel::getTexturedModelData);
            PlatformHelper.registerModelLayer(CustomBabyChickenEntityModel.MODEL_LAYER, CustomBabyChickenEntityModel::getTexturedModelData);
        }
    }

    public static void registerEntityRenderers() {
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.BLUE_JAY, BlueJayEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.CARDINAL, CardinalEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.CHICKADEE, ChickadeeEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.CROW, CrowEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.DUCK, DuckEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.GOOSE, GooseEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.GULL, GullEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.HAWK, HawkEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.PENGUIN, PenguinEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.PIGEON, PigeonEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.RAVEN, RavenEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.ROBIN, RobinEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.SPARROW, SparrowEntityRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityType.SCARECROW, ScarecrowEntityRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerEntityRenderer(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenEntityRenderer::new);
        }
    }

    // TODO: Fix cross-platform particle registration
    public static void registerParticleFactories() {
//        ParticleProviderRegistry.register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
//        PlatformHelper.registerParticleFactory(FowlPlayParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Factory::new);
    }
}
