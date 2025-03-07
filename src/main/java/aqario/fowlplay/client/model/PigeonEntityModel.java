package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.PigeonEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PigeonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PigeonEntityModel extends FlyingBirdEntityModel<PigeonEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "pigeon"), "main");

    public PigeonEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, -1.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.25F, 1.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 14).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -1.75F, -2.5F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 9).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 0.0F, -0.6109F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(9, 0).cuboid(-1.5F, -1.0F, -0.5F, 2.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -3.0F, -2.0F, -0.4363F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(9, 0).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 2.0F, 3.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -3.0F, -2.0F, -0.4363F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(8, 12).cuboid(-0.5F, 0.0F, -1.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(2, 19).cuboid(0.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(8, 12).mirrored().cuboid(-5.5F, 0.0F, -1.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(2, 19).mirrored().cuboid(-6.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.5F, 0.0F, -1.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(22, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 1.0F, -0.5236F, 0.0F, 0.0F));

        tail.addChild("cube_r1", ModelPartBuilder.create().uv(7, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-0.25F, -1.0F, 1.0F, 0.0F, -0.2182F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(7, 0).mirrored().cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.25F, -1.0F, 1.0F, 0.0F, 0.2182F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(16, 3).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.5F, 1.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addChild("cube_r3", ModelPartBuilder.create().uv(15, 2).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.5F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(16, 3).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.5F, 1.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addChild("cube_r4", ModelPartBuilder.create().uv(15, 2).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.5F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void animateModel(PigeonEntity pigeon, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(pigeon, limbAngle, limbDistance, tickDelta);
        float ageInTicks = pigeon.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, pigeon.prevBodyYaw, pigeon.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, pigeon.prevHeadYaw, pigeon.headYaw);
        float relativeHeadYaw = headYaw - bodyYaw;

        float headPitch = MathHelper.lerp(tickDelta, pigeon.prevPitch, pigeon.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(pigeon)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!pigeon.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (pigeon.isFlying()) {
            this.root.pitch = pigeon.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = pigeon.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (pigeon.isFlying()) {
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
        if (!pigeon.isFlying() && !pigeon.isInsideWaterOrBubbleColumn() && !pigeon.isInSittingPose()) {
            this.animateMovement(PigeonEntityAnimations.PIGEON_WALK, limbAngle, limbDistance, 5F, 5F);
        }
        this.updateAnimation(pigeon.idleState, PigeonEntityAnimations.PIGEON_IDLE, ageInTicks);
        this.updateAnimation(pigeon.floatState, PigeonEntityAnimations.PIGEON_FLOAT, ageInTicks);
        this.updateAnimation(pigeon.glideState, PigeonEntityAnimations.PIGEON_GLIDE, ageInTicks);
        this.updateAnimation(pigeon.flapState, PigeonEntityAnimations.PIGEON_FLAP, ageInTicks);
        this.updateAnimation(pigeon.sitState, PigeonEntityAnimations.PIGEON_SIT, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}