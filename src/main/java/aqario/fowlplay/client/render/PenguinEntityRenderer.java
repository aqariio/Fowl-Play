package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.BabyPenguinEntityModel;
import aqario.fowlplay.client.model.PenguinEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class PenguinEntityRenderer extends MobEntityRenderer<PenguinEntity, PenguinEntityModel> {
    private static final Identifier TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/penguin/penguin.png");
    private static final Identifier BABY_TEXTURE = Identifier.of(FowlPlay.ID, "textures/entity/penguin/penguin_baby.png");
    private final Map<Boolean, PenguinEntityModel> models;

    public PenguinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PenguinEntityModel(context.getPart(PenguinEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.145, -0.1475)
        ));
        this.models = bakeModels(context);
    }

    private static Map<Boolean, PenguinEntityModel> bakeModels(EntityRendererFactory.Context context) {
        return Map.of(
            false,
            new PenguinEntityModel(context.getPart(PenguinEntityModel.MODEL_LAYER)),
            true,
            new BabyPenguinEntityModel(context.getPart(BabyPenguinEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(PenguinEntity penguin, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = this.models.get(penguin.isBaby());
        if (penguin.isBaby()) {
            matrices.scale(0.8F, 0.8F, 0.8F);
        }
        super.render(penguin, f, g, matrices, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(PenguinEntity entity) {
        return entity.isBaby() ? BABY_TEXTURE : TEXTURE;
    }

    @Override
    protected void scale(PenguinEntity penguin, MatrixStack matrices, float amount) {
        super.scale(penguin, matrices, amount);

        String name = Formatting.strip(penguin.getName().getString());
        if (name.equalsIgnoreCase("rico")) {
            matrices.scale(1.1F, 1F, 1F);
        }
        if (name.equalsIgnoreCase("skipper")) {
            matrices.scale(1.25F, 0.9F, 1F);
        }
        if (name.equalsIgnoreCase("kowalski")) {
            matrices.scale(1F, 1.1F, 1F);
        }
        if (name.equalsIgnoreCase("private")) {
            matrices.scale(1.2F, 0.85F, 1F);
        }
    }
}
