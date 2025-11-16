package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
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
    private final List<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories;
    private final BiPredicate<E, Brain<?>> callback; // TODO: phase out Predicate in favor of Consumer, run conditions should be handled through startCondition()

    public AnonymousBehaviour(List<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories, BiPredicate<E, Brain<?>> callback) {
        this.requiredMemories = requiredMemories;
        this.callback = callback;
        for(Pair<MemoryModuleType<?>, MemoryModuleState> memory : requiredMemories) {
            this.requiredMemoryStates.put(memory.getFirst(), memory.getSecond());
        }
    }

    public AnonymousBehaviour(BiPredicate<E, Brain<?>> callback) {
        this.requiredMemories = List.of();
        this.callback = callback;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected boolean shouldRun(ServerWorld level, E entity) {
        Brain<?> brain = entity.getBrain();
        for(Pair<MemoryModuleType<?>, MemoryModuleState> memoryPair : this.requiredMemories) {
            if(!brain.isMemoryInState(memoryPair.getFirst(), memoryPair.getSecond())) {
                return false;
            }
        }
        return this.callback.test(entity, entity.getBrain());
    }
}
