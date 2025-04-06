package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.RavenAnimations;
import aqario.fowlplay.common.entity.RavenEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RavenEntityModel extends FlyingBirdEntityModel<RavenEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "raven"), "main");

    public RavenEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.5F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -2.5F, -2.5F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        head.addChild("throat", ModelPartBuilder.create().uv(22, 2).cuboid(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, -1.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
            .uv(0, 4).cuboid(-0.5F, 0.0F, -2.5F, 1.0F, 1.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -1.5F, -1.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -6.5F, 4.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 2.5F, -0.3491F, 0.0F, 0.0F));

        torso.addChild("cube_r1", ModelPartBuilder.create().uv(16, 8).cuboid(0.0F, -2.0F, -3.0F, 0.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.25F, -2.5F, 0.7854F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(0, 14).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -4.5F, -0.5F, -0.3491F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(0, 14).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 12.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -4.5F, -0.5F, -0.3491F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -5.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(16, 9).cuboid(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, -0.1F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-8.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -5.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(16, 9).mirrored().cuboid(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-8.0F, -0.1F, -1.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(16, 0).cuboid(-1.5F, -1.0F, 1.0F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F))
            .uv(21, 0).cuboid(-1.0F, -1.003F, 3.5F, 2.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.75F, 3.5F, -0.2618F, 0.0F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(22, 0).cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.0F, 2.0F, 0.0F, -0.6109F, 0.0F));

        tail.addChild("cube_r3", ModelPartBuilder.create().uv(21, 0).cuboid(-1.0F, -0.002F, 0.0F, 2.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.0F, 2.5F, 0.0F, -0.2618F, 0.0F));

        tail.addChild("cube_r4", ModelPartBuilder.create().uv(21, 0).mirrored().cuboid(-1.0F, -0.002F, 0.0F, 2.0F, 0.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.0F, 2.5F, 0.0F, 0.2618F, 0.0F));

        tail.addChild("cube_r5", ModelPartBuilder.create().uv(22, 0).mirrored().cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.0F, 2.0F, 0.0F, 0.6109F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(16, 4).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addChild("cube_r6", ModelPartBuilder.create().uv(16, 4).mirrored().cuboid(0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(16, 4).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addChild("cube_r7", ModelPartBuilder.create().uv(16, 4).cuboid(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void animateModel(RavenEntity raven, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(raven, limbAngle, limbDistance, tickDelta);
        float ageInTicks = raven.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, raven.prevBodyYaw, raven.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, raven.prevHeadYaw, raven.headYaw);
        float relativeHeadYaw = headYaw - bodyYaw;

        float headPitch = MathHelper.lerp(tickDelta, raven.prevPitch, raven.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(raven)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!raven.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (raven.isFlying()) {
            this.root.pitch = raven.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = raven.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (raven.isFlying() || raven.isInsideWaterOrBubbleColumn()) {
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
        if (!raven.isFlying() && !raven.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(RavenAnimations.WALKING, limbAngle, limbDistance, 2.5F, 4F);
        }
        this.updateAnimation(raven.standingState, RavenAnimations.STANDING, ageInTicks);
        this.updateAnimation(raven.floatingState, RavenAnimations.FLOATING, ageInTicks);
        this.updateAnimation(raven.glidingState, RavenAnimations.GLIDING, ageInTicks);
        this.updateAnimation(raven.flappingState, RavenAnimations.FLAPPING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}