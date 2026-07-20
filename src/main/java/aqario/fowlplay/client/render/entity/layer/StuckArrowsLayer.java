package aqario.fowlplay.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;

public class StuckArrowsLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public StuckArrowsLayer(EntityRendererProvider.Context context, LivingEntityRenderer<T, M> entityRenderer) {
        super(entityRenderer);
        this.dispatcher = context.getEntityRenderDispatcher();
    }

    protected int getObjectCount(T entity) {
        return entity.getArrowCount();
    }

    protected void renderObject(
        PoseStack matrices,
        MultiBufferSource vertexConsumers,
        int light,
        Entity entity,
        float directionX,
        float directionY,
        float directionZ,
        float tickDelta
    ) {
        float f = Mth.sqrt(directionX * directionX + directionZ * directionZ);
        Arrow arrowEntity = new Arrow(entity.level(), entity.getX(), entity.getY(), entity.getZ(), ItemStack.EMPTY, null);
        arrowEntity.setYRot((float) (Math.atan2(directionX, directionZ) * 180.0F / (float) Math.PI));
        arrowEntity.setXRot((float) (Math.atan2(directionY, f) * 180.0F / (float) Math.PI));
        arrowEntity.yRotO = arrowEntity.getYRot();
        arrowEntity.xRotO = arrowEntity.getXRot();
        this.dispatcher.render(arrowEntity, 0.0, 0.0, 0.0, 0.0F, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public void render(
        PoseStack matrixStack,
        MultiBufferSource vertexConsumerProvider,
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
        RandomSource random = RandomSource.create(entity.getId());
        if(m > 0) {
            for(int n = 0; n < m; n++) {
                matrixStack.pushPose();
                ModelPart modelPart = this.getParentModel().body;
                ModelPart.Cube cuboid = modelPart.getRandomCube(random);
                modelPart.translateAndRotate(matrixStack);
                float o = random.nextFloat();
                float p = random.nextFloat();
                float q = random.nextFloat();
                float r = Mth.lerp(o, cuboid.minX, cuboid.maxX) / 16.0F;
                float s = Mth.lerp(p, cuboid.minY, cuboid.maxY) / 16.0F;
                float t = Mth.lerp(q, cuboid.minZ, cuboid.maxZ) / 16.0F;
                matrixStack.translate(r, s, t);
                o = -1.0F * (o * 2.0F - 1.0F);
                p = -1.0F * (p * 2.0F - 1.0F);
                q = -1.0F * (q * 2.0F - 1.0F);
                this.renderObject(matrixStack, vertexConsumerProvider, i, entity, o, p, q, h);
                matrixStack.popPose();
            }
        }
    }
}