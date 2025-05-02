package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Improved {@link net.minecraft.entity.ai.brain.task.WalkToNearestVisibleWantedItemTask WalkToNearestVisibleWantedItemTask} with a speedGetter
 */
public class GoToNearestWantedItemTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> create(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, int radius) {
        return new SingleTickBehaviour<>(
            List.of(
                Pair.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED),
                Pair.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleState.VALUE_PRESENT),
                Pair.of(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryModuleState.REGISTERED),
                requiresWalkTarget
                    ? Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED)
                    : Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
            ),
            (bird, brain) -> {
                ItemEntity itemEntity = BrainUtils.getMemory(brain, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
                assert itemEntity != null;
                if (!BrainUtils.hasMemory(brain, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
                    && startPredicate.test(bird)
                    && itemEntity.isInRange(bird, radius)
                    && bird.getWorld().getWorldBorder().contains(itemEntity.getBlockPos())) {
                    WalkTarget newWalkTarget = new WalkTarget(new EntityLookTarget(itemEntity, false), entitySpeedGetter.apply(bird), 0);
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(itemEntity, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
                    return true;
                }
                return false;
            }
        );
    }
}