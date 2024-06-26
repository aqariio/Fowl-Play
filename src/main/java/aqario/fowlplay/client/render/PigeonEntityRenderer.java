package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.FowlPlayEntityModelLayers;
import aqario.fowlplay.client.model.PigeonEntityModel;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class PigeonEntityRenderer extends MobEntityRenderer<PigeonEntity, PigeonEntityModel> {
    public PigeonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PigeonEntityModel(context.getPart(FowlPlayEntityModelLayers.PIGEON)), 0.1f);
    }

    @Override
    public Identifier getTexture(PigeonEntity entity) {
        return new Identifier(FowlPlay.ID, "textures/entity/pigeon/pigeon.png");
    }
}
