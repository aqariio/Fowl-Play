package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.SensoryUtils;

import java.util.List;
import java.util.function.BiPredicate;

public class CheckHuntTargets<E extends BirdEntity> extends ExtendedBehaviour<E> {
    private static final MemoryList MEMORIES = MemoryList.create(1)
        .present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
        .absent(MemoryModuleType.NEAREST_ATTACKABLE);
    private BiPredicate<E, LivingEntity> predicate = (bird, target) -> true;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORIES;
    }

    public CheckHuntTargets<E> targetPredicate(BiPredicate<E, LivingEntity> predicate) {
        this.predicate = predicate;
        return this;
    }

    @Override
    protected void start(E entity) { // TODO: fix this from overriding the nearest attackable set by AttackTargetSensor
        entity.getPresentMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
            .findClosest(it -> entity.canHunt(it) && this.canHunt(entity, it))
            .ifPresentOrElse(it -> entity.setMemory(MemoryModuleType.NEAREST_ATTACKABLE, it),
                () -> entity.clearMemory(MemoryModuleType.NEAREST_ATTACKABLE)
            );
    }

    private boolean canHunt(E self, LivingEntity target) {
        return !self.isMemoryPresent(MemoryModuleType.HAS_HUNTING_COOLDOWN)
            && SensoryUtils.isEntityAttackable(self, target)
            && this.predicate.test(self, target)
            && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target);
    }
}
