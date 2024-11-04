package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.CardinalEntityModel;
import aqario.fowlplay.client.model.SparrowEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.SparrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class SparrowEntityRenderer extends MobEntityRenderer<SparrowEntity, SparrowEntityModel> {
    public SparrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SparrowEntityModel(context.getPart(CardinalEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.085F, -0.25625F);
            }
        });
    }

    @Override
    public Identifier getTexture(SparrowEntity entity) {
        return Identifier.of(FowlPlay.ID, "textures/entity/sparrow/house_sparrow.png");
    }
}
