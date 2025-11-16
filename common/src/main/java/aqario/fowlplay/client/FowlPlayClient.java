package aqario.fowlplay.client;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayEntityType;
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
