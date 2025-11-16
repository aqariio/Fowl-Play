package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.DuckEntityModel;
import aqario.fowlplay.common.entity.DuckEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class DuckEntityRenderer extends MobRenderer<DuckEntity, DuckEntityModel> {
    private static final ResourceLocation QUACKERS_TEXTURE = FowlPlay.id("textures/entity/duck/quackers.png");

    public DuckEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckEntityModel(context.bakeLayer(DuckEntityModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(DuckEntity duck) {
        String string = ChatFormatting.stripFormatting(duck.getName().getString());
        if("Quackers".equals(string)) {
            return QUACKERS_TEXTURE;
        }
        return duck.getVariant().texture();
    }
}
