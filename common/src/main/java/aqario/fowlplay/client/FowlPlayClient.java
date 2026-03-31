package aqario.fowlplay.client;

import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.debug.GenericDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.network.clientbound.BirdDebugPayload;
import aqario.fowlplay.common.network.clientbound.GenericDebugPayload;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import dev.architectury.networking.NetworkManager;
import io.github.flemmli97.debugutils.api.RegisterDebugRenderers;
import net.blay09.mods.balm.client.BalmClientRegistrars;
import net.blay09.mods.balm.client.model.geom.BalmModelLayerRegistrar;
import net.blay09.mods.balm.client.particle.BalmParticleProviderRegistrar;
import net.blay09.mods.balm.client.renderer.entity.BalmEntityRendererRegistrar;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;

@SuppressWarnings("unused")
public class FowlPlayClient {
    private static final CubeDeformation ARMOR_DILATION = new CubeDeformation(1.0F);
    private static final CubeDeformation HAT_DILATION = new CubeDeformation(0.5F);
    public static boolean DEBUG_BIRD = false;
    public static boolean DEBUG_GENERIC = false;

    public static void init(BalmClientRegistrars registrars) {
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

        registrars.modelLayers(FowlPlayClient::registerModelLayers);
        registrars.entityRenderers(FowlPlayClient::registerEntityRenderers);
        registrars.particleProviders(FowlPlayClient::registerParticleProviders);
    }

    public static void registerModelLayers(BalmModelLayerRegistrar registrar) {
        registrar.register(BlueJayModel.MODEL_LAYER.getModel(), BlueJayModel::createBodyLayer);

        registrar.register(CardinalModel.MODEL_LAYER.getModel(), CardinalModel::createBodyLayer);

        registrar.register(ChickadeeModel.MODEL_LAYER.getModel(), ChickadeeModel::createBodyLayer);

        registrar.register(CrowModel.MODEL_LAYER.getModel(), CrowModel::createBodyLayer);

        registrar.register(DuckModel.MODEL_LAYER.getModel(), DuckModel::createBodyLayer);

        registrar.register(GooseModel.MODEL_LAYER.getModel(), GooseModel::createBodyLayer);
        registrar.register(DomesticGooseModel.MODEL_LAYER.getModel(), DomesticGooseModel::createBodyLayer);
        registrar.register(BabyGooseModel.MODEL_LAYER.getModel(), BabyGooseModel::createBodyLayer);

        registrar.register(GullModel.MODEL_LAYER.getModel(), GullModel::createBodyLayer);

        registrar.register(HawkModel.MODEL_LAYER.getModel(), HawkModel::createBodyLayer);

        registrar.register(PenguinModel.MODEL_LAYER.getModel(), PenguinModel::createBodyLayer);
        registrar.register(BabyPenguinModel.MODEL_LAYER.getModel(), BabyPenguinModel::createBodyLayer);

        registrar.register(PigeonModel.MODEL_LAYER.getModel(), PigeonModel::createBodyLayer);

        registrar.register(RavenModel.MODEL_LAYER.getModel(), RavenModel::createBodyLayer);

        registrar.register(RobinModel.MODEL_LAYER.getModel(), RobinModel::createBodyLayer);

        registrar.register(SparrowModel.MODEL_LAYER.getModel(), SparrowModel::createBodyLayer);

        registrar.register(ScarecrowModel.MODEL_LAYER.getModel(), ScarecrowModel::createBodyLayer);
        registrar.register(ScarecrowModel.INNER_ARMOR.getModel(), () -> ScarecrowArmorModel.createBodyLayer(HAT_DILATION));
        registrar.register(ScarecrowModel.OUTER_ARMOR.getModel(), () -> ScarecrowArmorModel.createBodyLayer(ARMOR_DILATION));

        if(FowlPlayConfig.getInstance().customChickenModel) {
            registrar.register(CustomChickenModel.MODEL_LAYER.getModel(), CustomChickenModel::createBodyLayer);
            registrar.register(CustomBabyChickenModel.MODEL_LAYER.getModel(), CustomBabyChickenModel::createBodyLayer);
        }
    }

    @SuppressWarnings("unchecked")
    public static void registerEntityRenderers(BalmEntityRendererRegistrar registrar) {
        registrar.register(FowlPlayEntityTypes.BLUE_JAY, BlueJayRenderer::new);
        registrar.register(FowlPlayEntityTypes.CARDINAL, CardinalRenderer::new);
        registrar.register(FowlPlayEntityTypes.CHICKADEE, ChickadeeRenderer::new);
        registrar.register(FowlPlayEntityTypes.CROW, CrowRenderer::new);
        registrar.register(FowlPlayEntityTypes.DUCK, DuckRenderer::new);
        registrar.register(FowlPlayEntityTypes.GOOSE, GooseRenderer::new);
        registrar.register(FowlPlayEntityTypes.GULL, GullRenderer::new);
        registrar.register(FowlPlayEntityTypes.HAWK, HawkRenderer::new);
        registrar.register(FowlPlayEntityTypes.PENGUIN, PenguinRenderer::new);
        registrar.register(FowlPlayEntityTypes.PIGEON, PigeonRenderer::new);
        registrar.register(FowlPlayEntityTypes.RAVEN, RavenRenderer::new);
        registrar.register(FowlPlayEntityTypes.ROBIN, RobinRenderer::new);
        registrar.register(FowlPlayEntityTypes.SPARROW, SparrowRenderer::new);
        registrar.register(FowlPlayEntityTypes.SCARECROW, ScarecrowRenderer::new);

        if(FowlPlayConfig.getInstance().customChickenModel) {
            registrar.register(
                (Holder<? extends EntityType<? extends Chicken>>) BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(EntityType.CHICKEN),
                CustomChickenRenderer::new
            );
        }
    }

    public static void registerParticleProviders(BalmParticleProviderRegistrar registrar) {
        registrar.register(FowlPlayParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Provider::new);
    }
}
