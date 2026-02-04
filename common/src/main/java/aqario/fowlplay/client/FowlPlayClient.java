package aqario.fowlplay.client;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.network.clientbound.BirdDebugPayload;
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
    public static boolean DEBUG_BIRD = false;

    public static void init() {
        if(FowlPlay.isDebugUtilsLoaded()) {
            ResourceLocation debugBirdId = FowlPlay.id("debug/bird");
            RegisterDebugRenderers.registerCustomDebugRenderer(debugBirdId, BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugBirdId);
            RegisterDebugRenderers.registerClientHandler(debugBirdId, b -> FowlPlayClient.DEBUG_BIRD = b);

            NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                BirdDebugPayload.TYPE,
                BirdDebugPayload.STREAM_CODEC,
                (payload, context) ->
                    BirdDebugPayload.onReceive(payload)
            );
        }
    }

    public static void registerModelLayers() {
        PlatformHelper.registerModelLayer(BlueJayModel.MODEL_LAYER, BlueJayModel::createBodyLayer);

        PlatformHelper.registerModelLayer(CardinalModel.MODEL_LAYER, CardinalModel::createBodyLayer);

        PlatformHelper.registerModelLayer(ChickadeeModel.MODEL_LAYER, ChickadeeModel::createBodyLayer);

        PlatformHelper.registerModelLayer(CrowModel.MODEL_LAYER, CrowModel::createBodyLayer);

        PlatformHelper.registerModelLayer(DuckModel.MODEL_LAYER, DuckModel::createBodyLayer);

        PlatformHelper.registerModelLayer(GooseModel.MODEL_LAYER, GooseModel::createBodyLayer);
        PlatformHelper.registerModelLayer(DomesticGooseModel.MODEL_LAYER, DomesticGooseModel::createBodyLayer);
        PlatformHelper.registerModelLayer(BabyGooseModel.MODEL_LAYER, BabyGooseModel::createBodyLayer);

        PlatformHelper.registerModelLayer(GullModel.MODEL_LAYER, GullModel::createBodyLayer);

        PlatformHelper.registerModelLayer(HawkModel.MODEL_LAYER, HawkModel::createBodyLayer);

        PlatformHelper.registerModelLayer(PenguinModel.MODEL_LAYER, PenguinModel::createBodyLayer);
        PlatformHelper.registerModelLayer(BabyPenguinModel.MODEL_LAYER, BabyPenguinModel::createBodyLayer);

        PlatformHelper.registerModelLayer(PigeonModel.MODEL_LAYER, PigeonModel::createBodyLayer);

        PlatformHelper.registerModelLayer(RavenModel.MODEL_LAYER, RavenModel::createBodyLayer);

        PlatformHelper.registerModelLayer(RobinModel.MODEL_LAYER, RobinModel::createBodyLayer);

        PlatformHelper.registerModelLayer(SparrowModel.MODEL_LAYER, SparrowModel::createBodyLayer);

        PlatformHelper.registerModelLayer(ScarecrowModel.MODEL_LAYER, ScarecrowModel::createBodyLayer);
        PlatformHelper.registerModelLayer(ScarecrowModel.INNER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(HAT_DILATION));
        PlatformHelper.registerModelLayer(ScarecrowModel.OUTER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(ARMOR_DILATION));

        if(FowlPlayConfig.getInstance().customChickenModel) {
            PlatformHelper.registerModelLayer(CustomChickenModel.MODEL_LAYER, CustomChickenModel::createBodyLayer);
            PlatformHelper.registerModelLayer(CustomBabyChickenModel.MODEL_LAYER, CustomBabyChickenModel::createBodyLayer);
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
