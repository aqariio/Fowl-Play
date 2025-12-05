package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.ChickenAnimations;
import aqario.fowlplay.common.entity.GooseEntity;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BabyGooseModel extends GooseModel {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("baby_goose"), "main");

    public BabyGooseModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, -1.25F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(16, 5).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -1.0F));

        body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -3.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 1.5F, -0.1745F, 0.0F, 0.0F));

        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(8, 8).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.25F, -0.5F, -0.1745F, 0.0F, 0.0F));

        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(8, 8).mirror().addBox(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -1.25F, -0.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -4.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create(), PartPose.offset(4.5F, 0.0F, -1.0F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -4.25F, -1.0F, -0.3491F, 0.0F, 0.0F));

        right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create(), PartPose.offset(-4.5F, 0.0F, -1.0F));

        body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 3.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 3.0F, 1.5F, -0.1745F, 0.0F, 0.0F));

        right_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    protected void setAnimations(GooseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
        if(!entity.isFlying() && !entity.isInWaterOrBubble()) {
            this.animateWalk(ChickenAnimations.WALKING, limbSwing, limbSwingAmount, 3F, 3F);
        }
        this.animate(entity.standingState, ChickenAnimations.STANDING, ageInTicks);
        this.animate(entity.swimmingState, ChickenAnimations.SWIMMING, ageInTicks);
    }
}