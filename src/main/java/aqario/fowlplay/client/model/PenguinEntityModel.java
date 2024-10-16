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

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 7).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(12, 3).cuboid(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 21).cuboid(-3.5F, -12.0F, -3.0F, 7.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(28, 0).mirrored().cuboid(0.0F, -1.0F, -2.0F, 1.0F, 10.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.5F, -11.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(28, 0).cuboid(-1.0F, -1.0F, -2.0F, 1.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -11.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.5F, 3.0F, -0.7854F, 0.0F, 0.0F));

        tail.addChild("cube_r1", ModelPartBuilder.create().uv(39, 0).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(33, 0).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        root.addChild("left_leg", ModelPartBuilder.create().uv(24, 14).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
            .uv(24, 20).mirrored().cuboid(-1.0F, 2.0F, -2.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 8.0F, 0.0F));

        root.addChild("right_leg", ModelPartBuilder.create().uv(24, 14).mirrored().cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
            .uv(24, 20).cuboid(-1.0F, 2.0F, -2.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 8.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PenguinEntity penguin, float limbAngle, float limbDistance, float ageInTicks, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateHeadRotation(penguin.isInsideWaterOrBubbleColumn(), headYaw, headPitch);
        this.animateWalk(PenguinEntityAnimations.PENGUIN_WALK, limbAngle, limbDistance, 2.0F, 2.5F);
        this.animate(penguin.idleState, PenguinEntityAnimations.PENGUIN_IDLE, ageInTicks);
        this.animate(penguin.slideState, PenguinEntityAnimations.PENGUIN_SLIDE, ageInTicks);
        this.animate(penguin.fallingState, PenguinEntityAnimations.PENGUIN_SLIDE, ageInTicks);
        this.animate(penguin.floatState, PenguinEntityAnimations.PENGUIN_SWIM, ageInTicks);
        this.animate(penguin.danceState, PenguinEntityAnimations.PENGUIN_DANCE, ageInTicks);
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