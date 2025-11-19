package aqario.fowlplay.client.render.entity.layer;

import aqario.fowlplay.client.render.entity.model.BirdModel;
import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class BirdHeldItemLayer<E extends BirdEntity, M extends BirdModel<E>> extends RenderLayer<E, M> {
    private final ItemInHandRenderer itemRenderer;
    // Z should be ((number of pixels offset from head pivot point) / 16 + 0.0225) * -1
    private final Vec3 heldItemOffset;

    public BirdHeldItemLayer(RenderLayerParent<E, M> context, ItemInHandRenderer heldItemRenderer, Vec3 heldItemOffset) {
        super(context);
        this.itemRenderer = heldItemRenderer;
        this.heldItemOffset = heldItemOffset;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, E bird, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.pushPose();

        matrices.translate(
            this.getParentModel().root.x / 16.0F,
            this.getParentModel().root.y / 16.0F,
            this.getParentModel().root.z / 16.0F
        );
        matrices.mulPose(Axis.ZP.rotation(this.getParentModel().root.storePose().zRot));
        matrices.mulPose(Axis.YP.rotation(this.getParentModel().root.storePose().yRot));
        matrices.mulPose(Axis.XP.rotation(this.getParentModel().root.storePose().xRot));

        matrices.translate(
            this.getParentModel().body.x / 16.0F,
            this.getParentModel().body.y / 16.0F,
            this.getParentModel().body.z / 16.0F
        );
        matrices.mulPose(Axis.ZP.rotation(this.getParentModel().body.storePose().zRot));
        matrices.mulPose(Axis.YP.rotation(this.getParentModel().body.storePose().yRot));
        matrices.mulPose(Axis.XP.rotation(this.getParentModel().body.storePose().xRot));

        matrices.translate(
            this.getParentModel().neck.x / 16.0F,
            this.getParentModel().neck.y / 16.0F,
            this.getParentModel().neck.z / 16.0F
        );
        matrices.mulPose(Axis.ZP.rotation(this.getParentModel().neck.storePose().zRot));
        matrices.mulPose(Axis.YP.rotation(this.getParentModel().neck.storePose().yRot));
        matrices.mulPose(Axis.XP.rotation(this.getParentModel().neck.storePose().xRot));

        matrices.translate(
            this.getParentModel().head.x / 16.0F,
            this.getParentModel().head.y / 16.0F,
            this.getParentModel().head.z / 16.0F
        );
        matrices.mulPose(Axis.ZP.rotation(this.getParentModel().head.storePose().zRot));
        matrices.mulPose(Axis.YP.rotation(this.getParentModel().head.storePose().yRot));
        matrices.mulPose(Axis.XP.rotation(this.getParentModel().head.storePose().xRot));

        matrices.translate(this.heldItemOffset.x, this.heldItemOffset.y, this.heldItemOffset.z);
        matrices.mulPose(Axis.XN.rotationDegrees(90.0F));
        matrices.scale(0.5F, 0.5F, 0.5F);

        ItemStack stack = bird.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemRenderer.renderItem(bird, stack, ItemDisplayContext.GROUND, false, matrices, vertexConsumers, light);
        matrices.popPose();
    }
}
