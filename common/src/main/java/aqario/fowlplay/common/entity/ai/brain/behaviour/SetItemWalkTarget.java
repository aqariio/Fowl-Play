package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.item.ItemEntity;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

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
            SBLMemoryTypes.NEARBY_ITEMS.get()
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
        Brain<?> brain = entity.getBrain();
        List<ItemEntity> wantedItems = BrainUtils.getMemory(brain, SBLMemoryTypes.NEARBY_ITEMS.get());
        // noinspection ConstantConditions
        ItemEntity targetItem = wantedItems.getFirst();
        if(targetItem.closerThan(entity, this.radius.apply(entity))
            && entity.level().getWorldBorder().isWithinBounds(targetItem.blockPosition())
        ) {
            WalkTarget newWalkTarget = new WalkTarget(new EntityTracker(targetItem, false), this.speedModifier.apply(entity, targetItem.position()), 0);
            if(!BrainUtils.hasMemory(brain, MemoryModuleType.AVOID_TARGET)) {
                BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(targetItem, true));
            }
            BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
        }
    }
}
