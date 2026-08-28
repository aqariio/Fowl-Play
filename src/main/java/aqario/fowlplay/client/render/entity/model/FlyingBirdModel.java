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
    }

    @Override
    public void setCustomAnimations(T entity, long instanceId, AnimationState<T> state) {
        GeoBone root = this.getAnimationProcessor().getBone("root");

        if(root != null && entity.isFlying()) {
            float partialTick = state.getPartialTick();

            root.setRotX(root.getRotX() + entity.getViewXRot(partialTick) * Mth.DEG_TO_RAD);
            root.setRotZ(root.getRotZ() + entity.getRoll(partialTick) * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(entity, instanceId, state);
    }
}
