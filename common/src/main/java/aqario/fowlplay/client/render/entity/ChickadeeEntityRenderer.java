package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.ChickadeeEntityModel;
import aqario.fowlplay.common.entity.ChickadeeEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ChickadeeEntityRenderer extends MobRenderer<ChickadeeEntity, ChickadeeEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/chickadee/black_capped_chickadee.png");

    public ChickadeeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ChickadeeEntityModel(context.bakeLayer(ChickadeeEntityModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(ChickadeeEntity entity) {
        return TEXTURE;
    }
}
