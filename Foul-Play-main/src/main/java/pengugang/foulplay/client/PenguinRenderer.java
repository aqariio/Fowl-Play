package pengugang.foulplay.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import pengugang.foulplay.FoulPlay;
import pengugang.foulplay.entity.PenguinEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {
    public PenguinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PenguinModel());
    }

    @Override
    public Identifier getTextureLocation(PenguinEntity instance) {
        return instance.isBaby() ? new Identifier(FoulPlay.MODID, "textures/entity/penguin/penguin_baby.png") : new Identifier(FoulPlay.MODID, "textures/entity/penguin/penguin.png");
    }

    @Override
    public RenderLayer getRenderType(PenguinEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        if (animatable.isBaby()) {
            stack.scale(0.8f, 0.8f, 0.8f);
        } else {
            stack.scale(1f, 1f, 1f);
        }

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
