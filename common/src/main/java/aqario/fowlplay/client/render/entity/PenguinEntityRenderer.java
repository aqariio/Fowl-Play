package aqario.fowlplay.client.render.entity;

import aqario.fowlplay.client.render.entity.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.client.render.entity.model.AdultBabyModelPair;
import aqario.fowlplay.client.render.entity.model.BabyPenguinEntityModel;
import aqario.fowlplay.client.render.entity.model.PenguinEntityModel;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PenguinEntityRenderer extends MobEntityRenderer<PenguinEntity, PenguinEntityModel> {
    private static final Identifier TEXTURE = FowlPlay.id("textures/entity/penguin/penguin.png");
    private static final Identifier BABY_TEXTURE = FowlPlay.id("textures/entity/penguin/penguin_baby.png");
    private final AdultBabyModelPair<PenguinEntityModel> modelPair;

    public PenguinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PenguinEntityModel(context.getPart(PenguinEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(
            this,
            context.getHeldItemRenderer(),
            new Vec3d(0.0, -0.145, -0.1475)
        ));
        this.modelPair = bakeModels(context);
    }

    private static AdultBabyModelPair<PenguinEntityModel> bakeModels(EntityRendererFactory.Context context) {
        return AdultBabyModelPair.of(
            new PenguinEntityModel(context.getPart(PenguinEntityModel.MODEL_LAYER)),
            new BabyPenguinEntityModel(context.getPart(BabyPenguinEntityModel.MODEL_LAYER))
        );
    }

    @Override
    public void render(PenguinEntity penguin, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = this.modelPair.getModel(penguin.isBaby());
        if(penguin.isBaby()) {
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
