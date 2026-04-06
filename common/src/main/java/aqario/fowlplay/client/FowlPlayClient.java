package aqario.fowlplay.client;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.debug.GenericDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.network.clientbound.BirdDebugPayload;
import aqario.fowlplay.common.network.clientbound.GenericDebugPayload;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.platform.Register;
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
    public static boolean DEBUG_GENERIC = false;

    public static void init() {
        if(FowlPlay.isDebugUtilsLoaded()) {
            ResourceLocation debugBirdId = BirdDebugPayload.TYPE.id();
            RegisterDebugRenderers.registerCustomDebugRenderer(debugBirdId, BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugBirdId);
            RegisterDebugRenderers.registerClientHandler(debugBirdId, b -> FowlPlayClient.DEBUG_BIRD = b);
            ResourceLocation debugGenericId = GenericDebugPayload.TYPE.id();
            RegisterDebugRenderers.registerCustomDebugRenderer(debugGenericId, GenericDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugGenericId);
            RegisterDebugRenderers.registerClientHandler(debugGenericId, b -> FowlPlayClient.DEBUG_GENERIC = b);

            NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                BirdDebugPayload.TYPE,
                BirdDebugPayload.STREAM_CODEC,
                (payload, context) ->
                    BirdDebugPayload.onReceive(payload)
            );
            NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                GenericDebugPayload.TYPE,
                GenericDebugPayload.STREAM_CODEC,
                (payload, context) ->
                    GenericDebugPayload.onReceive(payload)
            );
        }
    }

    public static void registerModelLayers() {
        Register.modelLayer(BlueJayModel.MODEL_LAYER, BlueJayModel::createBodyLayer);

        Register.modelLayer(CardinalModel.MODEL_LAYER, CardinalModel::createBodyLayer);

        Register.modelLayer(ChickadeeModel.MODEL_LAYER, ChickadeeModel::createBodyLayer);

        Register.modelLayer(CrowModel.MODEL_LAYER, CrowModel::createBodyLayer);

        Register.modelLayer(DuckModel.MODEL_LAYER, DuckModel::createBodyLayer);

        Register.modelLayer(GooseModel.MODEL_LAYER, GooseModel::createBodyLayer);
        Register.modelLayer(DomesticGooseModel.MODEL_LAYER, DomesticGooseModel::createBodyLayer);
        Register.modelLayer(BabyGooseModel.MODEL_LAYER, BabyGooseModel::createBodyLayer);

        Register.modelLayer(GullModel.MODEL_LAYER, GullModel::createBodyLayer);

        Register.modelLayer(HawkModel.MODEL_LAYER, HawkModel::createBodyLayer);

        Register.modelLayer(PenguinModel.MODEL_LAYER, PenguinModel::createBodyLayer);
        Register.modelLayer(BabyPenguinModel.MODEL_LAYER, BabyPenguinModel::createBodyLayer);

        Register.modelLayer(PigeonModel.MODEL_LAYER, PigeonModel::createBodyLayer);

        Register.modelLayer(RavenModel.MODEL_LAYER, RavenModel::createBodyLayer);

        Register.modelLayer(RobinModel.MODEL_LAYER, RobinModel::createBodyLayer);

        Register.modelLayer(SparrowModel.MODEL_LAYER, SparrowModel::createBodyLayer);

        Register.modelLayer(ScarecrowModel.MODEL_LAYER, ScarecrowModel::createBodyLayer);
        Register.modelLayer(ScarecrowModel.INNER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(HAT_DILATION));
        Register.modelLayer(ScarecrowModel.OUTER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(ARMOR_DILATION));

        if(FowlPlayConfig.getInstance().customChickenModel) {
            Register.modelLayer(CustomChickenModel.MODEL_LAYER, CustomChickenModel::createBodyLayer);
            Register.modelLayer(CustomBabyChickenModel.MODEL_LAYER, CustomBabyChickenModel::createBodyLayer);
        }
    }

    public static void registerEntityRenderers() {
        Register.entityRenderer(FowlPlayEntityTypes.BLUE_JAY, BlueJayRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.CARDINAL, CardinalRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.CHICKADEE, ChickadeeRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.CROW, CrowRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.DUCK, DuckRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.GOOSE, GooseRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.GULL, GullRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.HAWK, HawkRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.PENGUIN, PenguinRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.PIGEON, PigeonRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.RAVEN, RavenRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.ROBIN, RobinRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.SPARROW, SparrowRenderer::new);
        Register.entityRenderer(FowlPlayEntityTypes.SCARECROW, ScarecrowRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            Register.entityRenderer(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenRenderer::new);
        }
    }

    // TODO: Fix cross-platform particle registration
    public static void registerParticleFactories() {
//        ParticleProviderRegistry.register(FowlPlayParticleTypes.SMALL_BUBBLE.get(), SmallBubbleParticle.Factory::new);
//        PlatformHelper.registerParticleFactory(FowlPlayParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Factory::new);
    }
}
