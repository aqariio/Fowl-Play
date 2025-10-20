package aqario.fowlplay.client.render.entity.feature;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class StuckArrowsFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public StuckArrowsFeatureRenderer(EntityRendererFactory.Context context, LivingEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
        this.dispatcher = context.getRenderDispatcher();
    }

    protected int getObjectCount(T entity) {
        return entity.getStuckArrowCount();
    }

    protected void renderObject(
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        Entity entity,
        float directionX,
        float directionY,
        float directionZ,
        float tickDelta
    ) {
        float f = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
        ArrowEntity arrowEntity = new ArrowEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ());
        arrowEntity.setYaw((float) (Math.atan2(directionX, directionZ) * 180.0F / (float) Math.PI));
        arrowEntity.setPitch((float) (Math.atan2(directionY, f) * 180.0F / (float) Math.PI));
        arrowEntity.prevYaw = arrowEntity.getYaw();
        arrowEntity.prevPitch = arrowEntity.getPitch();
        this.dispatcher.render(arrowEntity, 0.0, 0.0, 0.0, 0.0F, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public void render(
        MatrixStack matrixStack,
        VertexConsumerProvider vertexConsumerProvider,
        int i,
        T entity,
        float f,
        float g,
        float h,
        float j,
        float k,
        float l
    ) {
        int m = this.getObjectCount(entity);
        Random random = Random.create(entity.getId());
        if(m > 0) {
            for(int n = 0; n < m; n++) {
                matrixStack.push();
                ModelPart modelPart = this.getContextModel().body;
                ModelPart.Cuboid cuboid = modelPart.getRandomCuboid(random);
                modelPart.rotate(matrixStack);
                float o = random.nextFloat();
                float p = random.nextFloat();
                float q = random.nextFloat();
                float r = MathHelper.lerp(o, cuboid.minX, cuboid.maxX) / 16.0F;
                float s = MathHelper.lerp(p, cuboid.minY, cuboid.maxY) / 16.0F;
                float t = MathHelper.lerp(q, cuboid.minZ, cuboid.maxZ) / 16.0F;
                matrixStack.translate(r, s, t);
                o = -1.0F * (o * 2.0F - 1.0F);
                p = -1.0F * (p * 2.0F - 1.0F);
                q = -1.0F * (q * 2.0F - 1.0F);
                this.renderObject(matrixStack, vertexConsumerProvider, i, entity, o, p, q, h);
                matrixStack.pop();
            }
        }
    }
}