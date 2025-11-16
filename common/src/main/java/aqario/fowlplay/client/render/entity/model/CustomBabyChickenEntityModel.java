package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.ChickenAnimations;
import aqario.fowlplay.common.util.ChickenAnimationStates;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.math.MathHelper;

public class CustomBabyChickenEntityModel extends CustomChickenEntityModel {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(FowlPlay.id("baby_chicken"), "main");

    public CustomBabyChickenEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.5F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.25F, -1.25F));

        head.addChild("beak", ModelPartBuilder.create().uv(16, 5).cuboid(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.5F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -3.0F, -3.5F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.5F, 1.5F, -0.1745F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(8, 8).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -1.25F, -0.5F, -0.1745F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(8, 8).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -1.25F, -0.5F, -0.1745F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create(), ModelTransform.of(1.0F, -4.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create(), ModelTransform.pivot(4.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create(), ModelTransform.of(-1.0F, -4.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create(), ModelTransform.pivot(-4.5F, 0.0F, -1.0F));

        body.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, -0.5F, 3.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 3.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(1, 1).mirrored().cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 3.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(1, 1).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(ChickenEntity chicken, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(netHeadYaw, headPitch);

        if (chicken.isOnGround() && !chicken.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(ChickenAnimations.WALKING, limbSwing, limbSwingAmount, 3F, 3F);
        }
        this.updateAnimation(((ChickenAnimationStates) chicken).fowlplay$getStandingState(), ChickenAnimations.STANDING, ageInTicks);
        this.updateAnimation(((ChickenAnimationStates) chicken).fowlplay$getFloatingState(), ChickenAnimations.FLOATING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}