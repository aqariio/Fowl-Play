package aqario.fowlplay.client;

import aqario.fowlplay.client.particle.SmallBubbleParticle;
import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.debug.FPDebugRenderers;
import aqario.fowlplay.client.render.debug.GenericDebugRenderer;
import aqario.fowlplay.client.render.entity.*;
import aqario.fowlplay.client.render.entity.model.*;
import aqario.fowlplay.common.config.FPConfig;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.network.NetworkManager;
import aqario.fowlplay.common.network.clientbound.BirdDebugPayload;
import aqario.fowlplay.common.network.clientbound.GenericDebugPayload;
import aqario.fowlplay.core.FPEntityTypes;
import aqario.fowlplay.core.FPParticleTypes;
import aqario.fowlplay.core.FowlPlay;
import com.google.common.base.Suppliers;
import io.github.flemmli97.debugutils.api.RegisterDebugRenderers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.function.Supplier;

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

            NetworkManager.registerClientReceiver(
                BirdDebugPayload.TYPE,
                BirdDebugPayload.STREAM_CODEC,
                (payload, context) ->
                    BirdDebugPayload.onReceive(payload)
            );
            NetworkManager.registerClientReceiver(
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
        RenderRegistry.modelLayer(ScarecrowModel.MODEL_LAYER, ScarecrowModel::createBodyLayer);
        RenderRegistry.modelLayer(ScarecrowModel.INNER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(HAT_DILATION));
        RenderRegistry.modelLayer(ScarecrowModel.OUTER_ARMOR, () -> ScarecrowArmorModel.createBodyLayer(ARMOR_DILATION));

        if(FPConfig.get().replaceChicken) {
            RenderRegistry.modelLayer(CustomChickenModel.MODEL_LAYER, CustomChickenModel::createBodyLayer);
            RenderRegistry.modelLayer(CustomBabyChickenModel.MODEL_LAYER, CustomBabyChickenModel::createBodyLayer);
        }

        if(FPConfig.get().replaceParrot) {
            RenderRegistry.modelLayer(CustomParrotModel.MODEL_LAYER, CustomParrotModel::createBodyLayer);
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

        if(FPConfig.get().replaceChicken) {
            RenderRegistry.entityRenderer(Suppliers.ofInstance(EntityType.CHICKEN), CustomChickenRenderer::new);
        }

        if(FPConfig.get().replaceParrot) {
            RenderRegistry.entityRenderer(Suppliers.ofInstance(EntityType.PARROT), CustomParrotRenderer::new);
        }
    }

    private static <T extends BirdEntity & GeoEntity> void registerBird(Supplier<EntityType<T>> entity) {
        RenderRegistry.entityRenderer(entity, context -> new BirdRenderer<>(context, entity));
    }

    public static void registerParticleFactories() {
        RenderRegistry.particleFactory(FPParticleTypes.SMALL_BUBBLE, SmallBubbleParticle.Provider::new);
    }
}
