package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.ScarecrowEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class ScarecrowArmorEntityModel extends BipedEntityModel<ScarecrowEntity> {
    public ScarecrowArmorEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
            EntityModelPartNames.HAT,
            ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation.add(0.5F)),
            ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.BODY,
            ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation.add(0.1F)),
            ModelTransform.pivot(0.0F, 10.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation),
            ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.LEFT_LEG,
            ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
            ModelTransform.pivot(1.9F, 11.0F, 0.0F)
        );
        modelPartData.addChild(
            EntityModelPartNames.RIGHT_LEG,
            ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation),
            ModelTransform.pivot(-1.9F, 11.0F, 0.0F)
        );
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(ScarecrowEntity scarecrowEntity, float f, float g, float h, float i, float j) {
        this.head.pitch = (float) (Math.PI / 180.0) * scarecrowEntity.getHeadRotation().getPitch();
        this.head.yaw = (float) (Math.PI / 180.0) * scarecrowEntity.getHeadRotation().getYaw();
        this.head.roll = (float) (Math.PI / 180.0) * scarecrowEntity.getHeadRotation().getRoll();
        this.body.pitch = (float) (Math.PI / 180.0) * scarecrowEntity.getBodyRotation().getPitch();
        this.body.yaw = (float) (Math.PI / 180.0) * scarecrowEntity.getBodyRotation().getYaw();
        this.body.roll = (float) (Math.PI / 180.0) * scarecrowEntity.getBodyRotation().getRoll();
        this.leftArm.pitch = (float) (Math.PI / 180.0) * scarecrowEntity.getLeftArmRotation().getPitch();
        this.leftArm.yaw = (float) (Math.PI / 180.0) * scarecrowEntity.getLeftArmRotation().getYaw();
        this.leftArm.roll = (float) (Math.PI / 180.0) * scarecrowEntity.getLeftArmRotation().getRoll();
        this.rightArm.pitch = (float) (Math.PI / 180.0) * scarecrowEntity.getRightArmRotation().getPitch();
        this.rightArm.yaw = (float) (Math.PI / 180.0) * scarecrowEntity.getRightArmRotation().getYaw();
        this.rightArm.roll = (float) (Math.PI / 180.0) * scarecrowEntity.getRightArmRotation().getRoll();
        this.leftLeg.pitch = 0;
        this.leftLeg.yaw = 0;
        this.leftLeg.roll = 0;
        this.rightLeg.pitch = 0;
        this.rightLeg.yaw = 0;
        this.rightLeg.roll = 0;
        this.hat.copyTransform(this.head);
    }
}