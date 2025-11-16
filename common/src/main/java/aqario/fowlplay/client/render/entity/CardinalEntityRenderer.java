package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.CardinalEntityModel;
import aqario.fowlplay.common.entity.CardinalEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class CardinalEntityRenderer extends MobRenderer<CardinalEntity, CardinalEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/cardinal/cardinal.png");

    public CardinalEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new CardinalEntityModel(context.bakeLayer(CardinalEntityModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(CardinalEntity entity) {
        return TEXTURE;
    }
}
