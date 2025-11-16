package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.DomesticGooseEntityModel;
import aqario.fowlplay.client.render.entity.model.GooseEntityModel;
import aqario.fowlplay.common.entity.GooseEntity;
import aqario.fowlplay.common.entity.GooseVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class GooseEntityRenderer extends MobRenderer<GooseEntity, GooseEntityModel> {
    private final Map<GooseVariant.ModelType, GooseEntityModel> models;

    public GooseEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new GooseEntityModel(context.bakeLayer(GooseEntityModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
        this.models = bakeModels(context);
    }

    private static Map<GooseVariant.ModelType, GooseEntityModel> bakeModels(EntityRendererProvider.Context context) {
        return Map.of(
            GooseVariant.ModelType.WILD,
            new GooseEntityModel(context.bakeLayer(GooseEntityModel.MODEL_LAYER)),
            GooseVariant.ModelType.DOMESTIC,
            new DomesticGooseEntityModel(context.bakeLayer(DomesticGooseEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(GooseEntity goose, float f, float g, PoseStack matrices, MultiBufferSource vertexConsumerProvider, int i) {
        this.model = this.models.get(goose.getVariant().modelType());
        super.render(goose, f, g, matrices, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(GooseEntity goose) {
        return goose.getVariant().texture();
    }
}
