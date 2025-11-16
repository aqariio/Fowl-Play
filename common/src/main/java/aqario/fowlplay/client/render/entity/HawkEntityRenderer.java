package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.HawkEntityModel;
import aqario.fowlplay.common.entity.HawkEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class HawkEntityRenderer extends MobRenderer<HawkEntity, HawkEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/hawk/red_tailed_hawk.png");

    public HawkEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HawkEntityModel(context.bakeLayer(HawkEntityModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
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
