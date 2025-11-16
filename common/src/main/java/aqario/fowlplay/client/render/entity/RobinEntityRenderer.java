package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.RobinEntityModel;
import aqario.fowlplay.common.entity.RobinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class RobinEntityRenderer extends MobRenderer<RobinEntity, RobinEntityModel> {
    public RobinEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new RobinEntityModel(context.bakeLayer(RobinEntityModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(RobinEntity entity) {
        return FowlPlay.id("textures/entity/robin/" + entity.getVariant().getId() + "_robin.png");
    }
}
