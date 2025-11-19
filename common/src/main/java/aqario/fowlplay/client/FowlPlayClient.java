package aqario.fowlplay.client;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.platform.PlatformHelper;
import com.google.common.base.Suppliers;
import dev.architectury.networking.NetworkManager;
import io.github.flemmli97.debugutils.api.RegisterDebugRenderers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

@SuppressWarnings("unused")
public class FowlPlayClient {
    private static final CubeDeformation ARMOR_DILATION = new CubeDeformation(1.0F);
    private static final CubeDeformation HAT_DILATION = new CubeDeformation(0.5F);
    public static final ResourceLocation DEBUG_BIRD_ID = FowlPlay.id("debug/bird");
    public static boolean DEBUG_BIRD = false;

    public static void init() {
        if(FowlPlay.isDebugUtilsLoaded()) {
            RegisterDebugRenderers.registerCustomDebugRenderer(DEBUG_BIRD_ID, BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(DEBUG_BIRD_ID);
            RegisterDebugRenderers.registerClientHandler(DEBUG_BIRD_ID, b -> FowlPlayClient.DEBUG_BIRD = b);

            NetworkManager.registerReceiver(
                NetworkManager.s2c(),
                DEBUG_BIRD_ID,
                (payload, context) ->
                    BirdDebugRenderer.INSTANCE.addBird(new BirdDebugRenderer.BirdData(payload))
            );
        }
    }

    public static void registerModelLayers() {
        PlatformHelper.registerModelLayer(BlueJayModel.MODEL_LAYER, BlueJayModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(CardinalModel.MODEL_LAYER, CardinalModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ChickadeeModel.MODEL_LAYER, ChickadeeModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(CrowModel.MODEL_LAYER, CrowModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(DuckModel.MODEL_LAYER, DuckModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(GooseModel.MODEL_LAYER, GooseModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(DomesticGooseModel.MODEL_LAYER, DomesticGooseModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(GullModel.MODEL_LAYER, GullModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(HawkModel.MODEL_LAYER, HawkModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(PenguinModel.MODEL_LAYER, PenguinModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(BabyPenguinModel.MODEL_LAYER, BabyPenguinModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(PigeonModel.MODEL_LAYER, PigeonModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(RavenModel.MODEL_LAYER, RavenModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(RobinModel.MODEL_LAYER, RobinModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(SparrowModel.MODEL_LAYER, SparrowModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ScarecrowModel.MODEL_LAYER, ScarecrowModel::getTexturedModelData);
        PlatformHelper.registerModelLayer(ScarecrowModel.INNER_ARMOR, () -> ScarecrowArmorModel.getTexturedModelData(HAT_DILATION));
        PlatformHelper.registerModelLayer(ScarecrowModel.OUTER_ARMOR, () -> ScarecrowArmorModel.getTexturedModelData(ARMOR_DILATION));

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerModelLayer(CustomChickenModel.MODEL_LAYER, CustomChickenModel::getTexturedModelData);
            PlatformHelper.registerModelLayer(CustomBabyChickenModel.MODEL_LAYER, CustomBabyChickenModel::getTexturedModelData);
        }
    }

    public static void registerEntityRenderers() {
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.BLUE_JAY, BlueJayRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.CARDINAL, CardinalRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.CHICKADEE, ChickadeeRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.CROW, CrowRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.DUCK, DuckRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.GOOSE, GooseRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.GULL, GullRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.HAWK, HawkRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.PENGUIN, PenguinRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.PIGEON, PigeonRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.RAVEN, RavenRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.ROBIN, RobinRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.SPARROW, SparrowRenderer::new);
        PlatformHelper.registerEntityRenderer(FowlPlayEntityTypes.SCARECROW, ScarecrowRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerEntityRenderer(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenRenderer::new);
        }
    }

    // TODO: Fix cross-platform particle registration
    public static void registerParticleFactories() {
//        ParticleProviderRegistry.register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
//        PlatformHelper.registerParticleFactory(FowlPlayParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Factory::new);
    }
}
