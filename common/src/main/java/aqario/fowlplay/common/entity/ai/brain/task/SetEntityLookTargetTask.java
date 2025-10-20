package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.Optional;
import java.util.function.BiPredicate;

public class SetEntityLookTargetTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> create() {
        return create((entity, target) -> true);
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> create(SpawnGroup spawnGroup) {
        return create((entity, target) -> spawnGroup.equals(target.getType().getSpawnGroup()));
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> create(EntityType<?> type) {
        return create((entity, target) -> type.equals(target.getType()));
    }

    public static <E extends BirdEntity> SingleTickBehaviour<E> create(BiPredicate<E, LivingEntity> predicate) {
        return new SingleTickBehaviour<>(
            MemoryList.create(2)
                .absent(MemoryModuleType.LOOK_TARGET)
                .present(MemoryModuleType.VISIBLE_MOBS),
            (bird, brain) -> {
                // noinspection ConstantConditions
                Optional<LivingEntity> targetEntity = BrainUtils.getMemory(brain, MemoryModuleType.VISIBLE_MOBS)
                    .findFirst(target -> predicate.test(bird, target) && !bird.hasPassenger(target));
                if(targetEntity.isEmpty()) {
                    return false;
                }
                BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(targetEntity.get(), true));
                return true;
            }
        );
    }
}
