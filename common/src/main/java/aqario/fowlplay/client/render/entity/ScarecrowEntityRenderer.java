package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.StuckArrowsFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.ScarecrowArmorEntityModel;
import aqario.fowlplay.client.render.entity.model.ScarecrowEntityModel;
import aqario.fowlplay.common.entity.ScarecrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ScarecrowEntityRenderer extends LivingEntityRenderer<ScarecrowEntity, ScarecrowEntityModel> {
    public static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/scarecrow/scarecrow.png");

    public ScarecrowEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ScarecrowEntityModel(ctx.getPart(ScarecrowEntityModel.MODEL_LAYER)), 0.0F);
        this.addFeature(
            new ArmorFeatureRenderer<>(
                this,
                new ScarecrowArmorEntityModel(ctx.getPart(ScarecrowEntityModel.INNER_ARMOR)),
                new ScarecrowArmorEntityModel(ctx.getPart(ScarecrowEntityModel.OUTER_ARMOR)),
                ctx.getModelManager()
            )
        );
        this.addFeature(new StuckArrowsFeatureRenderer<>(ctx, this));
        this.addFeature(new HeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new HeadFeatureRenderer<>(this, ctx.getModelLoader(), ctx.getHeldItemRenderer()));
    }

    @Override
    protected void scale(ScarecrowEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected boolean hasLabel(ScarecrowEntity livingEntity) {
        return false;
    }

    @Override
    public Identifier getTexture(ScarecrowEntity entity) {
        return TEXTURE;
    }
}