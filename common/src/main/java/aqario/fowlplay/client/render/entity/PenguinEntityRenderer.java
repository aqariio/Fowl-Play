package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.BabyPenguinEntityModel;
import aqario.fowlplay.client.render.entity.model.PenguinEntityModel;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class PenguinEntityRenderer extends MobRenderer<PenguinEntity, PenguinEntityModel> {
    private static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/penguin/penguin.png");
    private static final ResourceLocation BABY_TEXTURE = FowlPlay.id("textures/entity/penguin/penguin_baby.png");
    private final AdultBabyModelPair<PenguinEntityModel> modelPair;

    public PenguinEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinEntityModel(context.bakeLayer(PenguinEntityModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.145, -0.1475)
        ));
        this.modelPair = bakeModels(context);
    }

    private static AdultBabyModelPair<PenguinEntityModel> bakeModels(EntityRendererProvider.Context context) {
        return AdultBabyModelPair.of(
            new PenguinEntityModel(context.bakeLayer(PenguinEntityModel.MODEL_LAYER)),
            new BabyPenguinEntityModel(context.bakeLayer(BabyPenguinEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(PenguinEntity penguin, float f, float g, PoseStack matrices, MultiBufferSource vertexConsumerProvider, int i) {
        this.model = this.modelPair.getModel(penguin.isBaby());
        if(penguin.isBaby()) {
            matrices.scale(0.8F, 0.8F, 0.8F);
        }
        super.render(penguin, f, g, matrices, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(PenguinEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    protected void scale(PenguinEntity penguin, PoseStack matrices, float amount) {
        super.scale(penguin, matrices, amount);

        String name = ChatFormatting.stripFormatting(penguin.getName().getString());
        if(name.equalsIgnoreCase("rico")) {
            matrices.scale(1.1F, 1F, 1F);
        }
        if(name.equalsIgnoreCase("skipper")) {
            matrices.scale(1.25F, 0.9F, 1F);
        }
        if(name.equalsIgnoreCase("kowalski")) {
            matrices.scale(1F, 1.1F, 1F);
        }
        if(name.equalsIgnoreCase("private")) {
            matrices.scale(1.2F, 0.85F, 1F);
        }
    }
}
