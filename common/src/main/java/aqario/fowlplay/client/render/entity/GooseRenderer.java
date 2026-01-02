package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.layer.BirdHeldItemLayer;
import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.BabyGooseModel;
import aqario.fowlplay.client.render.entity.model.DomesticGooseModel;
import aqario.fowlplay.client.render.entity.model.GooseModel;
import aqario.fowlplay.common.entity.bird.goose.GooseEntity;
import aqario.fowlplay.common.entity.bird.goose.GooseVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class GooseRenderer extends MobRenderer<GooseEntity, GooseModel> {
    private final Map<GooseVariant.ModelType, AdultBabyModelPair<GooseModel>> models;

    public GooseRenderer(EntityRendererProvider.Context context) {
        super(context, new GooseModel(context.bakeLayer(GooseModel.MODEL_LAYER)), 0.3f);
        this.addLayer(new BirdHeldItemLayer<>(
            this,
            context.getItemInHandRenderer(),
            new Vec3(0.0, -0.05375, -0.1475)
        ));
        this.models = bakeModels(context);
    }

    private static Map<GooseVariant.ModelType, AdultBabyModelPair<GooseModel>> bakeModels(EntityRendererProvider.Context context) {
        return Map.of(
            GooseVariant.ModelType.WILD,
            new AdultBabyModelPair<>(
                new GooseModel(context.bakeLayer(GooseModel.MODEL_LAYER)),
                new BabyGooseModel(context.bakeLayer(BabyGooseModel.MODEL_LAYER))
            ),
            GooseVariant.ModelType.DOMESTIC,
            new AdultBabyModelPair<>(
                new DomesticGooseModel(context.bakeLayer(DomesticGooseModel.MODEL_LAYER)),
                new BabyGooseModel(context.bakeLayer(BabyGooseModel.MODEL_LAYER))
            )
        );
    }

    @Override
    public void render(GooseEntity goose, float f, float g, PoseStack matrices, MultiBufferSource vertexConsumerProvider, int i) {
        this.model = this.models.get(goose.getVariant().value().modelType())
            .getModel(goose.isBaby());
        super.render(goose, f, g, matrices, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(GooseEntity goose) {
        return goose.getVariant().value().texture(goose.isBaby());
    }
}
