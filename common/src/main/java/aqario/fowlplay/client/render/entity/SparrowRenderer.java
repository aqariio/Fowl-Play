package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.SparrowModel;
import aqario.fowlplay.common.entity.SparrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class SparrowRenderer extends MobRenderer<SparrowEntity, SparrowModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/sparrow/house_sparrow.png");

    public SparrowRenderer(EntityRendererProvider.Context context) {
        super(context, new SparrowModel(context.bakeLayer(SparrowModel.MODEL_LAYER)), 0.15f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.085, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(SparrowEntity entity) {
        return TEXTURE;
    }
}
