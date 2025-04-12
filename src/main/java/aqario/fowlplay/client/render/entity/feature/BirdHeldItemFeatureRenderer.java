package aqario.fowlplay.client.render.entity.feature;

import aqario.fowlplay.client.render.entity.model.BirdEntityModel;
import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class BirdHeldItemFeatureRenderer<E extends BirdEntity, M extends BirdEntityModel<E>> extends FeatureRenderer<E, M> {
    private final HeldItemRenderer itemRenderer;
    // Z should be ((number of pixels offset from head pivot point) / 16 + 0.0225) * -1
    private final Vec3d heldItemOffset;

    public BirdHeldItemFeatureRenderer(FeatureRendererContext<E, M> context, HeldItemRenderer heldItemRenderer, Vec3d heldItemOffset) {
        super(context);
        this.itemRenderer = heldItemRenderer;
        this.heldItemOffset = heldItemOffset;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, E bird, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();

        matrices.translate(
            this.getContextModel().root.pivotX / 16.0F,
            this.getContextModel().root.pivotY / 16.0F,
            this.getContextModel().root.pivotZ / 16.0F
        );
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(this.getContextModel().root.getTransform().roll));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(this.getContextModel().root.getTransform().yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.getContextModel().root.getTransform().pitch));

        matrices.translate(
            this.getContextModel().body.pivotX / 16.0F,
            this.getContextModel().body.pivotY / 16.0F,
            this.getContextModel().body.pivotZ / 16.0F
        );
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(this.getContextModel().body.getTransform().roll));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(this.getContextModel().body.getTransform().yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.getContextModel().body.getTransform().pitch));

        matrices.translate(
            this.getContextModel().neck.pivotX / 16.0F,
            this.getContextModel().neck.pivotY / 16.0F,
            this.getContextModel().neck.pivotZ / 16.0F
        );
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(this.getContextModel().neck.getTransform().roll));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(this.getContextModel().neck.getTransform().yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.getContextModel().neck.getTransform().pitch));

        matrices.translate(
            this.getContextModel().head.pivotX / 16.0F,
            this.getContextModel().head.pivotY / 16.0F,
            this.getContextModel().head.pivotZ / 16.0F
        );
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(this.getContextModel().head.getTransform().roll));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(this.getContextModel().head.getTransform().yaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.getContextModel().head.getTransform().pitch));

        matrices.translate(this.heldItemOffset.x, this.heldItemOffset.y, this.heldItemOffset.z);
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90.0F));
        matrices.scale(0.5F, 0.5F, 0.5F);

        ItemStack stack = bird.getEquippedStack(EquipmentSlot.MAINHAND);
        this.itemRenderer.renderItem(bird, stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
