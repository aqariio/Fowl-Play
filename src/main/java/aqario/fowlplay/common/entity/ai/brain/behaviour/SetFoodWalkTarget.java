package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FPMemoryTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;

public class SetFoodWalkTarget<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORY_REQUIREMENTS = MemoryList.create(4)
        .registered(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS
        )
        .present(
            FPMemoryTypes.NEAREST_FOOD_ITEM.get()
        );

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        ItemEntity targetItem = entity.getPresentMemory(FPMemoryTypes.NEAREST_FOOD_ITEM.get());
        if(entity.level().getWorldBorder().isWithinBounds(targetItem.blockPosition())) {
            WalkTarget newWalkTarget = new WalkTarget(
                new EntityTracker(targetItem, false),
                this.speedModifier.apply(entity, targetItem.position()),
                0
            );
            if(!entity.isMemoryPresent(MemoryModuleType.AVOID_TARGET)) {
                entity.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(targetItem, true));
            }
            entity.setMemory(MemoryModuleType.WALK_TARGET, newWalkTarget);
        }
    }
}
