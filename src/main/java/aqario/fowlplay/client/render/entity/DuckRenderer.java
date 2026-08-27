package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.DuckModel;
import aqario.fowlplay.client.render.entity.state.DuckRenderState;
import aqario.fowlplay.common.entity.bird.waterfowl.DuckEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class DuckRenderer extends MobRenderer<DuckEntity, DuckRenderState, DuckModel> {
    private static final Identifier QUACKERS_TEXTURE = FowlPlay.id("textures/entity/duck/quackers.png");

    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel(context.bakeLayer(DuckModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
    }

    @Override
    public DuckRenderState createRenderState() {
        return new DuckRenderState();
    }

    @Override
    public void extractRenderState(DuckEntity entity, DuckRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        String customName = ChatFormatting.stripFormatting(entity.getName().getString());
        if(customName.equals("Quackers")) {
            state.texture = QUACKERS_TEXTURE;
        }
        else {
            state.texture = entity.getVariant().value().texture(false, entity.isDomestic());
        }
    }

    @Override
    public Identifier getTextureLocation(DuckRenderState state) {
        return state.texture;
    }
}
