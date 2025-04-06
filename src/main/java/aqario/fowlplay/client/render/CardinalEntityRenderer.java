package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.CardinalEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.entity.CardinalEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class CardinalEntityRenderer extends MobEntityRenderer<CardinalEntity, CardinalEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/cardinal/cardinal.png");

    public CardinalEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CardinalEntityModel(context.getPart(CardinalEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(CardinalEntity entity) {
        return TEXTURE;
    }
}
