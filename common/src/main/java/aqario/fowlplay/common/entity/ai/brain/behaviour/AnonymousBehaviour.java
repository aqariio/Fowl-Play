package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;

import java.util.List;
import java.util.function.BiPredicate;

/**
 * A behaviour that operates through invoking a callback, with specified memory requirements.
 * Useful for very simple behaviours that do not require their own class. <br/>
 * Equivalent to {@link CustomBehaviour} in SmartBrainLib, but supports specifying required memory states.
 */
public class AnonymousBehaviour<E extends BirdEntity> extends ExtendedBehaviour<E> {
    private final List<Pair<MemoryModuleType<?>, MemoryStatus>> requiredMemories;
    private final BiPredicate<E, Brain<?>> callback; // TODO: phase out Predicate in favor of Consumer, run conditions should be handled through startCondition()

    public AnonymousBehaviour(List<Pair<MemoryModuleType<?>, MemoryStatus>> requiredMemories, BiPredicate<E, Brain<?>> callback) {
        this.requiredMemories = requiredMemories;
        this.callback = callback;
        for(Pair<MemoryModuleType<?>, MemoryStatus> memory : requiredMemories) {
            this.entryCondition.put(memory.getFirst(), memory.getSecond());
        }
    }

    public AnonymousBehaviour(BiPredicate<E, Brain<?>> callback) {
        this.requiredMemories = List.of();
        this.callback = callback;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        Brain<?> brain = entity.getBrain();
        for(Pair<MemoryModuleType<?>, MemoryStatus> memoryPair : this.requiredMemories) {
            if(!brain.checkMemory(memoryPair.getFirst(), memoryPair.getSecond())) {
                return false;
            }
        }
        return this.callback.test(entity, entity.getBrain());
    }
}
