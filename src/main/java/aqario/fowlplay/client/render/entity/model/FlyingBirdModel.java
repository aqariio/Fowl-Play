package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;

import java.util.function.Supplier;

public class FlyingBirdModel<T extends FlyingBirdEntity & GeoAnimatable> extends BirdModel<T> {
    public FlyingBirdModel(Supplier<EntityType<T>> entity) {
        super(entity);
        this.dontRotateHeadWhen(bird -> bird.isSleeping() || bird.isFlying());
    }

    @Override
    public void setCustomAnimations(T bird, long instanceId, AnimationState<T> state) {
        GeoBone root = this.getAnimationProcessor().getBone("root");

        if(root != null && bird.isFlying()) {
            float partialTick = state.getPartialTick();

            root.setRotX(root.getRotX() + bird.getViewXRot(partialTick) * Mth.DEG_TO_RAD);
            root.setRotZ(root.getRotZ() + bird.getRoll(partialTick) * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(bird, instanceId, state);
    }
}
