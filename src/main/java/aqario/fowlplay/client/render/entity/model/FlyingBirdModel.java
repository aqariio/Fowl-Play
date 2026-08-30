package aqario.fowlplay.client.render.entity.model;

import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class FlyingBirdModel<T extends FlyingBirdEntity & GeoAnimatable> extends BirdModel<T> {
    private Predicate<T> renderWingsPredicate = FlyingBirdEntity::isFlying;

    public FlyingBirdModel(Supplier<EntityType<T>> entity) {
        super(entity);
        this.dontRotateHeadWhen(bird -> bird.isSleeping() || bird.isFlying());
    }

    protected GeoBone leftWingOpen() {
        return this.getPresentBone("left_wing_open");
    }

    protected GeoBone rightWingOpen() {
        return this.getPresentBone("right_wing_open");
    }

    @Override
    public void setCustomAnimations(T bird, long instanceId, AnimationState<T> state) {
        if(bird.isFlying()) {
            float partialTick = state.getPartialTick();

            this.root().setRotX(bird.getViewXRot(partialTick) * Mth.DEG_TO_RAD);
            this.root().setRotZ(bird.getRoll(partialTick) * Mth.DEG_TO_RAD);
        }

        boolean renderOpenWings = this.renderWingsPredicate.test(bird);
        this.leftWingOpen().setHidden(!renderOpenWings);
        this.rightWingOpen().setHidden(!renderOpenWings);
        this.leftWing().setHidden(renderOpenWings);
        this.rightWing().setHidden(renderOpenWings);

        super.setCustomAnimations(bird, instanceId, state);
    }

    public FlyingBirdModel<T> renderWingsWhen(Predicate<T> predicate) {
        this.renderWingsPredicate = predicate;
        return this;
    }
}
