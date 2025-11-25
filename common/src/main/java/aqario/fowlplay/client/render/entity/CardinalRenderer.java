package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.CardinalModel;
import aqario.fowlplay.common.entity.CardinalEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class CardinalRenderer extends MobRenderer<CardinalEntity, CardinalModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/cardinal/cardinal.png");

    public CardinalRenderer(EntityRendererProvider.Context context) {
        super(context, new CardinalModel(context.bakeLayer(CardinalModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemLayer<>(
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
