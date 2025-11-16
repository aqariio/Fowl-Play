package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.GullAnimations;
import aqario.fowlplay.common.entity.GullEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;

public class GullEntityModel extends FlyingBirdEntityModel<GullEntity> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("gull"), "main");

    public GullEntityModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -2.5F, -2.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -1.0F));

        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.5F, -6.5F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.05F, 2.5F, -0.3491F, 0.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -4.25F, 0.0F, -0.3491F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -4.25F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -0.1F, -1.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -5.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(16, 9).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -0.1F, -1.0F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-9.0F, -0.1F, -1.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -5.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(16, 9).mirror().addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.0F, -0.1F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, -1.0F, 1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(23, 0).addBox(-1.0F, -1.002F, 3.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.75F, 3.5F, -0.2618F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(23, 0).addBox(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, 2.5F, 0.0F, -0.3491F, 0.0F));

        tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(23, 0).mirror().addBox(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.0F, 2.5F, 0.0F, 0.3491F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 5).addBox(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void prepareMobModel(GullEntity gull, float limbAngle, float limbDistance, float tickDelta) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        super.prepareMobModel(gull, limbAngle, limbDistance, tickDelta);
        float ageInTicks = gull.tickCount + tickDelta;
        float bodyYaw = Mth.rotLerp(tickDelta, gull.yBodyRotO, gull.yBodyRot);
        float headYaw = Mth.rotLerp(tickDelta, gull.yHeadRotO, gull.yHeadRot);
        float relativeHeadYaw = Mth.wrapDegrees(headYaw - bodyYaw);

        float headPitch = Mth.lerp(tickDelta, gull.xRotO, gull.getXRot());
        if (LivingEntityRenderer.isEntityUpsideDown(gull)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }
        if (!gull.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if (gull.isFlying()) {
            this.root.xRot = gull.getViewXRot(tickDelta) * (float) (Math.PI / 180.0);
            this.root.zRot = gull.getRoll(tickDelta) * (float) (Math.PI / 180.0);
        }
        if (gull.isFlying()) {
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
        if (gull.isFlying()) {
            this.animateWalk(GullAnimations.FLAPPING, limbAngle, limbDistance, 2F, 2F);
        }
        else if (!gull.isInWaterOrBubble()) {
            this.animateWalk(GullAnimations.WALKING, limbAngle, limbDistance, 4F, 4F);
        }
        this.animate(gull.standingState, GullAnimations.STANDING, ageInTicks);
        this.animate(gull.floatingState, GullAnimations.FLOATING, ageInTicks);
        this.animate(gull.glidingState, GullAnimations.GLIDING, ageInTicks);
    }

    private void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -135.0F, 135.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 45.0F);
        this.neck.yRot = headYaw * (float) (Math.PI / 180.0);
        this.neck.xRot = headPitch * (float) (Math.PI / 180.0);
    }
}