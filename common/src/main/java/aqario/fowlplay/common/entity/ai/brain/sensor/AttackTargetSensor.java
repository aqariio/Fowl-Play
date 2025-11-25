package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.core.FowlPlaySensorTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.SensoryUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class AttackTargetSensor<E extends BirdEntity> extends EntityFilteringSensor<LivingEntity, E> {
    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(this.getMemory(), MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorTypes.ATTACK_TARGETS.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> {
            if (self.shouldAttack(target) && canAttack(self, target)) {
                return true;
            }
            return self.canHunt(target) && canHunt(self, target);
        };
    }

    @Nullable
    @Override
    protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
        return matcher.findClosest(target -> predicate().test(target, entity)).orElse(null);
    }

    private static boolean canAttack(BirdEntity bird, LivingEntity target) {
        return SensoryUtils.isEntityAttackable(bird, target)
            && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target);
    }

    private static boolean canHunt(BirdEntity bird, LivingEntity target) {
        return !BrainUtils.hasMemory(bird, MemoryModuleType.HAS_HUNTING_COOLDOWN)
            && canAttack(bird, target);
    }
}