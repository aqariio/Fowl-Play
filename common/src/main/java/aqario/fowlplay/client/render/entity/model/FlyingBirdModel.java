package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;

public abstract class FlyingBirdModel<E extends FlyingBirdEntity> extends BirdModel<E> {
    public final ModelPart leftWingOpen;
    public final ModelPart rightWingOpen;

    public FlyingBirdModel(ModelPart root) {
        super(root);
        this.leftWingOpen = this.body.getChild("left_wing_open");
        this.rightWingOpen = this.body.getChild("right_wing_open");
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
        if(!entity.isFlying()) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
        if(entity.isFlying()) {
            this.root.xRot = entity.getViewXRot(partialTick) * (float) (Math.PI / 180.0);
            this.root.zRot = entity.getRoll(partialTick) * (float) (Math.PI / 180.0);
        }
        if(this.shouldRenderWings(entity)) {
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
        this.setAnimations(entity, limbSwing, limbSwingAmount, ageInTicks, relativeHeadYaw, headPitch, partialTick);
    }

    protected boolean shouldRenderWings(E entity) {
        return entity.isFlying();
    }
}
