package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;

public abstract class BirdModel<E extends BirdEntity> extends HierarchicalModel<E> {
    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart neck;
    public final ModelPart head;
    public final ModelPart torso;
    public final ModelPart leftWing;
    public final ModelPart rightWing;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart tail;

    public BirdModel(ModelPart root) {
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
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public final void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float ageInTicks = entity.tickCount + partialTick;
        float bodyYaw = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        float headYaw = Mth.rotLerp(partialTick, entity.yHeadRotO, entity.yHeadRot);
        float relativeHeadYaw = Mth.wrapDegrees(headYaw - bodyYaw);

        float headPitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        if(LivingEntityRenderer.isEntityUpsideDown(entity)) {
            headPitch *= -1.0F;
            relativeHeadYaw *= -1.0F;
        }

        this.setAnimations(entity, limbSwing, limbSwingAmount, ageInTicks, relativeHeadYaw, headPitch, partialTick);
    }

    protected void setAnimations(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {
    }

    protected void updateHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -135.0F, 135.0F);
        headPitch = Mth.clamp(headPitch, -45.0F, 45.0F);
        this.neck.yRot = headYaw * (float) (Math.PI / 180.0);
        this.neck.xRot = headPitch * (float) (Math.PI / 180.0);
    }
}
