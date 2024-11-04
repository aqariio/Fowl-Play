package aqario.fowlplay.client.render;

import aqario.fowlplay.client.model.PenguinEntityModel;
import aqario.fowlplay.client.render.feature.BirdHeldItemFeatureRenderer;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PenguinEntityRenderer extends MobEntityRenderer<PenguinEntity, PenguinEntityModel> {
    public PenguinEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PenguinEntityModel(context.getPart(PenguinEntityModel.MODEL_LAYER)), 0.3f);
        this.addFeature(new BirdHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()) {
            @Override
            public Vec3d getItemOffset() {
                return new Vec3d(0.0F, -0.145F, -0.31875F);
            }
        });
    }

    @Override
    public Identifier getTexture(PenguinEntity entity) {
        return entity.isBaby() ? Identifier.of(FowlPlay.ID, "textures/entity/penguin/penguin_baby.png") : Identifier.of(FowlPlay.ID, "textures/entity/penguin/penguin.png");
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
