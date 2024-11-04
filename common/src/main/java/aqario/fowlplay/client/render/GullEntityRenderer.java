package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.GullEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.GullEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class GullEntityRenderer extends MobEntityRenderer<GullEntity, GullEntityModel> {
    public GullEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GullEntityModel(context.getPart(GullEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.085F, -0.25F);
            }
        });
    }

    @Override
    public Identifier getTexture(GullEntity gull) {
        return Identifier.of(FowlPlay.ID, "textures/entity/gull/" + gull.getVariant().getId() + "_gull.png");
    }
}
