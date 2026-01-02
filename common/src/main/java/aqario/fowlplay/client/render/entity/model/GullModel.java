package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.GullAnimations;
import aqario.fowlplay.common.entity.bird.gull.GullEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GullModel extends FlyingBirdModel<GullEntity> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("gull"), "main");

    public GullModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
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
    protected void setAnimations(GullEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(entity.isFlying()) {
            this.animateWalk(GullAnimations.FLAPPING, limbSwing, limbSwingAmount, 2F, 2F);
        }
        else if(!entity.isInWaterOrBubble()) {
            this.animateWalk(GullAnimations.WALKING, limbSwing, limbSwingAmount, 4F, 4F);
        }
        this.animate(entity.standingState, GullAnimations.STANDING, ageInTicks);
        this.animate(entity.swimmingState, GullAnimations.SWIMMING, ageInTicks);
        this.animate(entity.glidingState, GullAnimations.GLIDING, ageInTicks);
    }
}