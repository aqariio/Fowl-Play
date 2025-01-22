package aqario.fowlplay.common.entity;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.Optional;

/**
 * A utility class for bird entities.
 */
public final class Bird {
    public static boolean canPickupFood(BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        if (!brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)) {
            return false;
        }
        Optional<LivingTargetCache> visibleMobs = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
        if (visibleMobs == null || visibleMobs.isEmpty()) {
            return false;
        }
        ItemEntity wantedItem = brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM).get();
        Optional<LivingEntity> avoidTarget = visibleMobs.get().stream(entity -> true)
            .filter(bird::shouldAvoid)
            .filter(EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
            .filter(entity -> entity.isInRange(wantedItem, bird.getFleeRange()))
            .findFirst();

        return !bird.getFood().test(bird.getMainHandStack()) && avoidTarget.isEmpty();
    }
}
