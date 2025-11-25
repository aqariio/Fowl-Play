package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.HawkModel;
import aqario.fowlplay.common.entity.HawkEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class HawkRenderer extends MobRenderer<HawkEntity, HawkModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/hawk/red_tailed_hawk.png");

    public HawkRenderer(EntityRendererProvider.Context context) {
        super(context, new HawkModel(context.bakeLayer(HawkModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(HawkEntity hawk) {
        return TEXTURE;
    }
}
