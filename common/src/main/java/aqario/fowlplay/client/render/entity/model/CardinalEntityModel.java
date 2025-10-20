package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.CardinalAnimations;
import aqario.fowlplay.common.entity.CardinalEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CardinalEntityModel extends FlyingBirdEntityModel<CardinalEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "cardinal"), "main");
    public final ModelPart crest;

    public CardinalEntityModel(ModelPart root) {
        super(root);
        this.crest = this.head.getChild("crest");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, -1.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.25F, 1.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -1.75F, -2.5F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

        head.addChild("crest", ModelPartBuilder.create().uv(6, 6).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, 0.0F, -0.3491F, 0.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(16, 0).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -2.5F, -1.5F, -0.3927F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(16, 0).mirrored().cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.75F, -2.5F, -1.5F, -0.3927F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(8, 9).cuboid(-0.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(3, 15).cuboid(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, 0.0F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(8, 9).mirrored().cuboid(-4.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(3, 15).mirrored().cuboid(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-4.5F, 0.0F, -1.0F));

        body.addChild("tail", ModelPartBuilder.create().uv(11, 0).cuboid(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
            .uv(13, 3).cuboid(-1.0F, -1.0F, 3.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.25F, 0.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(1, 3).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(-1, 2).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(1, 3).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addChild("cube_r2", ModelPartBuilder.create().uv(-1, 2).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void animateModel(CardinalEntity cardinal, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(cardinal, limbAngle, limbDistance, tickDelta);
        float ageInTicks = cardinal.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, cardinal.prevBodyYaw, cardinal.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, cardinal.prevHeadYaw, cardinal.headYaw);
        float relativeHeadYaw = MathHelper.wrapDegrees(headYaw - bodyYaw);

        float headPitch = MathHelper.lerp(tickDelta, cardinal.prevPitch, cardinal.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(cardinal)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!cardinal.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (cardinal.isFlying()) {
            this.root.pitch = cardinal.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = cardinal.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (cardinal.isFlying()) {
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
        if (!cardinal.isFlying() && !cardinal.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(CardinalAnimations.WALKING, limbAngle, limbDistance, 6F, 6F);
        }
        this.updateAnimation(cardinal.standingState, CardinalAnimations.STANDING, ageInTicks);
        this.updateAnimation(cardinal.floatingState, CardinalAnimations.FLOATING, ageInTicks);
        this.updateAnimation(cardinal.glidingState, CardinalAnimations.GLIDING, ageInTicks);
        this.updateAnimation(cardinal.flappingState, CardinalAnimations.FLAPPING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -135.0F, 135.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}