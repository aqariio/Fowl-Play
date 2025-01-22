package aqario.fowlplay.client.model;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.client.model.ModelPart;

public abstract class FlyingBirdEntityModel<E extends FlyingBirdEntity> extends BirdEntityModel<E> {
    public final ModelPart leftWingOpen;
    public final ModelPart rightWingOpen;

    public FlyingBirdEntityModel(ModelPart root) {
        super(root);
        this.leftWingOpen = this.body.getChild("left_wing_open");
        this.rightWingOpen = this.body.getChild("right_wing_open");
    }

    @Override
    public void setAngles(E bird, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
    }
}
