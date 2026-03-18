package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.CustomBabyChickenModel;
import aqario.fowlplay.client.render.entity.model.CustomChickenModel;
import aqario.fowlplay.common.entity.variant.ChickenVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Chicken;

public class CustomChickenRenderer extends MobRenderer<Chicken, CustomChickenModel> {
    private final AdultBabyModelPair<CustomChickenModel> modelPair;

    public CustomChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomChickenModel(context.bakeLayer(CustomChickenModel.MODEL_LAYER)), 0.3f);
        this.modelPair = bakeModels(context);
    }

    private static AdultBabyModelPair<CustomChickenModel> bakeModels(EntityRendererProvider.Context context) {
        return new AdultBabyModelPair<>(
            new CustomChickenModel(context.bakeLayer(CustomChickenModel.MODEL_LAYER)),
            new CustomBabyChickenModel(context.bakeLayer(CustomBabyChickenModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(Chicken chicken, float f, float g, PoseStack matrices, MultiBufferSource vertexConsumers, int i) {
        this.model = this.modelPair.getModel(chicken.isBaby());
        if(chicken.isBaby()) {
            matrices.scale(0.8F, 0.8F, 0.8F);
        }
        super.render(chicken, f, g, matrices, vertexConsumers, i);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResourceLocation getTextureLocation(Chicken chicken) {
        return ((VariantHolder<Holder<ChickenVariant>>) chicken).getVariant().value().texture(chicken.isBaby());
    }
}
