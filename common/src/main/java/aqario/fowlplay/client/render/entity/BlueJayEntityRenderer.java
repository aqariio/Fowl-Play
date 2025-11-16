package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.BlueJayEntityModel;
import aqario.fowlplay.common.entity.BlueJayEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BlueJayEntityRenderer extends MobRenderer<BlueJayEntity, BlueJayEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/blue_jay/blue_jay.png");

    public BlueJayEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BlueJayEntityModel(context.bakeLayer(BlueJayEntityModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(BlueJayEntity entity) {
        return TEXTURE;
    }
}
