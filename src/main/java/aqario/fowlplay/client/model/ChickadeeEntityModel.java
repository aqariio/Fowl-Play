package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.ChickadeeEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.ChickadeeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ChickadeeEntityModel extends BirdEntityModel<ChickadeeEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "chickadee"), "main");
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart neck;
    public final ModelPart head;
    public final ModelPart torso;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart leftWingOpen;
    public final ModelPart rightWingOpen;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart tail;

    public ChickadeeEntityModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.torso = this.body.getChild("torso");
        this.leftWing = this.body.getChild("left_wing");
        this.rightWing = this.body.getChild("right_wing");
        this.leftWingOpen = this.body.getChild("left_wing_open");
        this.rightWingOpen = this.body.getChild("right_wing_open");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightLeg = this.root.getChild("right_leg");
        this.tail = this.body.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.75F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -1.0F, -1.75F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 7).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -3.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(14, 0).cuboid(-1.5F, -1.0F, -0.5F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -2.25F, -1.0F, -0.3927F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(14, 0).mirrored().cuboid(-0.5F, -1.0F, -0.5F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -2.25F, -1.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(8, 7).cuboid(-0.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.75F, -0.25F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(3, 13).cuboid(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(8, 7).mirrored().cuboid(-4.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -2.75F, -0.25F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(3, 13).mirrored().cuboid(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-4.5F, 0.0F, -1.0F));

        body.addChild("tail", ModelPartBuilder.create().uv(10, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(20, 0).cuboid(-1.0F, -1.0F, 3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 0.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(9, 8).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(7, 7).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(9, 8).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(7, 7).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(ChickadeeEntity chickadee, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
    }

    @Override
    public void animateModel(ChickadeeEntity chickadee, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(chickadee, limbAngle, limbDistance, tickDelta);
        float ageInTicks = chickadee.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, chickadee.prevBodyYaw, chickadee.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, chickadee.prevHeadYaw, chickadee.headYaw);
        float relativeHeadYaw = headYaw - bodyYaw;

        float headPitch = MathHelper.lerp(tickDelta, chickadee.prevPitch, chickadee.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(chickadee)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!chickadee.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (chickadee.isFlying()) {
            this.root.pitch = chickadee.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = chickadee.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (chickadee.isFlying()) {
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
        if (!chickadee.isFlying() && !chickadee.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(ChickadeeEntityAnimations.CHICKADEE_WALK, limbAngle, limbDistance, 6F, 6F);
        }
        this.updateAnimation(chickadee.idleState, ChickadeeEntityAnimations.CHICKADEE_IDLE, ageInTicks);
        this.updateAnimation(chickadee.floatState, ChickadeeEntityAnimations.CHICKADEE_FLOAT, ageInTicks);
        this.updateAnimation(chickadee.glideState, ChickadeeEntityAnimations.CHICKADEE_GLIDE, ageInTicks);
        this.updateAnimation(chickadee.flapState, ChickadeeEntityAnimations.CHICKADEE_FLAP, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}