package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.PenguinAnimations;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BabyPenguinEntityModel extends PenguinEntityModel {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "baby_penguin"), "main");

    public BabyPenguinEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 13.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -7.5F, 0.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 15).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(12, 15).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -8.0F, -2.5F, 6.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(22, 0).mirrored().cuboid(0.0F, -1.0F, -1.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, -8.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(22, 0).cuboid(-1.0F, -1.0F, -1.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -8.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.of(0.0F, -3.5F, 2.0F, -0.7854F, 0.0F, 0.0F));

        tail.addChild("cube_r1", ModelPartBuilder.create().uv(27, 14).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(21, 14).cuboid(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        root.addChild("left_leg", ModelPartBuilder.create().uv(30, 0).mirrored().cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.5F, 10.0F, 0.0F));

        root.addChild("right_leg", ModelPartBuilder.create().uv(30, 0).cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 10.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(PenguinEntity penguin, float limbAngle, float limbDistance, float ageInTicks, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        if (penguin.isSwimming()) {
            this.root.yaw = headYaw * (float) (Math.PI / 180.0);
            this.root.pitch = headPitch * (float) (Math.PI / 180.0);
        }
        if (!penguin.isSwimming() && !penguin.isSliding()) {
            this.updateHeadRotation(headYaw, headPitch);
            this.animateMovement(PenguinAnimations.WALKING, limbAngle, limbDistance, 7F, 7F);
        }
        this.updateAnimation(penguin.standingState, PenguinAnimations.STANDING, ageInTicks);
        this.updateAnimation(penguin.slidingState, PenguinAnimations.SLIDING, ageInTicks);
        this.updateAnimation(penguin.slidingTransitionState, PenguinAnimations.SLIDING_TRANSITION, ageInTicks, 1.0F);
        this.updateAnimation(penguin.standingTransitionState, PenguinAnimations.STANDING_TRANSITION, ageInTicks, 1.0F);
        this.updateAnimation(penguin.swimmingState, PenguinAnimations.SWIMMING, ageInTicks);
        this.updateAnimation(penguin.dancingState, PenguinAnimations.DANCING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -75.0F, 75.0F);
        headPitch = MathHelper.clamp(headPitch, -45.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}