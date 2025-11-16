package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.StuckArrowsFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.ScarecrowArmorEntityModel;
import aqario.fowlplay.client.render.entity.model.ScarecrowEntityModel;
import aqario.fowlplay.common.entity.ScarecrowEntity;
import aqario.fowlplay.core.FowlPlay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ScarecrowEntityRenderer extends LivingEntityRenderer<ScarecrowEntity, ScarecrowEntityModel> {
    public static final ResourceLocation TEXTURE = FowlPlay.id("textures/entity/scarecrow/scarecrow.png");

    public ScarecrowEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ScarecrowEntityModel(ctx.bakeLayer(ScarecrowEntityModel.MODEL_LAYER)), 0.0F);
        this.addLayer(
            new HumanoidArmorLayer<>(
                this,
                new ScarecrowArmorEntityModel(ctx.bakeLayer(ScarecrowEntityModel.INNER_ARMOR)),
                new ScarecrowArmorEntityModel(ctx.bakeLayer(ScarecrowEntityModel.OUTER_ARMOR)),
                ctx.getModelManager()
            )
        );
        this.addLayer(new StuckArrowsFeatureRenderer<>(ctx, this));
        this.addLayer(new ItemInHandLayer<>(this, ctx.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer<>(this, ctx.getModelSet()));
        this.addLayer(new CustomHeadLayer<>(this, ctx.getModelSet(), ctx.getItemInHandRenderer()));
    }

    @Override
    protected void scale(ScarecrowEntity entity, PoseStack matrices, float amount) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected boolean shouldShowName(ScarecrowEntity livingEntity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(ScarecrowEntity entity) {
        return TEXTURE;
    }
}