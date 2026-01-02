package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.GullModel;
import aqario.fowlplay.common.entity.bird.gull.GullEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class GullRenderer extends MobRenderer<GullEntity, GullModel> {
    public GullRenderer(EntityRendererProvider.Context context) {
        super(context, new GullModel(context.bakeLayer(GullModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(GullEntity gull) {
        return gull.getVariant().value().texture();
    }
}
