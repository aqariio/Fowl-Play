package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

public class AvoidTargetSensor<E extends BirdEntity> extends EntityFilteringSensor<LivingEntity, E> {
    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.AVOID_TARGET;
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return List.of(this.getMemory(), MemoryModuleType.VISIBLE_MOBS, FowlPlayMemoryModuleType.IS_AVOIDING.get());
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.AVOID_TARGETS.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> Birds.shouldAvoid(self, target);
    }

    @Override
    protected @Nullable LivingEntity findMatches(E bird, LivingTargetCache matcher) {
        return matcher.findFirst(target -> predicate().test(target, bird)).orElse(null);
    }

    @Override
    protected void sense(ServerWorld level, E bird) {
        LivingEntity avoidTarget = this.testForEntity(bird);
        if(avoidTarget != null) {
            BrainUtils.setMemory(bird, this.getMemory(), avoidTarget);
        }
        else {
            BrainUtils.clearMemory(bird, this.getMemory());
        }
        if(avoidTarget != null && avoidTarget.isInRange(bird, bird.getFleeRange(avoidTarget))) {
            BrainUtils.setMemory(bird, FowlPlayMemoryModuleType.IS_AVOIDING.get(), Unit.INSTANCE);
        }
        else {
            BrainUtils.clearMemory(bird, FowlPlayMemoryModuleType.IS_AVOIDING.get());
        }
    }
}