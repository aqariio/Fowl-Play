package aqario.fowlplay.common.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.function.BiFunction;

public abstract class SpeedModifiableBehaviour<E extends LivingEntity> extends ExtendedBehaviour<E> {
    protected BiFunction<E, Vec3d, Float> speedModifier = (entity, targetPos) -> 1f;

    public SpeedModifiableBehaviour<E> speedModifier(float modifier) {
        return this.speedModifier((entity, targetPos) -> modifier);
    }

    public SpeedModifiableBehaviour<E> speedModifier(BiFunction<E, Vec3d, Float> function) {
        this.speedModifier = function;

        return this;
    }
}
