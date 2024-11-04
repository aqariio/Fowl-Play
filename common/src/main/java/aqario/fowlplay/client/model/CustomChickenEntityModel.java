package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.ChickenEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CustomChickenEntityModel extends SinglePartEntityModel<ChickenEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "chicken"), "main");
    public final AnimationState idleState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState floatState = new AnimationState();
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart torso;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart tail;

    public CustomChickenEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.torso = this.body.getChild("torso");
        this.leftWing = this.body.getChild("left_wing");
        this.rightWing = this.body.getChild("right_wing");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightLeg = this.root.getChild("right_leg");
        this.tail = this.body.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.5F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 21).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
            .uv(0, -3).cuboid(0.0F, -7.0F, -2.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F))
            .uv(0, 1).cuboid(0.5F, -4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
            .uv(0, 1).cuboid(-0.5F, -4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.5F));

        head.addChild("beak", ModelPartBuilder.create().uv(15, 4).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.5F, -6.5F, 4.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.95F, 2.5F, -0.3491F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(0, 11).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, -3.55F, -1.5F, -0.3491F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(0, 11).mirrored().cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.25F, -3.55F, -1.5F, -0.3491F, 0.0F, 0.0F));

        body.addChild("tail", ModelPartBuilder.create().uv(15, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
            .uv(8, 16).cuboid(0.0F, -6.0F, 1.0F, 0.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 2.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, -4.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(0, 5).mirrored().cuboid(0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, -4.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(0, 5).cuboid(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(ChickenEntity chicken, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(netHeadYaw, headPitch);

        // animation state processing
        if (chicken.isOnGround() && !chicken.isInsideWaterOrBubbleColumn()) {
            this.idleState.start(chicken.age);
        }
        else {
            this.idleState.stop();
        }

        if (!chicken.isOnGround() && !chicken.isInsideWaterOrBubbleColumn()) {
            this.flapState.start(chicken.age);
        }
        else {
            this.flapState.stop();
        }

        if (chicken.isInsideWaterOrBubbleColumn()) {
            this.floatState.start(chicken.age);
        }
        else {
            this.floatState.stop();
        }
        //

        if (chicken.isOnGround() && !chicken.isInsideWaterOrBubbleColumn()) {
            this.animateWalk(ChickenEntityAnimations.CHICKEN_WALK, limbSwing, limbSwingAmount, 3F, 3F);
        }
        this.animate(this.idleState, ChickenEntityAnimations.CHICKEN_IDLE, ageInTicks);
        this.animate(this.flapState, ChickenEntityAnimations.CHICKEN_FLAP, ageInTicks);
        this.animate(this.floatState, ChickenEntityAnimations.CHICKEN_FLOAT, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}