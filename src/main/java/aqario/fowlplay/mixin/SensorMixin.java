package aqario.fowlplay.mixin;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sensor.class)
public class SensorMixin {
    @Unique
    private static final double SIGHT_DISTANCE = 32.0;
    @Unique
    private static final TargetPredicate TARGET_PREDICATE = TargetPredicate.createNonAttackable()
        .setBaseMaxDistance(SIGHT_DISTANCE);
    @Unique
    private static final TargetPredicate TARGET_PREDICATE_IGNORE_DISTANCE_SCALING = TargetPredicate.createNonAttackable()
        .setBaseMaxDistance(SIGHT_DISTANCE)
        .ignoreDistanceScalingFactor();
    @Unique
    private static final TargetPredicate ATTACKABLE_TARGET_PREDICATE = TargetPredicate.createAttackable()
        .setBaseMaxDistance(SIGHT_DISTANCE);
    @Unique
    private static final TargetPredicate ATTACKABLE_TARGET_PREDICATE_IGNORE_DISTANCE_SCALING = TargetPredicate.createAttackable()
        .setBaseMaxDistance(SIGHT_DISTANCE)
        .ignoreDistanceScalingFactor();

    @Inject(method = "testTargetPredicate", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$modifyTargetPredicateRange(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof BirdEntity bird) {
            cir.setReturnValue(bird.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ATTACK_TARGET, target)
                ? TARGET_PREDICATE_IGNORE_DISTANCE_SCALING.test(entity, target)
                : TARGET_PREDICATE.test(entity, target));
        }
    }

    @Inject(method = "testAttackableTargetPredicate", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$modifyAttackableTargetPredicateRange(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof BirdEntity bird) {
            cir.setReturnValue(bird.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ATTACK_TARGET, target)
                ? ATTACKABLE_TARGET_PREDICATE_IGNORE_DISTANCE_SCALING.test(entity, target)
                : ATTACKABLE_TARGET_PREDICATE.test(entity, target));
        }
    }
}
