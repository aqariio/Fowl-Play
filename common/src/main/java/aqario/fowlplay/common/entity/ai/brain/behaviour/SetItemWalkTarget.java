package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.archaeopteryx.core.registry.AtrxMemoryTypes;
import aqario.archaeopteryx.core.util.MemoryList;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.List;
import java.util.function.Function;

public class SetItemWalkTarget<E extends BirdEntity> extends SpeedModifiableBehaviour<E> {
    private static final MemoryList MEMORY_REQUIREMENTS = MemoryList.create(4)
        .registered(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS
        )
        .present(
            AtrxMemoryTypes.NEARBY_ITEMS.get()
        );
    protected Function<E, Integer> radius = entity -> 32;

    public SetItemWalkTarget<E> radius(int radius) {
        return this.radius(entity -> radius);
    }

    public SetItemWalkTarget<E> radius(Function<E, Integer> function) {
        this.radius = function;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        List<ItemEntity> wantedItems = entity.getPresentMemory(AtrxMemoryTypes.NEARBY_ITEMS.get());
        ItemEntity targetItem = wantedItems.getFirst();
        if(targetItem.closerThan(entity, this.radius.apply(entity))
            && entity.level().getWorldBorder().isWithinBounds(targetItem.blockPosition())
        ) {
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
