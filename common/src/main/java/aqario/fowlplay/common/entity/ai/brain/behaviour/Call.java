package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class Call<E extends BirdEntity> extends ExtendedBehaviour<E> {
    public Call() {
        this.runFor(entity -> entity.getRandom().nextIntBetweenInclusive(
            entity.getCallDelay(),
            entity.getCallDelay() + 40
        ));
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return entity.canCall();
    }

    @Override
    protected void start(E entity) {
        entity.playCallSound();
    }
}
