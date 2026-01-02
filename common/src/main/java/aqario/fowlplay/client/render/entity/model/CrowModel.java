package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.CrowAnimations;
import aqario.fowlplay.common.entity.bird.crow.CrowEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CrowModel extends FlyingBirdModel<CrowEntity> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("crow"), "main");

    public CrowModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, -1.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.25F, 1.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -2.0F, -2.75F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -1.0F));

        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 0.0F, -0.4363F, 0.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(10, 7).addBox(-1.5F, -1.0F, -0.5F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.25F, -2.0F, -0.3927F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(10, 7).mirror().addBox(-0.5F, -1.0F, -0.5F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.25F, -2.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, 0.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.75F, -1.5F, -0.4363F, 0.0F, 0.0F));

        left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(-6, 26).addBox(0.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 0.0F, -1.0F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-5.5F, 0.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -3.75F, -1.5F, -0.4363F, 0.0F, 0.0F));

        right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(-6, 26).mirror().addBox(-6.0F, 0.0F, 0.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 0.0F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(16, 0).addBox(-1.0F, -1.001F, 3.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.75F, 0.5F, -0.2618F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 0).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 2.25F, 0.0F, -0.5236F, 0.0F));

        tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.25F, 0.0F, 0.5236F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(2, 3).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-1, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(2, 3).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-1, 3).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    protected void setAnimations(CrowEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(!entity.isFlying() && !entity.isInWaterOrBubble()) {
            this.animateWalk(CrowAnimations.WALKING, limbSwing, limbSwingAmount, 5F, 5F);
        }
        this.animate(entity.standingState, CrowAnimations.STANDING, ageInTicks);
        this.animate(entity.swimmingState, CrowAnimations.SWIMMING, ageInTicks);
        this.animate(entity.glidingState, CrowAnimations.GLIDING, ageInTicks);
        this.animate(entity.flappingState, CrowAnimations.FLAPPING, ageInTicks);
    }

    @Override
    protected boolean shouldRenderWings(CrowEntity entity) {
        return super.shouldRenderWings(entity) || entity.isInWaterOrBubble();
    }
}