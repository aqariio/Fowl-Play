package aqario.fowlplay.client.render.entity.feature;

import aqario.fowlplay.client.render.entity.model.PigeonEntityModel;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Axis;

public class PigeonBundleFeatureRenderer extends RenderLayer<PigeonEntity, PigeonEntityModel> {
    private final ItemInHandRenderer itemRenderer;

    public PigeonBundleFeatureRenderer(RenderLayerParent<PigeonEntity, PigeonEntityModel> context, ItemInHandRenderer heldItemRenderer) {
        super(context);
        this.itemRenderer = heldItemRenderer;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, PigeonEntity pigeon, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
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
            this.getParentModel().leftLeg.x / 16.0F,
            this.getParentModel().leftLeg.y / 16.0F,
            this.getParentModel().leftLeg.z / 16.0F
        );
        matrices.mulPose(Axis.ZP.rotation(this.getParentModel().leftLeg.storePose().zRot));
        matrices.mulPose(Axis.YP.rotation(this.getParentModel().leftLeg.storePose().yRot));
        matrices.mulPose(Axis.XP.rotation(this.getParentModel().leftLeg.storePose().xRot));

        matrices.translate(0.03125F, 0.075F, 0.0F);
        matrices.mulPose(Axis.XP.rotationDegrees(180.0F));
        matrices.mulPose(Axis.YP.rotationDegrees(90.0F));
        matrices.scale(0.25F, 0.25F, 0.25F);

        ItemStack stack = pigeon.getItemBySlot(EquipmentSlot.OFFHAND);
        this.itemRenderer.renderItem(pigeon, stack, ItemDisplayContext.GROUND, false, matrices, vertexConsumers, light);
        matrices.popPose();
    }
}
