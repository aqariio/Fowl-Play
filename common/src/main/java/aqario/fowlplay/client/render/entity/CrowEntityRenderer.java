package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.CrowEntityModel;
import aqario.fowlplay.common.entity.CrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class CrowEntityRenderer extends MobRenderer<CrowEntity, CrowEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/crow/crow.png");

    public CrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new CrowEntityModel(context.bakeLayer(CrowEntityModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.0225, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(CrowEntity entity) {
        return TEXTURE;
    }
}
