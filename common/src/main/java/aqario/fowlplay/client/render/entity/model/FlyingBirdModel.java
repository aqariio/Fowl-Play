package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import net.minecraft.client.model.geom.ModelPart;

public abstract class FlyingBirdModel<E extends FlyingBirdEntity> extends BirdModel<E> {
    public final ModelPart leftWingOpen;
    public final ModelPart rightWingOpen;

    public FlyingBirdModel(ModelPart root) {
        super(root);
        this.leftWingOpen = this.body.getChild("left_wing_open");
        this.rightWingOpen = this.body.getChild("right_wing_open");
    }

    @Override
    public void setupAnim(E bird, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
    }
}
