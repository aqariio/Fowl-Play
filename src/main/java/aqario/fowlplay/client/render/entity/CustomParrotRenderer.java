package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.CustomParrotModel;
import aqario.fowlplay.core.FowlPlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Parrot;

public class CustomParrotRenderer extends MobRenderer<Parrot, CustomParrotModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/parrot/scarlet_macaw_parrot.png");
    private final AdultBabyModelPair<CustomParrotModel> modelPair;

    public CustomParrotRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomParrotModel(context.bakeLayer(CustomParrotModel.MODEL_LAYER)), 0.3f);
        this.modelPair = bakeModels(context);
    }

    private static AdultBabyModelPair<CustomParrotModel> bakeModels(EntityRendererProvider.Context context) {
        return new AdultBabyModelPair<>(
            new CustomParrotModel(context.bakeLayer(CustomParrotModel.MODEL_LAYER)),
            new CustomParrotModel(context.bakeLayer(CustomParrotModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(Parrot parrot, float f, float g, PoseStack matrices, MultiBufferSource vertexConsumers, int i) {
        this.model = this.modelPair.getModel(parrot.isBaby());
        if(parrot.isBaby()) {
            matrices.scale(0.8F, 0.8F, 0.8F);
        }
        super.render(parrot, f, g, matrices, vertexConsumers, i);
    }

    @Override
    public ResourceLocation getTextureLocation(Parrot parrot) {
        return TEXTURE;
    }
}
