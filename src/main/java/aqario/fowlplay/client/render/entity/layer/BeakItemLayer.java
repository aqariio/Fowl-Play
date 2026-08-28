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

public class BeakItemLayer<T extends BirdEntity & GeoEntity> extends BlockAndItemGeoLayer<T> {
    private static final String BEAK_NAME = "beak";

    public BeakItemLayer(GeoRenderer<T> renderer) {
        super(
            renderer,
            (bone, entity) -> bone.getName().equals(BEAK_NAME)
                ? entity.getMainHandItem()
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
        if(bone.getName().equals(BEAK_NAME)) {
            poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
            poseStack.scale(0.5F, 0.5F, 0.5F);
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
