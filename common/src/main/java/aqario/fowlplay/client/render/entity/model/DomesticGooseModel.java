package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.DomesticGooseAnimations;
import aqario.fowlplay.common.entity.bird.goose.GooseEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DomesticGooseModel extends GooseModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("domestic_goose"), "main");

    public DomesticGooseModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -7.0F, -1.75F, 3.0F, 9.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -5.0F, -2.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 7).addBox(-1.0F, -3.25F, -1.75F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -6.0F, -1.75F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.001F))
            .texOffs(0, 3).addBox(-1.0F, -0.75F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -6.5F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 2.5F, -0.2618F, 0.0F, 0.0F));

        torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 16).addBox(-0.5F, -3.0F, -0.5F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.25F, 2.75F, 0.3491F, 0.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(1, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -6.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(1, 24).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -6.0F, -1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -7.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(24, 9).addBox(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -0.1F, -1.0F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-8.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -7.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(24, 9).mirror().addBox(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -0.1F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(34, 17).addBox(-1.5F, -1.0F, 1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(40, 17).addBox(-1.0F, -1.002F, 1.75F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.75F, 6.0F, -0.0436F, 0.0F, 0.0F));

        tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(41, 17).addBox(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, 2.0F, 0.0F, -0.5236F, 0.0F));

        tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(41, 17).mirror().addBox(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.0F, 2.0F, 0.0F, 0.5236F, 0.0F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(-1, 0).mirror().addBox(-1.5F, 4.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.25F, 1.0F, 2.5F));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(-1, 0).addBox(-1.5F, 4.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, 1.0F, 2.5F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    protected void setAnimations(GooseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(!entity.isFlying() && !entity.isInWaterOrBubble()) {
            this.animateWalk(DomesticGooseAnimations.WALKING, limbSwing, limbSwingAmount, 3F, 3F);
        }
        this.animate(entity.standingState, DomesticGooseAnimations.STANDING, ageInTicks);
        this.animate(entity.swimmingState, DomesticGooseAnimations.SWIMMING, ageInTicks);
        this.animate(entity.glidingState, DomesticGooseAnimations.GLIDING, ageInTicks);
        this.animate(entity.flappingState, DomesticGooseAnimations.FLAPPING, ageInTicks);
    }
}