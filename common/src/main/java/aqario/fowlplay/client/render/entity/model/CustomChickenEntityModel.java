package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.ChickenAnimations;
import aqario.fowlplay.common.util.ChickenAnimationStates;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.math.MathHelper;

public class CustomChickenEntityModel extends SinglePartEntityModel<ChickenEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(FowlPlay.id("chicken"), "main");
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart torso;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart leftWingOpen;
    public final ModelPart rightWingOpen;
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
        this.leftWingOpen = this.body.getChild("left_wing_open");
        this.rightWingOpen = this.body.getChild("right_wing_open");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightLeg = this.root.getChild("right_leg");
        this.tail = this.body.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.5F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 21).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
            .uv(0, -3).cuboid(0.0F, -7.0F, -2.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F))
            .uv(0, 1).cuboid(0.5F, -4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
            .uv(0, 1).cuboid(-0.5F, -4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.5F));

        head.addChild("beak", ModelPartBuilder.create().uv(15, 4).cuboid(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.5F, -6.5F, 4.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.95F, 2.5F, -0.3491F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(0, 11).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, -3.55F, -1.5F, -0.3491F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(0, 11).mirrored().cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.25F, -3.55F, -1.5F, -0.3491F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(8, 19).cuboid(-0.5F, 0.0F, -1.0F, 5.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -4.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(2, 26).cuboid(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(8, 19).mirrored().cuboid(-4.5F, 0.0F, -1.0F, 5.0F, 1.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -4.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(2, 26).mirrored().cuboid(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-4.5F, 0.0F, -1.0F));

        body.addChild("tail", ModelPartBuilder.create().uv(15, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
            .uv(22, -1).cuboid(0.0F, -6.0F, 1.0F, 0.0F, 8.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 2.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, 1.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(0, 5).mirrored().cuboid(0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, 1.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(0, 5).cuboid(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(ChickenEntity chicken, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(netHeadYaw, headPitch);
        if (chicken.isOnGround() || chicken.isInsideWaterOrBubbleColumn()) {
            this.leftWingOpen.visible = false;
            this.rightWingOpen.visible = false;
            this.leftWing.visible = true;
            this.rightWing.visible = true;
        }
        else {
            this.leftWingOpen.visible = true;
            this.rightWingOpen.visible = true;
            this.leftWing.visible = false;
            this.rightWing.visible = false;
        }

        if (chicken.isOnGround() && !chicken.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(ChickenAnimations.WALKING, limbSwing, limbSwingAmount, 3F, 3F);
        }
        this.updateAnimation(((ChickenAnimationStates) chicken).fowlplay$getStandingState(), ChickenAnimations.STANDING, ageInTicks);
        this.updateAnimation(((ChickenAnimationStates) chicken).fowlplay$getFlappingState(), ChickenAnimations.FLAPPING, ageInTicks);
        this.updateAnimation(((ChickenAnimationStates) chicken).fowlplay$getFloatingState(), ChickenAnimations.FLOATING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.head.yaw = headYaw * (float) (Math.PI / 180.0);
        this.head.pitch = headPitch * (float) (Math.PI / 180.0);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}