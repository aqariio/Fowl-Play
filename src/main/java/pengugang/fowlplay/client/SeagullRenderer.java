package pengugang.fowlplay.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import pengugang.fowlplay.FowlPlay;
import pengugang.fowlplay.entity.PenguinEntity;
import pengugang.fowlplay.entity.SeagullEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SeagullRenderer extends GeoEntityRenderer<SeagullEntity> {
    public SeagullRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SeagullModel());
    }

    @Override
    public Identifier getTextureLocation(SeagullEntity instance) {
        return new Identifier(FowlPlay.MODID, "textures/entity/seagull/seagull.png");
    }

    @Override
    public RenderLayer getRenderType(SeagullEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        stack.scale(1.2f, 1.2f, 1.2f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
