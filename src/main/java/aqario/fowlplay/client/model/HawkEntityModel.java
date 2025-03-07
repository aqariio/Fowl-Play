package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.HawkEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.HawkEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class HawkEntityModel extends FlyingBirdEntityModel<HawkEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "hawk"), "main");

    public HawkEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.5F, 1.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -2.0F, -2.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.75F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(0, 3).cuboid(-0.5F, -0.25F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(-0.001F))
            .uv(0, 6).cuboid(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.25F, -1.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 1).cuboid(-2.0F, -2.9021F, -6.3154F, 4.0F, 4.0F, 7.0F, new Dilation(0.0F))
            .uv(16, 12).cuboid(-2.0F, -2.9021F, 0.6846F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.9021F, 2.3154F, -0.6109F, 0.0F, 0.0F));

        torso.addChild("cube_r1", ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, -3.0F, -3.0F, 0.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.4021F, -2.3154F, 0.7854F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, -1.0F, -0.75F, 2.0F, 4.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -4.0F, -0.25F, -0.6109F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(0, 13).mirrored().cuboid(-1.0F, -1.0F, -0.75F, 2.0F, 4.0F, 12.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.75F, -4.0F, -0.25F, -0.6109F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, 0.0F, -1.5F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -4.5F, 0.5F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(20, 9).cuboid(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, -1.5F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(23, 0).mirrored().cuboid(-8.0F, 0.0F, -1.5F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -4.5F, 0.5F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(20, 9).mirrored().cuboid(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-8.0F, 0.0F, -1.5F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(15, 0).cuboid(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F))
            .uv(49, 0).cuboid(-1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(19, 0).cuboid(-1.0F, -0.503F, 1.5F, 2.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 3.5F, -0.1745F, 0.0F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(20, 0).cuboid(-1.0F, 0.999F, -1.0F, 2.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.5F, 2.0F, 0.0F, -0.6109F, 0.0F));

        tail.addChild("cube_r3", ModelPartBuilder.create().uv(19, 0).cuboid(-1.0F, 0.998F, -1.0F, 2.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.5F, 2.0F, 0.0F, -0.2618F, 0.0F));

        tail.addChild("cube_r4", ModelPartBuilder.create().uv(19, 0).mirrored().cuboid(-1.0F, 0.998F, -1.0F, 2.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.5F, 2.0F, 0.0F, 0.2618F, 0.0F));

        tail.addChild("cube_r5", ModelPartBuilder.create().uv(20, 0).mirrored().cuboid(-1.0F, 0.999F, -1.0F, 2.0F, 0.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.5F, 2.0F, 0.0F, 0.6109F, 0.0F));

        root.addChild("left_leg", ModelPartBuilder.create().uv(21, 4).cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 2.0F, new Dilation(0.001F))
            .uv(15, 4).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F))
            .uv(15, 4).mirrored().cuboid(-1.0F, 4.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.5F, 1.0F, 2.0F));

        root.addChild("right_leg", ModelPartBuilder.create().uv(21, 4).mirrored().cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 2.0F, new Dilation(0.001F)).mirrored(false)
            .uv(15, 4).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F))
            .uv(15, 4).cuboid(-1.0F, 4.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 1.0F, 2.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void animateModel(HawkEntity hawk, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(hawk, limbAngle, limbDistance, tickDelta);
        float ageInTicks = hawk.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, hawk.prevBodyYaw, hawk.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, hawk.prevHeadYaw, hawk.headYaw);
        float relativeHeadYaw = headYaw - bodyYaw;

        float headPitch = MathHelper.lerp(tickDelta, hawk.prevPitch, hawk.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(hawk)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!hawk.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (hawk.isFlying()) {
            this.root.pitch = hawk.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = hawk.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (hawk.isFlying() || hawk.isInsideWaterOrBubbleColumn()) {
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
        if (!hawk.isFlying() && !hawk.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(HawkEntityAnimations.HAWK_WALK, limbAngle, limbDistance, 2.5F, 4F);
        }
        this.updateAnimation(hawk.idleState, HawkEntityAnimations.HAWK_IDLE, ageInTicks);
        this.updateAnimation(hawk.floatState, HawkEntityAnimations.HAWK_FLOAT, ageInTicks);
        this.updateAnimation(hawk.glideState, HawkEntityAnimations.HAWK_GLIDE, ageInTicks);
        this.updateAnimation(hawk.flapState, HawkEntityAnimations.HAWK_FLAP, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}