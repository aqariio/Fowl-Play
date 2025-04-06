package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.SparrowEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.entity.SparrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class SparrowEntityRenderer extends MobEntityRenderer<SparrowEntity, SparrowEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/sparrow/house_sparrow.png");

    public SparrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SparrowEntityModel(context.getPart(SparrowEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(SparrowEntity entity) {
        return TEXTURE;
    }
}
