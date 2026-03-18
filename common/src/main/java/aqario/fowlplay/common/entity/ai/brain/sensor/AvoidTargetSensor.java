package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import aqario.fowlplay.core.FowlPlaySensorTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class AvoidTargetSensor<E extends BirdEntity> extends EntityFilteringSensor<LivingEntity, E> {
    public AvoidTargetSensor() {
        this.setScanRate(bird -> 10);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.AVOID_TARGET;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(this.getMemory(), MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, FowlPlayMemoryTypes.IS_AVOIDING.get());
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorTypes.AVOID_TARGETS.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> BirdUtils.shouldAvoid(self, target);
    }

    @Override
    protected @Nullable LivingEntity findMatches(E bird, NearestVisibleLivingEntities matcher) {
        return matcher.findClosest(target -> this.predicate().test(target, bird)).orElse(null);
    }

    @Override
    protected void doTick(ServerLevel level, E bird) {
        LivingEntity avoidTarget = this.testForEntity(bird);
        if(avoidTarget != null) {
            BrainUtils.setMemory(bird, this.getMemory(), avoidTarget);
        }
        else {
            BrainUtils.clearMemory(bird, this.getMemory());
        }
        if(avoidTarget != null && avoidTarget.closerThan(bird, bird.getFleeRange(avoidTarget))) {
            BrainUtils.setMemory(bird, FowlPlayMemoryTypes.IS_AVOIDING.get(), Unit.INSTANCE);
        }
        else {
            BrainUtils.clearMemory(bird, FowlPlayMemoryTypes.IS_AVOIDING.get());
        }
    }
}