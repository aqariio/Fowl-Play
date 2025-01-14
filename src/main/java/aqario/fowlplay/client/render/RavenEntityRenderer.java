package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.RavenEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.RavenEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RavenEntityRenderer extends MobEntityRenderer<RavenEntity, RavenEntityModel> {
    public RavenEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RavenEntityModel(context.getPart(RavenEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(RavenEntity raven) {
        return Identifier.of(FowlPlay.ID, "textures/entity/raven/raven.png");
    }
}
