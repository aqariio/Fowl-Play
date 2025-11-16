package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.RobinEntityModel;
import aqario.fowlplay.common.entity.RobinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RobinEntityRenderer extends MobEntityRenderer<RobinEntity, RobinEntityModel> {
    public RobinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RobinEntityModel(context.getPart(RobinEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(RobinEntity entity) {
        return FowlPlay.id("textures/entity/robin/" + entity.getVariant().getId() + "_robin.png");
    }
}
