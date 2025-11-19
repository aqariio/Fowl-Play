package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.ChickadeeModel;
import aqario.fowlplay.common.entity.ChickadeeEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class ChickadeeRenderer extends MobRenderer<ChickadeeEntity, ChickadeeModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/chickadee/black_capped_chickadee.png");

    public ChickadeeRenderer(EntityRendererProvider.Context context) {
        super(context, new ChickadeeModel(context.bakeLayer(ChickadeeModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemLayer<>(
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
