package aqario.fowlplay.client.render.entity.layer;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class BundleItemLayer<T extends BirdEntity & GeoEntity> extends BlockAndItemGeoLayer<T> {
    private static final String LEFT_LEG_NAME = "left_leg";

    public BundleItemLayer(GeoRenderer<T> renderer) {
        super(
            renderer,
            (bone, entity) -> bone.getName().equals(LEFT_LEG_NAME)
                ? entity.getOffhandItem()
                : null,
            (bone, entity) -> null
        );
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
        return ItemDisplayContext.GROUND;
    }

    @Override
    protected void renderStackForBone(
        PoseStack poseStack,
        GeoBone bone,
        ItemStack stack,
        T animatable,
        MultiBufferSource bufferSource,
        float partialTick,
        int packedLight,
        int packedOverlay
    ) {
        if(bone.getName().equals(LEFT_LEG_NAME)) {
            poseStack.translate(0.03125F, 0.075F, 0.0F);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            poseStack.scale(0.25F, 0.25F, 0.25F);
        }
        super.renderStackForBone(
            poseStack,
            bone,
            stack,
            animatable,
            bufferSource,
            partialTick,
            packedLight,
            packedOverlay
        );
    }
}
