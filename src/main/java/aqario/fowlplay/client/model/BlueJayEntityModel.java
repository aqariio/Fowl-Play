package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.BlueJayEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.BlueJayEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BlueJayEntityModel extends FlyingBirdEntityModel<BlueJayEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "blue_jay"), "main");
    public final ModelPart crest;

    public BlueJayEntityModel(ModelPart root) {
        super(root);
        this.crest = this.head.getChild("crest");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, -1.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.25F, 1.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -1.75F, -2.5F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        head.addChild("crest", ModelPartBuilder.create().uv(8, 6).cuboid(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.2F, 0.4363F, 0.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(16, 3).cuboid(-0.5F, -3.0F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(16, 0).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -2.5F, -1.5F, -0.3927F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(16, 0).mirrored().cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.75F, -2.5F, -1.5F, -0.3927F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(8, 9).cuboid(-0.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(3, 15).cuboid(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(8, 9).mirrored().cuboid(-4.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(3, 15).mirrored().cuboid(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-4.5F, 0.0F, -1.0F));

        body.addChild("tail", ModelPartBuilder.create().uv(11, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(20, 0).cuboid(-1.0F, -1.0F, 3.0F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 0.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(1, 3).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(-1, 2).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(1, 3).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(-1, 2).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void animateModel(BlueJayEntity blueJay, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(blueJay, limbAngle, limbDistance, tickDelta);
        float ageInTicks = blueJay.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, blueJay.prevBodyYaw, blueJay.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, blueJay.prevHeadYaw, blueJay.headYaw);
        float relativeHeadYaw = headYaw - bodyYaw;

        float headPitch = MathHelper.lerp(tickDelta, blueJay.prevPitch, blueJay.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(blueJay)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!blueJay.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (blueJay.isFlying()) {
            this.root.pitch = blueJay.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = blueJay.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (blueJay.isFlying()) {
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
        if (!blueJay.isFlying() && !blueJay.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(BlueJayEntityAnimations.BLUE_JAY_WALK, limbAngle, limbDistance, 6F, 6F);
        }
        this.updateAnimation(blueJay.idleState, BlueJayEntityAnimations.BLUE_JAY_IDLE, ageInTicks);
        this.updateAnimation(blueJay.floatState, BlueJayEntityAnimations.BLUE_JAY_FLOAT, ageInTicks);
        this.updateAnimation(blueJay.glideState, BlueJayEntityAnimations.BLUE_JAY_GLIDE, ageInTicks);
        this.updateAnimation(blueJay.flapState, BlueJayEntityAnimations.BLUE_JAY_FLAP, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}