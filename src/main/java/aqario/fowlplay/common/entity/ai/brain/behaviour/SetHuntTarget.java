package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FPMemoryTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;
import java.util.function.BiPredicate;

public class SetHuntTarget<E extends BirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(3)
        .present(FPMemoryTypes.NEAREST_HUNTABLE.get())
        .absent(MemoryModuleType.NEAREST_ATTACKABLE)
        .absent(MemoryModuleType.ATTACK_TARGET);
    private BiPredicate<E, LivingEntity> predicate = (bird, target) -> true;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    public SetHuntTarget<E> targetPredicate(BiPredicate<E, LivingEntity> predicate) {
        this.predicate = predicate;
        return this;
    }

    @Override
    protected void start(E entity) {
        LivingEntity target = entity.getPresentMemory(FPMemoryTypes.NEAREST_HUNTABLE.get());
        if(this.predicate.test(entity, target)) {
            entity.setMemory(MemoryModuleType.ATTACK_TARGET, target);
            entity.clearMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }
    }
}
