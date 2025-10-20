package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.DomesticGooseAnimations;
import aqario.fowlplay.client.render.entity.animation.GooseAnimations;
import aqario.fowlplay.common.entity.GooseEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class GooseEntityModel extends FlyingBirdEntityModel<GooseEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "goose"), "main");

    public GooseEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.5F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 23).cuboid(-1.5F, -7.0F, -1.75F, 3.0F, 9.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -4.5F, -3.5F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, -1.75F));

        head.addChild("beak", ModelPartBuilder.create().uv(22, 0).cuboid(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 3.0F, new Dilation(-0.001F))
            .uv(0, 3).cuboid(-1.0F, -0.75F, -2.5F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -1.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -6.0F, -6.5F, 6.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, 2.5F, -0.0873F, 0.0F, 0.0F));

        torso.addChild("cube_r1", ModelPartBuilder.create().uv(16, 16).cuboid(-0.5F, -3.0F, -0.5F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -3.0F, 3.0F, 0.0873F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(1, 24).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -5.5F, -0.5F, -0.0873F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(1, 24).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 11.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, -5.5F, -0.5F, -0.0873F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -0.1F, -1.0F, 10.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -6.5F, -2.0F, -0.0873F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(24, 10).cuboid(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, -0.1F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(23, 0).mirrored().cuboid(-9.0F, -0.1F, -1.0F, 10.0F, 1.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, -6.5F, -2.0F, -0.0873F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(24, 10).mirrored().cuboid(-11.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-8.0F, -0.1F, -1.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(34, 18).cuboid(-1.5F, -1.0F, 1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
            .uv(40, 18).cuboid(-1.0F, -1.002F, 1.75F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.25F, 6.0F, -0.0436F, 0.0F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(41, 18).cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.0F, 2.0F, 0.0F, -0.5236F, 0.0F));

        tail.addChild("cube_r3", ModelPartBuilder.create().uv(41, 18).mirrored().cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.0F, 2.0F, 0.0F, 0.5236F, 0.0F));

        root.addChild("left_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
            .uv(-1, 0).mirrored().cuboid(-1.5F, 4.0F, -3.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.25F, 1.0F, 2.5F));

        root.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
            .uv(-1, 0).cuboid(-1.5F, 4.0F, -3.0F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.25F, 1.0F, 2.5F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void animateModel(GooseEntity goose, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(goose, limbAngle, limbDistance, tickDelta);
        float ageInTicks = goose.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, goose.prevBodyYaw, goose.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, goose.prevHeadYaw, goose.headYaw);
        float relativeHeadYaw = MathHelper.wrapDegrees(headYaw - bodyYaw);

        float headPitch = MathHelper.lerp(tickDelta, goose.prevPitch, goose.getPitch());
        if(LivingEntityRenderer.shouldFlipUpsideDown(goose)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if(!goose.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if(goose.isFlying()) {
            this.root.pitch = goose.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = goose.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if(goose.isFlying()) {
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
        boolean domestic = goose.getVariant().domestic();
        final Animation walkingAnimation = domestic ? DomesticGooseAnimations.WALKING : GooseAnimations.WALKING;
        final Animation standingAnimation = domestic ? DomesticGooseAnimations.STANDING : GooseAnimations.STANDING;
        final Animation swimmingAnimation = domestic ? DomesticGooseAnimations.SWIMMING : GooseAnimations.SWIMMING;
        final Animation glidingAnimation = domestic ? DomesticGooseAnimations.GLIDING : GooseAnimations.GLIDING;
        final Animation flappingAnimation = domestic ? DomesticGooseAnimations.FLAPPING : GooseAnimations.FLAPPING;

        if(!goose.isFlying() && !goose.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(walkingAnimation, limbAngle, limbDistance, 3F, 3F);
        }
        this.updateAnimation(goose.standingState, standingAnimation, ageInTicks);
        this.updateAnimation(goose.floatingState, swimmingAnimation, ageInTicks);
        this.updateAnimation(goose.glidingState, glidingAnimation, ageInTicks);
        this.updateAnimation(goose.flappingState, flappingAnimation, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -100.0F, 100.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}