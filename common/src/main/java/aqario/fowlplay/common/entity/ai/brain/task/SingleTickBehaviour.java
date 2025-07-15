package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;
import java.util.function.BiPredicate;

/**
 * A behaviour that operates purely through side effects. Useful for when I'm too lazy to write an entire class.
 */
public class SingleTickBehaviour<E extends BirdEntity> extends ExtendedBehaviour<E> {
    private final List<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories;
    private final BiPredicate<E, Brain<?>> callback;

    public SingleTickBehaviour(List<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories, BiPredicate<E, Brain<?>> callback) {
        this.requiredMemories = requiredMemories;
        this.callback = callback;
        for(Pair<MemoryModuleType<?>, MemoryModuleState> memoryReq : requiredMemories) {
            this.requiredMemoryStates.put(memoryReq.getFirst(), memoryReq.getSecond());
        }
    }

    public SingleTickBehaviour(BiPredicate<E, Brain<?>> callback) {
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
