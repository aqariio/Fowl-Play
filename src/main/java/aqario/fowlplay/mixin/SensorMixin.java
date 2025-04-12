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

import java.util.function.Function;

@Mixin(Sensor.class)
public class SensorMixin {
    @Unique
    private static final Function<BirdEntity, TargetPredicate> TARGET_PREDICATE = bird -> TargetPredicate.createNonAttackable()
        .setBaseMaxDistance(bird.getLookDistance());
    @Unique
    private static final Function<BirdEntity, TargetPredicate> TARGET_PREDICATE_IGNORE_DISTANCE_SCALING = bird -> TargetPredicate.createNonAttackable()
        .setBaseMaxDistance(bird.getLookDistance())
        .ignoreDistanceScalingFactor();
    @Unique
    private static final Function<BirdEntity, TargetPredicate> ATTACKABLE_TARGET_PREDICATE = bird -> TargetPredicate.createAttackable()
        .setBaseMaxDistance(bird.getLookDistance());
    @Unique
    private static final Function<BirdEntity, TargetPredicate> ATTACKABLE_TARGET_PREDICATE_IGNORE_DISTANCE_SCALING = bird -> TargetPredicate.createAttackable()
        .setBaseMaxDistance(bird.getLookDistance())
        .ignoreDistanceScalingFactor();

    @Inject(method = "testTargetPredicate", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$modifyTargetPredicateRange(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof BirdEntity bird) {
            cir.setReturnValue(bird.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ATTACK_TARGET, target)
                ? TARGET_PREDICATE_IGNORE_DISTANCE_SCALING.apply(bird).test(entity, target)
                : TARGET_PREDICATE.apply(bird).test(entity, target));
        }
    }

    @Inject(method = "testAttackableTargetPredicate", at = @At("HEAD"), cancellable = true)
    private static void fowlplay$modifyAttackableTargetPredicateRange(LivingEntity entity, LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof BirdEntity bird) {
            cir.setReturnValue(bird.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ATTACK_TARGET, target)
                ? ATTACKABLE_TARGET_PREDICATE_IGNORE_DISTANCE_SCALING.apply(bird).test(entity, target)
                : ATTACKABLE_TARGET_PREDICATE.apply(bird).test(entity, target));
        }
    }
}
