package aqario.fowlplay.client;

import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.debug.FPDebugRenderers;
import aqario.fowlplay.client.render.debug.GenericDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.network.clientbound.BirdDebugPayload;
import aqario.fowlplay.common.network.clientbound.GenericDebugPayload;
import aqario.fowlplay.core.FPEntityTypes;
import aqario.fowlplay.core.FPParticleTypes;
import aqario.fowlplay.core.FowlPlay;
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
            FPDebugRenderers.register(BirdDebugRenderer.INSTANCE);
            RegisterDebugRenderers.registerServerToggle(debugBirdId);
            RegisterDebugRenderers.registerClientHandler(debugBirdId, b -> FowlPlayClient.DEBUG_BIRD = b);
            ResourceLocation debugGenericId = GenericDebugPayload.TYPE.id();
            FPDebugRenderers.register(GenericDebugRenderer.INSTANCE);
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

        registerModelLayers();
        registerEntityRenderers();
        registerParticleFactories();
    }

    public static void registerModelLayers() {
        RenderRegistry.modelLayer(BlueJayModel.MODEL_LAYER, BlueJayModel::createBodyLayer);

        RenderRegistry.modelLayer(CardinalModel.MODEL_LAYER, CardinalModel::createBodyLayer);

        RenderRegistry.modelLayer(ChickadeeModel.MODEL_LAYER, ChickadeeModel::createBodyLayer);

        RenderRegistry.modelLayer(CrowModel.MODEL_LAYER, CrowModel::createBodyLayer);

        RenderRegistry.modelLayer(DuckModel.MODEL_LAYER, DuckModel::createBodyLayer);

        RenderRegistry.modelLayer(GooseModel.MODEL_LAYER, GooseModel::createBodyLayer);
        RenderRegistry.modelLayer(DomesticGooseModel.MODEL_LAYER, DomesticGooseModel::createBodyLayer);
        RenderRegistry.modelLayer(BabyGooseModel.MODEL_LAYER, BabyGooseModel::createBodyLayer);

        RenderRegistry.modelLayer(GullModel.MODEL_LAYER, GullModel::createBodyLayer);

        RenderRegistry.modelLayer(HawkModel.MODEL_LAYER, HawkModel::createBodyLayer);

        RenderRegistry.modelLayer(PenguinModel.MODEL_LAYER, PenguinModel::createBodyLayer);
        RenderRegistry.modelLayer(BabyPenguinModel.MODEL_LAYER, BabyPenguinModel::createBodyLayer);

        RenderRegistry.modelLayer(PigeonModel.MODEL_LAYER, PigeonModel::createBodyLayer);

        RenderRegistry.modelLayer(RavenModel.MODEL_LAYER, RavenModel::createBodyLayer);

        RenderRegistry.modelLayer(RobinModel.MODEL_LAYER, RobinModel::createBodyLayer);

        RenderRegistry.modelLayer(SparrowModel.MODEL_LAYER, SparrowModel::createBodyLayer);

        RenderRegistry.modelLayer(ScarecrowModel.MODEL_LAYER, ScarecrowModel::createBodyLayer);
        RenderRegistry.modelLayer(ScarecrowModel.INNER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(HAT_DILATION));
        RenderRegistry.modelLayer(ScarecrowModel.OUTER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(ARMOR_DILATION));

        if(FPConfig.getInstance().customChickenModel) {
            RenderRegistry.modelLayer(CustomChickenModel.MODEL_LAYER, CustomChickenModel::createBodyLayer);
            RenderRegistry.modelLayer(CustomBabyChickenModel.MODEL_LAYER, CustomBabyChickenModel::createBodyLayer);
        }
    }

    public static void registerEntityRenderers() {
        RenderRegistry.entityRenderer(FPEntityTypes.BLUE_JAY, BlueJayRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.CARDINAL, CardinalRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.CHICKADEE, ChickadeeRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.CROW, CrowRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.DUCK, DuckRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.GOOSE, GooseRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.GULL, GullRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.HAWK, HawkRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.PENGUIN, PenguinRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.PIGEON, PigeonRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.RAVEN, RavenRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.ROBIN, RobinRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.SPARROW, SparrowRenderer::new);
        RenderRegistry.entityRenderer(FPEntityTypes.SCARECROW, ScarecrowRenderer::new);

        if(FPConfig.getInstance().customChickenModel) {
            RenderRegistry.entityRenderer(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenRenderer::new);
        }
    }

    public static void registerParticleFactories() {
        RenderRegistry.particleFactory(FPParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Provider::new);
    }
}
