package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.HawkAnimations;
import aqario.fowlplay.common.entity.bird.raptor.HawkEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HawkModel extends FlyingBirdModel<HawkEntity> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("hawk"), "main");

    public HawkModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 1.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -2.0F, -2.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.75F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 3).addBox(-0.5F, -0.25F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F))
            .texOffs(0, 6).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, -1.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 1).addBox(-2.0F, -2.9021F, -6.3154F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(16, 12).addBox(-2.0F, -2.9021F, 0.6846F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.9021F, 2.3154F, -0.6109F, 0.0F, 0.0F));

        torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, -3.0F, -3.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4021F, -2.3154F, 0.7854F, 0.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -1.0F, -0.75F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, -4.0F, -0.25F, -0.6109F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 13).mirror().addBox(-1.0F, -1.0F, -0.75F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.75F, -4.0F, -0.25F, -0.6109F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -4.5F, 0.5F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(19, 10).addBox(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -1.5F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create().texOffs(22, 0).mirror().addBox(-8.0F, 0.0F, -1.5F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -4.5F, 0.5F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(19, 10).mirror().addBox(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 0.0F, -1.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(15, 0).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(49, 0).addBox(-1.5F, -0.5F, 3.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(19, 0).addBox(-1.0F, -0.503F, 1.5F, 2.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 3.5F, -0.1745F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, 0.999F, -1.0F, 2.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.5F, 2.0F, 0.0F, -0.6109F, 0.0F));

        tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(19, 0).addBox(-1.0F, 0.998F, -1.0F, 2.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.5F, 2.0F, 0.0F, -0.2618F, 0.0F));

        tail.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-1.0F, 0.998F, -1.0F, 2.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.5F, 2.0F, 0.0F, 0.2618F, 0.0F));

        tail.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-1.0F, 0.999F, -1.0F, 2.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.5F, 2.0F, 0.0F, 0.6109F, 0.0F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(21, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
            .texOffs(15, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(15, 4).mirror().addBox(-1.0F, 4.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, 1.0F, 2.0F));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(21, 4).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false)
            .texOffs(15, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(15, 4).addBox(-1.0F, 4.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 1.0F, 2.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    protected void setAnimations(HawkEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(entity.isFlying()) {
            this.animateWalk(HawkAnimations.FLAPPING, limbSwing, limbSwingAmount, 1.5F, 1.5F);
        }
        else if(!entity.isInWaterOrBubble()) {
            this.animateWalk(HawkAnimations.WALKING, limbSwing, limbSwingAmount, 2.5F, 4F);
        }
        this.animate(entity.standingState, HawkAnimations.STANDING, ageInTicks);
        this.animate(entity.swimmingState, HawkAnimations.SWIMMING, ageInTicks);
        this.animate(entity.glidingState, HawkAnimations.GLIDING, ageInTicks);
    }

    @Override
    protected boolean shouldRenderWings(HawkEntity entity) {
        return super.shouldRenderWings(entity) || entity.isInWaterOrBubble();
    }
}