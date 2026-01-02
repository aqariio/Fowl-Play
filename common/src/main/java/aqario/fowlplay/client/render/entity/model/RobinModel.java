package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.RobinAnimations;
import aqario.fowlplay.common.entity.bird.robin.RobinEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class RobinModel extends FlyingBirdModel<RobinEntity> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("robin"), "main");

    public RobinModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, -1.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.25F, 1.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -1.75F, -2.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -1.0F));

        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(16, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -2.5F, -1.5F, -0.3927F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 0).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -2.5F, -1.5F, -0.3927F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create().texOffs(8, 9).addBox(-0.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(3, 15).addBox(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 0.0F, -1.0F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create().texOffs(8, 9).mirror().addBox(-4.5F, 0.0F, -1.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -3.25F, -1.0F, -0.6109F, 0.0F, 0.0F));

        right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(3, 15).mirror().addBox(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 0.0F, -1.0F));

        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(11, 0).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(13, 3).addBox(-1.0F, -1.0F, 3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(1, 3).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-1, 2).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(1, 3).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        right_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-1, 2).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    protected void setAnimations(RobinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(!entity.isFlying() && !entity.isInWaterOrBubble()) {
            this.animateWalk(RobinAnimations.WALKING, limbSwing, limbSwingAmount, 6F, 6F);
        }
        this.animate(entity.standingState, RobinAnimations.STANDING, ageInTicks);
        this.animate(entity.swimmingState, RobinAnimations.SWIMMING, ageInTicks);
        this.animate(entity.glidingState, RobinAnimations.GLIDING, ageInTicks);
        this.animate(entity.flappingState, RobinAnimations.FLAPPING, ageInTicks);
    }
}