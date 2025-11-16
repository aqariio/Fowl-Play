package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.RavenEntityModel;
import aqario.fowlplay.common.entity.RavenEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class RavenEntityRenderer extends MobRenderer<RavenEntity, RavenEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/raven/raven.png");

    public RavenEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new RavenEntityModel(context.bakeLayer(RavenEntityModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(RavenEntity raven) {
        return TEXTURE;
    }
}
