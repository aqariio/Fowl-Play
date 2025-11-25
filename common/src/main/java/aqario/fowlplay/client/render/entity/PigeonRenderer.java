package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.layer.PigeonBundleLayer;
import aqario.fowlplay.client.render.entity.model.PigeonModel;
import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class PigeonRenderer extends MobRenderer<PigeonEntity, PigeonModel> {
    private static final ResourceLocation MARTHA_TEXTURE = FowlPlay.id("textures/entity/pigeon/martha.png");

    public PigeonRenderer(EntityRendererProvider.Context context) {
        super(context, new PigeonModel(context.bakeLayer(PigeonModel.MODEL_LAYER)), 0.2f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.0225, -0.1475)
        ));
        this.addLayer(new PigeonBundleLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(PigeonEntity pigeon) {
        String string = ChatFormatting.stripFormatting(pigeon.getName().getString());
        if ("Martha".equals(string)) {
            return MARTHA_TEXTURE;
        }
        return pigeon.getVariant().value().texture();
    }
}
