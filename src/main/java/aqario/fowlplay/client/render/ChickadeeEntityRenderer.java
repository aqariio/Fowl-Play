package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.ChickadeeEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.ChickadeeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ChickadeeEntityRenderer extends MobEntityRenderer<ChickadeeEntity, ChickadeeEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/chickadee/black_capped_chickadee.png");

    public ChickadeeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ChickadeeEntityModel(context.getPart(ChickadeeEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(ChickadeeEntity entity) {
        return TEXTURE;
    }
}
