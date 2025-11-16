package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.CrowAnimations;
import aqario.fowlplay.common.entity.CrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.math.MathHelper;

public class CrowEntityModel extends FlyingBirdEntityModel<CrowEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(FowlPlay.id("crow"), "main");

    public CrowEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, -1.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.25F, 1.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 14).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -2.0F, -2.75F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 9).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.75F, 0.0F, -0.4363F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(10, 7).cuboid(-1.5F, -1.0F, -0.5F, 2.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -3.25F, -2.0F, -0.3927F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(10, 7).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 2.0F, 3.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -3.25F, -2.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(0, 19).cuboid(-0.5F, 0.0F, -1.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.75F, -1.5F, -0.4363F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(-6, 26).cuboid(0.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(0, 19).mirrored().cuboid(-5.5F, 0.0F, -1.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -3.75F, -1.5F, -0.4363F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(-6, 26).mirrored().cuboid(-6.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.5F, 0.0F, -1.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(12, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(16, 0).cuboid(-1.0F, -1.001F, 3.0F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.75F, 0.5F, -0.2618F, 0.0F, 0.0F));

        tail.addChild("cube_r1", ModelPartBuilder.create().uv(16, 0).mirrored().cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -1.0F, 2.25F, 0.0F, -0.5236F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(16, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 2.25F, 0.0F, 0.5236F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(2, 3).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addChild("cube_r3", ModelPartBuilder.create().uv(-1, 3).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(2, 3).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addChild("cube_r4", ModelPartBuilder.create().uv(-1, 3).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void animateModel(CrowEntity crow, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(crow, limbAngle, limbDistance, tickDelta);
        float ageInTicks = crow.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, crow.prevBodyYaw, crow.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, crow.prevHeadYaw, crow.headYaw);
        float relativeHeadYaw = MathHelper.wrapDegrees(headYaw - bodyYaw);

        float headPitch = MathHelper.lerp(tickDelta, crow.prevPitch, crow.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(crow)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!crow.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (crow.isFlying()) {
            this.root.pitch = crow.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = crow.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (crow.isFlying() || crow.isInsideWaterOrBubbleColumn()) {
            this.leftWingOpen.visible = true;
            this.rightWingOpen.visible = true;
            this.leftWing.visible = false;
            this.rightWing.visible = false;
        }
        else {
            this.leftWingOpen.visible = false;
            this.rightWingOpen.visible = false;
            this.leftWing.visible = true;
            this.rightWing.visible = true;
        }
        if (!crow.isFlying() && !crow.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(CrowAnimations.WALKING, limbAngle, limbDistance, 5F, 5F);
        }
        this.updateAnimation(crow.standingState, CrowAnimations.STANDING, ageInTicks);
        this.updateAnimation(crow.floatingState, CrowAnimations.FLOATING, ageInTicks);
        this.updateAnimation(crow.glidingState, CrowAnimations.GLIDING, ageInTicks);
        this.updateAnimation(crow.flappingState, CrowAnimations.FLAPPING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}