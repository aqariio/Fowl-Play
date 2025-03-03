package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;
import java.util.Set;

public class AttackTargetSensor extends Sensor<BirdEntity> {
    @Override
    protected void sense(ServerWorld world, BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        Optional<LivingTargetCache> visibleMobs = brain.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS);
        if (visibleMobs.isEmpty()) {
            brain.forget(MemoryModuleType.NEAREST_ATTACKABLE);
            return;
        }
        Optional<LivingEntity> attackTarget = visibleMobs.get().stream(bird::canAttack)
            .filter(entity -> canAttack(bird, entity))
            .findFirst();
        if (attackTarget.isPresent()) {
            brain.remember(MemoryModuleType.NEAREST_ATTACKABLE, attackTarget.get());
            return;
        }
        Optional<LivingEntity> huntTarget = visibleMobs.get().stream(bird::canHunt)
            .filter(entity -> canHunt(bird, entity))
            .findFirst();
        if (huntTarget.isPresent()) {
            brain.remember(MemoryModuleType.NEAREST_ATTACKABLE, huntTarget.get());
            return;
        }
        brain.forget(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean canAttack(BirdEntity bird, LivingEntity target) {
        return Sensor.testAttackableTargetPredicate(bird, target)
            && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target);
    }

    private static boolean canHunt(BirdEntity bird, LivingEntity target) {
        return !bird.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN)
            && canAttack(bird, target);
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(MemoryModuleType.NEAREST_ATTACKABLE);
    }
}