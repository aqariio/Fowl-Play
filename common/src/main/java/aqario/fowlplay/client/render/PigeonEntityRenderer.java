package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.PigeonEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.feature.PigeonBundleFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PigeonEntityRenderer extends MobEntityRenderer<PigeonEntity, PigeonEntityModel> {
    public PigeonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PigeonEntityModel(context.getPart(PigeonEntityModel.MODEL_LAYER)), 0.2f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.0225F, -0.25625F);
            }
        });
        this.addFeature(new PigeonBundleFeatureRenderer(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(PigeonEntity entity) {
        return Identifier.of(FowlPlay.ID, "textures/entity/pigeon/pigeon.png");
    }
}
