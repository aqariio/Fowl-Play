package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.BlueJayModel;
import aqario.fowlplay.common.entity.bird.blue_jay.BlueJayEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BlueJayRenderer extends MobRenderer<BlueJayEntity, BlueJayModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/blue_jay/blue_jay.png");

    public BlueJayRenderer(EntityRendererProvider.Context context) {
        super(context, new BlueJayModel(context.bakeLayer(BlueJayModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemLayer<>(
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
