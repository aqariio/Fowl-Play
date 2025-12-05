package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.DuckModel;
import aqario.fowlplay.common.entity.DuckEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class DuckRenderer extends MobRenderer<DuckEntity, DuckModel> {
    private static final ResourceLocation QUACKERS_TEXTURE = FowlPlay.id("textures/entity/duck/quackers.png");

    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel(context.bakeLayer(DuckModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(DuckEntity duck) {
        String customName = ChatFormatting.stripFormatting(duck.getName().getString());
        if(customName.equals("Quackers")) {
            return QUACKERS_TEXTURE;
        }
        return duck.getVariant().value().texture(false);
    }
}
