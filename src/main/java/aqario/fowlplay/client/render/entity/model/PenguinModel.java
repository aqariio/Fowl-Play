package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.penguin.PenguinEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.function.Supplier;

public class PenguinModel extends BirdModel<PenguinEntity> {
    public PenguinModel(Supplier<EntityType<PenguinEntity>> entityType) {
        super(entityType);
        this.customNames("Pingu");
        this.dontRotateHeadWhen(penguin ->
            penguin.isSleeping()
                || penguin.isIdleAnimationActive()
                || penguin.isSliding()
                || penguin.isSwimming()
                || penguin.isSongPlaying()
        );
    }

    @Override
    public void setCustomAnimations(PenguinEntity penguin, long instanceId, AnimationState<PenguinEntity> state) {
        if(penguin.isSwimming()) {
            EntityModelData entityData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            this.root().setRotX(this.root().getRotX() + entityData.headPitch() * Mth.DEG_TO_RAD);
            this.root().setRotY(this.root().getRotY() + entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        super.setCustomAnimations(penguin, instanceId, state);
    }
}
