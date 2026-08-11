package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
    private final Quaternionf parentTransforms = new Quaternionf();
    private final Vector3f localViewVector = new Vector3f();

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
        if(this.shouldApplyHeadRotation(entity)) {
            this.updateHeadRotation(relativeHeadYaw, headPitch);
        }
    }

    protected void setAnimations(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float relativeHeadYaw, float headPitch, float partialTick) {
    }

    protected boolean shouldApplyHeadRotation(E entity) {
        return true;
    }

    protected void updateHeadRotation(float headYaw, float headPitch) {
        this.applyHeadRotation(
            headYaw,
            -135.0F,
            135.0F,
            headPitch,
            -45.0F,
            45.0F
        );
    }

    protected void applyHeadRotation(
        float headYaw,
        float minHeadYaw,
        float maxHeadYaw,
        float headPitch,
        float minHeadPitch,
        float maxHeadPitch
    ) {
        float yaw = Mth.clamp(headYaw, minHeadYaw, maxHeadYaw) * Mth.DEG_TO_RAD;
        float pitch = Mth.clamp(headPitch, minHeadPitch, maxHeadPitch) * Mth.DEG_TO_RAD;
        float cosPitch = Mth.cos(pitch);

        this.parentTransforms
            .rotationZYX(this.root.zRot, this.root.yRot, this.root.xRot)
            .rotateZYX(this.body.zRot, this.body.yRot, this.body.xRot)
            .conjugate();

        this.localViewVector.set(
            -Mth.sin(yaw) * cosPitch,
            Mth.sin(pitch),
            -Mth.cos(yaw) * cosPitch
        );
        this.parentTransforms.transform(this.localViewVector);

        float roll = (float) Mth.atan2(
            this.parentTransforms.x * this.parentTransforms.y + this.parentTransforms.w * this.parentTransforms.z,
            0.5F - this.parentTransforms.y * this.parentTransforms.y - this.parentTransforms.z * this.parentTransforms.z
        );
        float sinRoll = Mth.sin(roll);
        float cosRoll = Mth.cos(roll);
        float unrolledX = cosRoll * this.localViewVector.x + sinRoll * this.localViewVector.y;
        float unrolledY = -sinRoll * this.localViewVector.x + cosRoll * this.localViewVector.y;
        float horizontalLength = Mth.sqrt(
            unrolledX * unrolledX + this.localViewVector.z * this.localViewVector.z
        );

        this.neck.yRot = (float) Mth.atan2(-unrolledX, -this.localViewVector.z);
        this.neck.xRot = (float) Mth.atan2(unrolledY, horizontalLength);
        this.neck.zRot = roll;
    }
}
