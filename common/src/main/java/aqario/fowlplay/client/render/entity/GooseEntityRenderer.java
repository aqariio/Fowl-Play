package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.DomesticGooseEntityModel;
import aqario.fowlplay.client.render.entity.model.GooseEntityModel;
import aqario.fowlplay.common.entity.GooseEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class GooseEntityRenderer extends MobEntityRenderer<GooseEntity, GooseEntityModel> {
    private final Map<Boolean, GooseEntityModel> models;

    public GooseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GooseEntityModel(context.getPart(GooseEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.05375, -0.1475)
        ));
        this.models = bakeModels(context);
    }

    private static Map<Boolean, GooseEntityModel> bakeModels(EntityRendererFactory.Context context) {
        return Map.of(
            false,
            new GooseEntityModel(context.getPart(GooseEntityModel.MODEL_LAYER)),
            true,
            new DomesticGooseEntityModel(context.getPart(DomesticGooseEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(GooseEntity goose, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = this.models.get(goose.getVariant().domestic());
        super.render(goose, f, g, matrices, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(GooseEntity goose) {
        return goose.getVariant().texture();
    }
}
