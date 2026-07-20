package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.VultureModel;
import aqario.fowlplay.common.entity.bird.raptor.VultureEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class VultureRenderer extends MobRenderer<VultureEntity, VultureModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/hawk/red_tailed_hawk.png");

    public VultureRenderer(EntityRendererProvider.Context context) {
        super(context, new VultureModel(context.bakeLayer(VultureModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(VultureEntity vulture) {
        return TEXTURE;
    }
}
