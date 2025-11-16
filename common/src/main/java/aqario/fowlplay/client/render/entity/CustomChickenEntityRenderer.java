package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.CustomBabyChickenEntityModel;
import aqario.fowlplay.client.render.entity.model.CustomChickenEntityModel;
import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.animal.Chicken;

public class CustomChickenEntityRenderer extends MobRenderer<Chicken, CustomChickenEntityModel> {
    private final AdultBabyModelPair<CustomChickenEntityModel> modelPair;

    public CustomChickenEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomChickenEntityModel(context.bakeLayer(CustomChickenEntityModel.MODEL_LAYER)), 0.3f);
        this.modelPair = bakeModels(context);
    }

    private static AdultBabyModelPair<CustomChickenEntityModel> bakeModels(EntityRendererProvider.Context context) {
        return AdultBabyModelPair.of(
            new CustomChickenEntityModel(context.bakeLayer(CustomChickenEntityModel.MODEL_LAYER)),
            new CustomBabyChickenEntityModel(context.bakeLayer(CustomBabyChickenEntityModel.MODEL_LAYER))
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
        return chicken.isBaby()
            ? FowlPlay.id("textures/entity/chicken/" + ((VariantHolder<ChickenVariant>) chicken).getVariant().id() + "_baby_chicken.png")
            : FowlPlay.id("textures/entity/chicken/" + ((VariantHolder<ChickenVariant>) chicken).getVariant().id() + "_chicken.png");
    }
}
