package aqario.fowlplay.client.model;

import aqario.fowlplay.client.render.animation.DuckEntityAnimations;
import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.DuckEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DuckEntityModel extends BirdEntityModel<DuckEntity> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(Identifier.of(FowlPlay.ID, "duck"), "main");
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

    public DuckEntityModel(ModelPart root) {
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
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.5F, 0.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 19).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 3.0F, new Dilation(-0.001F)), ModelTransform.pivot(0.0F, -2.5F, -2.5F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        head.addChild("beak", ModelPartBuilder.create().uv(17, 5).cuboid(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new Dilation(-0.005F)), ModelTransform.pivot(0.0F, -1.0F, -1.0F));

        body.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.5F, -6.5F, 4.0F, 4.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.05F, 2.5F, -0.2618F, 0.0F, 0.0F));

        body.addChild("left_wing", ModelPartBuilder.create().uv(3, 18).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        body.addChild("right_wing", ModelPartBuilder.create().uv(3, 18).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -4.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        ModelPartData left_wing_open = body.addChild("left_wing_open", ModelPartBuilder.create().uv(25, 0).cuboid(-1.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -5.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        left_wing_open.addChild("left_wing_outer", ModelPartBuilder.create().uv(18, 9).cuboid(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, -0.1F, -1.0F));

        ModelPartData right_wing_open = body.addChild("right_wing_open", ModelPartBuilder.create().uv(25, 0).mirrored().cuboid(-8.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, -5.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        right_wing_open.addChild("right_wing_outer", ModelPartBuilder.create().uv(18, 9).mirrored().cuboid(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-8.0F, -0.1F, -1.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(17, 0).cuboid(-1.5F, -1.0F, 1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
            .uv(23, 0).cuboid(-1.0F, -1.002F, 1.75F, 2.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.75F, 4.5F, -0.2618F, 0.0F, 0.0F));

        tail.addChild("cube_r1", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -1.0F, 2.0F, 0.0F, -0.5236F, 0.0F));

        tail.addChild("cube_r2", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.5F, -1.0F, 2.0F, 0.0F, 0.5236F, 0.0F));

        ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(0, 5).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addChild("cube_r3", ModelPartBuilder.create().uv(0, 6).mirrored().cuboid(0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 5).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addChild("cube_r4", ModelPartBuilder.create().uv(0, 6).cuboid(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(DuckEntity duck, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
    }

    @Override
    public void animateModel(DuckEntity duck, float limbAngle, float limbDistance, float tickDelta) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        super.animateModel(duck, limbAngle, limbDistance, tickDelta);
        float ageInTicks = duck.age + tickDelta;
        float bodyYaw = MathHelper.lerpAngleDegrees(tickDelta, duck.prevBodyYaw, duck.bodyYaw);
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, duck.prevHeadYaw, duck.headYaw);
        float relativeHeadYaw = headYaw - bodyYaw;

        float headPitch = MathHelper.lerp(tickDelta, duck.prevPitch, duck.getPitch());
        if (LivingEntityRenderer.shouldFlipUpsideDown(duck)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!duck.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (duck.isFlying()) {
            this.root.pitch = duck.getPitch(tickDelta) * (float) (Math.PI / 180.0);
            this.root.roll = duck.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (duck.isFlying()) {
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
        if (!duck.isFlying() && !duck.isInsideWaterOrBubbleColumn()) {
            this.animateMovement(DuckEntityAnimations.DUCK_WALK, limbAngle, limbDistance, 4F, 4F);
        }
        this.updateAnimation(duck.idleState, DuckEntityAnimations.DUCK_IDLE, ageInTicks);
        this.updateAnimation(duck.floatState, DuckEntityAnimations.DUCK_FLOAT, ageInTicks);
        this.updateAnimation(duck.glideState, DuckEntityAnimations.DUCK_GLIDE, ageInTicks);
        this.updateAnimation(duck.flapState, DuckEntityAnimations.DUCK_FLAP, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yaw = headYaw * (float) (Math.PI / 180.0);
        this.neck.pitch = headPitch * (float) (Math.PI / 180.0);
    }
}