package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.GullEntityModel;
import aqario.fowlplay.common.entity.GullEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class GullEntityRenderer extends MobRenderer<GullEntity, GullEntityModel> {
    public GullEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new GullEntityModel(context.bakeLayer(GullEntityModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(GullEntity gull) {
        return gull.getVariant().texture();
    }
}
