package aqario.fowlplay.client.render.feature;

import aqario.fowlplay.client.model.PigeonEntityModel;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;

public class PigeonBundleFeatureRenderer extends FeatureRenderer<PigeonEntity, PigeonEntityModel> {
    private final HeldItemRenderer itemRenderer;

    public PigeonBundleFeatureRenderer(FeatureRendererContext<PigeonEntity, PigeonEntityModel> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.itemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PigeonEntity pigeon, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
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
            this.getContextModel().leftLeg.pivotX / 16.0F,
            this.getContextModel().leftLeg.pivotY / 16.0F,
            this.getContextModel().leftLeg.pivotZ / 16.0F
        );
        matrices.rotate(Axis.Z_POSITIVE.rotation(this.getContextModel().leftLeg.getTransform().roll));
        matrices.rotate(Axis.Y_POSITIVE.rotation(this.getContextModel().leftLeg.getTransform().yaw));
        matrices.rotate(Axis.X_POSITIVE.rotation(this.getContextModel().leftLeg.getTransform().pitch));

        matrices.translate(0.03125F, 0.075F, 0.0F);
        matrices.rotate(Axis.X_POSITIVE.rotationDegrees(180.0F));
        matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(90.0F));
        matrices.scale(0.25F, 0.25F, 0.25F);

        ItemStack stack = pigeon.getEquippedStack(EquipmentSlot.OFFHAND);
        this.itemRenderer.renderItem(pigeon, stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
