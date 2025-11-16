package aqario.fowlplay.common.entity.ai.brain.behaviour;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.function.BiFunction;

public abstract class SpeedModifiableBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
    protected BiFunction<E, Vec3, Float> speedModifier = (entity, targetPos) -> 1f;

    public SpeedModifiableBehaviour<E> speed(float modifier) {
        return this.speed((entity, targetPos) -> modifier);
    }

    public SpeedModifiableBehaviour<E> speed(BiFunction<E, Vec3, Float> function) {
        this.speedModifier = function;

        return this;
    }
}
