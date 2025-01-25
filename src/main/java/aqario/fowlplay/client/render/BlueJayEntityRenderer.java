package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.BlueJayEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.BlueJayEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class BlueJayEntityRenderer extends MobEntityRenderer<BlueJayEntity, BlueJayEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/blue_jay/blue_jay.png");

    public BlueJayEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BlueJayEntityModel(context.getPart(BlueJayEntityModel.MODEL_LAYER)), 0.15f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public Identifier getTexture(BlueJayEntity entity) {
        return TEXTURE;
    }
}
