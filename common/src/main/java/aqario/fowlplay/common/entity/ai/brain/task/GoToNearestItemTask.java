package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Improved {@link net.minecraft.entity.ai.brain.task.WalkToNearestVisibleWantedItemTask WalkToNearestVisibleWantedItemTask} with a speedGetter
 */
public class GoToNearestItemTask {
    public static <E extends BirdEntity> SingleTickBehaviour<E> create(Predicate<E> startPredicate, Function<E, Float> entitySpeedGetter, boolean requiresWalkTarget, int radius) {
        return new SingleTickBehaviour<>(
            MemoryList.create(4)
                .registered(
                    MemoryModuleType.LOOK_TARGET,
                    MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS
                )
                .present(
                    SBLMemoryTypes.NEARBY_ITEMS.get()
                )
                .add(
                    MemoryModuleType.WALK_TARGET, requiresWalkTarget ? MemoryModuleState.REGISTERED : MemoryModuleState.VALUE_ABSENT
                ),
            (bird, brain) -> {
                List<ItemEntity> wantedItems = BrainUtils.getMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get());
                assert wantedItems != null;
                if(!BrainUtils.hasMemory(brain, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)
                    && startPredicate.test(bird)
                    && wantedItems.get(0).isInRange(bird, radius)
                    && bird.getWorld().getWorldBorder().contains(wantedItems.get(0).getBlockPos())) {
                    WalkTarget newWalkTarget = new WalkTarget(new EntityLookTarget(wantedItems.get(0), false), entitySpeedGetter.apply(bird), 0);
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(wantedItems.get(0), true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
                    return true;
                }
                return false;
            }
        );
    }
}