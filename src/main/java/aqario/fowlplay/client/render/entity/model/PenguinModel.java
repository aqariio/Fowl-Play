package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.PenguinAnimations;
import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PenguinModel extends BirdModel<PenguinEntity> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("penguin"), "main");

    public PenguinModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 29).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -13.5F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(12, 22).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -2.0F));

        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -14.0F, -3.0F, 7.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(26, 0).mirror().addBox(0.0F, -1.0F, -1.5F, 1.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, -12.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -1.0F, -1.5F, 1.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -12.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.5F, 3.0F, -0.7854F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(27, 13).addBox(0.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        tail.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(21, 13).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(40, 2).mirror().addBox(-1.5F, 2.0F, -2.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 8.0F, 0.0F));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 0).mirror().addBox(-1.0F, -2.0F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(40, 2).addBox(-1.5F, 2.0F, -2.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 8.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    protected void setAnimations(PenguinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(entity.isSwimming()) {
            this.root.yRot = netHeadYaw * (float) (Math.PI / 180.0);
            this.root.xRot = headPitch * (float) (Math.PI / 180.0);
        }
        if(!entity.isSwimming() && !entity.isSliding()) {
            this.updateHeadRotation(netHeadYaw, headPitch);
            this.animateWalk(PenguinAnimations.WALKING, limbSwing, limbSwingAmount, 7F, 7F);
        }
        this.animate(entity.standingState, PenguinAnimations.STANDING, ageInTicks);
        this.animate(entity.slidingState, PenguinAnimations.SLIDING, ageInTicks);
        this.animate(entity.slidingTransitionState, PenguinAnimations.SLIDING_TRANSITION, ageInTicks, 1.0F);
        this.animate(entity.standingTransitionState, PenguinAnimations.STANDING_TRANSITION, ageInTicks, 1.0F);
        this.animate(entity.swimmingState, PenguinAnimations.SWIMMING, ageInTicks);
        this.animate(entity.dancingState, PenguinAnimations.DANCING, ageInTicks);
    }

    @Override
    protected void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -75.0F, 75.0F);
        headPitch = Mth.clamp(headPitch, -45.0F, 45.0F);
        this.neck.yRot = headYaw * (float) (Math.PI / 180.0);
        this.neck.xRot = headPitch * (float) (Math.PI / 180.0);
    }
}