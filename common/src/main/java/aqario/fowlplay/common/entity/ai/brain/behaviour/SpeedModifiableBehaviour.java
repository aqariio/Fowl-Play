package aqario.fowlplay.common.entity.ai.brain.behaviour;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.function.BiFunction;

public abstract class SpeedModifiableBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
    protected BiFunction<E, Vec3d, Float> speedModifier = (entity, targetPos) -> 1f;

    public SpeedModifiableBehaviour<E> speed(float modifier) {
        return this.speed((entity, targetPos) -> modifier);
    }

    public SpeedModifiableBehaviour<E> speed(BiFunction<E, Vec3d, Float> function) {
        this.speedModifier = function;

        return this;
    }
}
