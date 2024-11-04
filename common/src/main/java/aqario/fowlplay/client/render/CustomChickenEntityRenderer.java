package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.CustomChickenEntityModel;
import aqario.fowlplay.common.FowlPlay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;

public class CustomChickenEntityRenderer extends MobEntityRenderer<ChickenEntity, CustomChickenEntityModel> {
    public CustomChickenEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CustomChickenEntityModel(context.getPart(CustomChickenEntityModel.MODEL_LAYER)), 0.3f);
    }

    @Override
    public Identifier getTexture(ChickenEntity chicken) {
        return Identifier.of(FowlPlay.ID, "textures/entity/chicken/white.png");
    }

    @Override
    public void render(ChickenEntity livingEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i) {
        if (livingEntity.isBaby()) {
            matrices.scale(0.6F, 0.6F, 0.6F);
        }
        super.render(livingEntity, f, g, matrices, vertexConsumers, i);
    }
}
