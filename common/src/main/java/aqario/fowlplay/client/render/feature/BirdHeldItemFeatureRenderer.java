package aqario.fowlplay.client.render.feature;

import aqario.fowlplay.client.model.BirdEntityModel;
import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.Vec3d;

public abstract class BirdHeldItemFeatureRenderer<E extends BirdEntity, M extends BirdEntityModel<E>> extends FeatureRenderer<E, M> {
    private final HeldItemRenderer itemRenderer;

    public BirdHeldItemFeatureRenderer(FeatureRendererContext<E, M> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.itemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, E bird, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();

        matrices.translate(
            this.getContextModel().root.pivotX / 16.0F,
            this.getContextModel().root.pivotY / 16.0F,
            this.getContextModel().root.pivotZ / 16.0F
        );
        matrices.rotate(Axis.Z_POSITIVE.rotation(this.getContextModel().root.getTransform().roll));
        matrices.rotate(Axis.Y_POSITIVE.rotation(this.getContextModel().root.getTransform().yaw));
        matrices.rotate(Axis.X_POSITIVE.rotation(this.getContextModel().root.getTransform().pitch));

        matrices.translate(
            this.getContextModel().body.pivotX / 16.0F,
            this.getContextModel().body.pivotY / 16.0F,
            this.getContextModel().body.pivotZ / 16.0F
        );
        matrices.rotate(Axis.Z_POSITIVE.rotation(this.getContextModel().body.getTransform().roll));
        matrices.rotate(Axis.Y_POSITIVE.rotation(this.getContextModel().body.getTransform().yaw));
        matrices.rotate(Axis.X_POSITIVE.rotation(this.getContextModel().body.getTransform().pitch));

        matrices.translate(
            this.getContextModel().neck.pivotX / 16.0F,
            this.getContextModel().neck.pivotY / 16.0F,
            this.getContextModel().neck.pivotZ / 16.0F
        );
        matrices.rotate(Axis.Z_POSITIVE.rotation(this.getContextModel().neck.getTransform().roll));
        matrices.rotate(Axis.Y_POSITIVE.rotation(this.getContextModel().neck.getTransform().yaw));
        matrices.rotate(Axis.X_POSITIVE.rotation(this.getContextModel().neck.getTransform().pitch));

        matrices.translate(
            this.getContextModel().head.pivotX / 16.0F,
            this.getContextModel().head.pivotY / 16.0F,
            this.getContextModel().head.pivotZ / 16.0F
        );
        matrices.rotate(Axis.Z_POSITIVE.rotation(this.getContextModel().head.getTransform().roll));
        matrices.rotate(Axis.Y_POSITIVE.rotation(this.getContextModel().head.getTransform().yaw));
        matrices.rotate(Axis.X_POSITIVE.rotation(this.getContextModel().head.getTransform().pitch));

        matrices.translate(this.getItemOffset().x, this.getItemOffset().y, this.getItemOffset().z);
        matrices.rotate(Axis.X_POSITIVE.rotationDegrees(90.0F));
        matrices.scale(0.5F, 0.5F, 0.5F);

        ItemStack stack = bird.getEquippedStack(EquipmentSlot.MAINHAND);
        this.itemRenderer.renderItem(bird, stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light);
        matrices.pop();
    }

    // Should be ((number of pixels offset from head pivot point) / 16 + 0.0225) * -1
    public abstract Vec3d getItemOffset();
}
