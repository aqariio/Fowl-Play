package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import aqario.fowlplay.core.FowlPlaySensorTypes;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.SensoryUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class HuntTargetSensor<E extends BirdEntity> extends EntityFilteringSensor<LivingEntity, E> {
    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return FowlPlayMemoryTypes.NEAREST_HUNTABLE.get();
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(this.getMemory(), MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorTypes.HUNT_TARGETS.get();
    }

    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> self.canHunt(target) && this.canHunt(self, target);
    }

    @Nullable
    @Override
    protected LivingEntity findMatches(E entity, NearestVisibleLivingEntities matcher) {
        return matcher.findClosest(target -> this.predicate().test(target, entity)).orElse(null);
    }

    private boolean canHunt(E self, LivingEntity target) {
        return !self.isMemoryPresent(MemoryModuleType.HAS_HUNTING_COOLDOWN)
            && SensoryUtils.isEntityAttackable(self, target)
            && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target);
    }
}