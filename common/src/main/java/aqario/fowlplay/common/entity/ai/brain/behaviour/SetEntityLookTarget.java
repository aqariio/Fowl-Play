package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.BiPredicate;

public class SetEntityLookTarget {
    public static <E extends BirdEntity> AnonymousBehaviour<E> create() {
        return create((entity, target) -> true);
    }

    public static <E extends BirdEntity> AnonymousBehaviour<E> create(MobCategory spawnGroup) {
        return create((entity, target) -> spawnGroup.equals(target.getType().getCategory()));
    }

    public static <E extends BirdEntity> AnonymousBehaviour<E> create(EntityType<?> type) {
        return create((entity, target) -> type.equals(target.getType()));
    }

    public static <E extends BirdEntity> AnonymousBehaviour<E> create(BiPredicate<E, LivingEntity> predicate) {
        return new AnonymousBehaviour<>(
            MemoryList.create(2)
                .absent(MemoryModuleType.LOOK_TARGET)
                .present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES),
            bird -> {
                Optional<LivingEntity> targetEntity = bird.getPresentMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
                    .findClosest(target -> predicate.test(bird, target) && !bird.hasPassenger(target));
                if(targetEntity.isEmpty()) {
                    return false;
                }
                bird.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(targetEntity.get(), true));
                return true;
            }
        );
    }
}
