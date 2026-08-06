package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.client.render.entity.animation.ParrotAnimations;
import aqario.fowlplay.common.util.ParrotAnimationHolder;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;

public class CustomParrotModel extends HierarchicalModel<Parrot> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(FowlPlay.id("parrot"), "main");
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

    public CustomParrotModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.torso = this.body.getChild("torso");
        this.leftWing = this.body.getChild("left_wing");
        this.rightWing = this.body.getChild("right_wing");
        this.leftLeg = this.root.getChild("left_leg");
        this.rightLeg = this.root.getChild("right_leg");
        this.tail = this.body.getChild("tail");
        this.leftWingOpen = this.body.getChild("left_wing_open");
        this.rightWingOpen = this.body.getChild("right_wing_open");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, -1.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 2.5F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 36).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(3, 37).addBox(-0.5F, -1.0F, -1.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(9, 37).addBox(-0.5F, -0.5F, -1.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(4, 41).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, -1.5F, -1.5F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -6.5F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -4.0F, -3.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -4.0F, -3.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_wing_open = body.addOrReplaceChild("left_wing_open", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -4.5F, -3.5F, -0.3491F, 0.0F, 0.0F));

        PartDefinition left_wing_outer = left_wing_open.addOrReplaceChild("left_wing_outer", CubeListBuilder.create().texOffs(16, 9).addBox(0.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -0.1F, -1.0F));

        PartDefinition right_wing_open = body.addOrReplaceChild("right_wing_open", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-8.0F, -0.1F, -1.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -4.5F, -3.5F, -0.3491F, 0.0F, 0.0F));

        PartDefinition right_wing_outer = right_wing_open.addOrReplaceChild("right_wing_outer", CubeListBuilder.create().texOffs(16, 9).mirror().addBox(-10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -0.1F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 0).addBox(-1.5F, -1.0F, 1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(6, -15).addBox(-1.0F, -1.003F, 3.5F, 2.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, 1.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r2 = tail.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, -8).addBox(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, 2.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition cube_r3 = tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, -13).addBox(-1.0F, -0.002F, 0.0F, 2.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, 2.5F, 0.0F, -0.0436F, 0.0F));

        PartDefinition cube_r4 = tail.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(8, -13).mirror().addBox(-1.0F, -0.002F, 0.0F, 2.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.0F, 2.5F, 0.0F, 0.0436F, 0.0F));

        PartDefinition cube_r5 = tail.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(14, -8).mirror().addBox(-1.0F, -0.001F, 0.0F, 2.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.0F, 2.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r6 = left_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(15, 5).mirror().addBox(0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 1.0F, 2.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r7 = right_leg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(15, 5).addBox(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 4.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Parrot parrot, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.updateHeadRotation(netHeadYaw, headPitch);
        if(parrot.onGround() || parrot.isInWaterOrBubble()) {
            this.leftWingOpen.visible = false;
            this.rightWingOpen.visible = false;
            this.leftWing.visible = true;
            this.rightWing.visible = true;
        }
        else {
            this.leftWingOpen.visible = true;
            this.rightWingOpen.visible = true;
            this.leftWing.visible = false;
            this.rightWing.visible = false;
        }

        if(parrot.onGround() && !parrot.isInWaterOrBubble()) {
            this.animateWalk(ParrotAnimations.WALKING, limbSwing, limbSwingAmount, 3F, 3F);
        }
        this.animate(((ParrotAnimationHolder) parrot).fowlplay$getStandingState(), ParrotAnimations.PERCHING, ageInTicks);
        this.animate(((ParrotAnimationHolder) parrot).fowlplay$getFlappingState(), ParrotAnimations.FLAPPING, ageInTicks);
        this.animate(((ParrotAnimationHolder) parrot).fowlplay$getSwimmingState(), ParrotAnimations.SWIMMING, ageInTicks);
    }

    protected void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -135.0F, 135.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 45.0F);
        this.head.yRot = headYaw * (float) (Math.PI / 180.0);
        this.head.xRot = headPitch * (float) (Math.PI / 180.0);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}