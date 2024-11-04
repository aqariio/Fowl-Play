package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.PenguinEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.PenguinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PenguinEntityModel extends BirdEntityModel<PenguinEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "penguin"), "main");
    public final ModelPart baby;
    public final ModelPart body2;
    public final ModelPart neck2;
    public final ModelPart head2;
    public final ModelPart beak2;
    public final ModelPart torso2;
    public final ModelPart left_wing2;
    public final ModelPart right_wing2;
    public final ModelPart tail2;
    public final ModelPart left_leg2;
    public final ModelPart right_leg2;
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart neck;
    public final ModelPart head;
    public final ModelPart torso;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart tail;

    public PenguinEntityModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.baby = this.root.getChild("baby");
        this.body2 = this.baby.getChild("body2");
        this.neck2 = this.body2.getChild("neck2");
        this.head2 = this.neck2.getChild("head2");
        this.beak2 = this.head2.getChild("beak2");
        this.torso2 = this.body2.getChild("torso2");
        this.left_wing2 = this.body2.getChild("left_wing2");
        this.right_wing2 = this.body2.getChild("right_wing2");
        this.tail2 = this.body2.getChild("tail2");
        this.left_leg2 = this.baby.getChild("left_leg2");
        this.right_leg2 = this.baby.getChild("right_leg2");
        this.body = this.root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
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
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 13.0F, 0.0F));

        ModelPartData baby = root.addChild("baby", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body2 = baby.addChild("body2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

        ModelPartData neck2 = body2.addChild("neck2", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -7.5F, 0.0F));

        ModelPartData head2 = neck2.addChild("head2", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        ModelPartData beak2 = head2.addChild("beak2", ModelPartBuilder.create().uv(12, 15).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.0F));

        ModelPartData torso2 = body2.addChild("torso2", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -8.0F, -2.5F, 6.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData left_wing2 = body2.addChild("left_wing2", ModelPartBuilder.create().uv(22, 0).mirrored().cuboid(0.0F, -1.0F, -1.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, -8.0F, 0.0F));

        ModelPartData right_wing2 = body2.addChild("right_wing2", ModelPartBuilder.create().uv(22, 0).cuboid(-1.0F, -1.0F, -1.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -8.0F, 0.0F));

        ModelPartData tail2 = body2.addChild("tail2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -3.5F, 2.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r1 = tail2.addChild("cube_r1", ModelPartBuilder.create().uv(27, 14).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        ModelPartData cube_r2 = tail2.addChild("cube_r2", ModelPartBuilder.create().uv(21, 14).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        ModelPartData left_leg2 = baby.addChild("left_leg2", ModelPartBuilder.create().uv(30, 0).mirrored().cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.5F, 10.0F, 0.0F));

        ModelPartData right_leg2 = baby.addChild("right_leg2", ModelPartBuilder.create().uv(30, 0).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 10.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 27).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -11.5F, 0.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        ModelPartData beak = head.addChild("beak", ModelPartBuilder.create().uv(12, 20).cuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.0F));

        ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -12.0F, -3.0F, 7.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create().uv(26, 0).mirrored().cuboid(0.0F, -1.0F, -2.0F, 1.0F, 10.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.5F, -11.0F, 0.0F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -11.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.5F, 3.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r3 = tail.addChild("cube_r3", ModelPartBuilder.create().uv(27, 14).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        ModelPartData cube_r4 = tail.addChild("cube_r4", ModelPartBuilder.create().uv(21, 14).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(36, 0).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
            .uv(40, 2).mirrored().cuboid(-1.0F, 2.0F, -2.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 8.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(36, 0).mirrored().cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
            .uv(40, 2).cuboid(-1.0F, 2.0F, -2.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 8.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PenguinEntity penguin, float limbAngle, float limbDistance, float ageInTicks, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        if (penguin.isBaby()) {
            this.baby.visible = true;
            this.body.visible = false;
            this.leftLeg.visible = false;
            this.rightLeg.visible = false;
        }
        else {
            this.baby.visible = false;
            this.body.visible = true;
            this.leftLeg.visible = true;
            this.rightLeg.visible = true;
        }
        if (penguin.isBaby()) {
            this.updateBabyHeadRotation(penguin.isInsideWaterOrBubbleColumn(), headYaw, headPitch);
        }
        else {
            this.updateHeadRotation(penguin.isInsideWaterOrBubbleColumn(), headYaw, headPitch);
        }
        this.animateWalk(PenguinEntityAnimations.PENGUIN_WALK, limbAngle, limbDistance, 2.0F, 2.5F);
        this.animate(penguin.idleState, PenguinEntityAnimations.PENGUIN_IDLE, ageInTicks);
        this.animate(penguin.slideState, PenguinEntityAnimations.PENGUIN_SLIDE, ageInTicks);
        this.animate(penguin.fallingState, PenguinEntityAnimations.PENGUIN_SLIDE, ageInTicks);
        this.animate(penguin.swimState, PenguinEntityAnimations.PENGUIN_SWIM, ageInTicks);
        this.animate(penguin.danceState, PenguinEntityAnimations.PENGUIN_DANCE, ageInTicks);
    }

    private void updateBabyHeadRotation(boolean swimming, float headYaw, float headPitch) {
        if (swimming) {
            this.baby.yaw = headYaw * (float) (Math.PI / 180.0);
            this.baby.pitch = headPitch * (float) (Math.PI / 180.0);
        }
        else {
            headYaw = MathHelper.clamp(headYaw, -75.0F, 75.0F);
            headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);
            this.neck2.yaw = headYaw * (float) (Math.PI / 180.0);
            this.neck2.pitch = headPitch * (float) (Math.PI / 180.0);
        }
    }

    private void updateHeadRotation(boolean swimming, float headYaw, float headPitch) {
        if (swimming) {
            this.root.yaw = headYaw * (float) (Math.PI / 180.0);
            this.root.pitch = headPitch * (float) (Math.PI / 180.0);
        }
        else {
            headYaw = MathHelper.clamp(headYaw, -75.0F, 75.0F);
            headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);
            this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
            this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
        }
    }
}