package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.CustomBabyChickenEntityModel;
import aqario.fowlplay.client.render.entity.model.CustomChickenEntityModel;
import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;

public class CustomChickenEntityRenderer extends MobEntityRenderer<ChickenEntity, CustomChickenEntityModel> {
    private final AdultBabyModelPair<CustomChickenEntityModel> modelPair;

    public CustomChickenEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CustomChickenEntityModel(context.getPart(CustomChickenEntityModel.MODEL_LAYER)), 0.3f);
        this.modelPair = bakeModels(context);
    }

    private static AdultBabyModelPair<CustomChickenEntityModel> bakeModels(EntityRendererFactory.Context context) {
        return AdultBabyModelPair.of(
            new CustomChickenEntityModel(context.getPart(CustomChickenEntityModel.MODEL_LAYER)),
            new CustomBabyChickenEntityModel(context.getPart(CustomBabyChickenEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(ChickenEntity chicken, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i) {
        this.model = this.modelPair.getModel(chicken.isBaby());
        if(chicken.isBaby()) {
            matrices.scale(0.8F, 0.8F, 0.8F);
        }
        super.render(chicken, f, g, matrices, vertexConsumers, i);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Identifier getTexture(ChickenEntity chicken) {
        return chicken.isBaby()
            ? FowlPlay.id("textures/entity/chicken/" + ((VariantHolder<ChickenVariant>) chicken).getVariant().id() + "_baby_chicken.png")
            : FowlPlay.id("textures/entity/chicken/" + ((VariantHolder<ChickenVariant>) chicken).getVariant().id() + "_chicken.png");
    }
}
