package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import com.google.common.collect.Lists;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.EntityFilteringSensor;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;
import java.util.function.BiPredicate;

public class NearbyAdultsSensor<E extends AgeableMob> extends EntityFilteringSensor<List<? extends AgeableMob>, E> {
    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.NEARBY_ADULTS.get();
    }

    @Override
    protected MemoryModuleType<List<? extends AgeableMob>> getMemory() {
        return FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get();
    }

    @Override
    protected BiPredicate<LivingEntity, E> predicate() {
        return (target, self) -> target.getType() == self.getType() && !self.isBaby();
    }

    @Override
    protected List<? extends AgeableMob> findMatches(E self, NearestVisibleLivingEntities matcher) {
        List<AgeableMob> nearbyVisibleAdults = Lists.newArrayList();
        matcher.find(target -> this.predicate().test(target, self))
            .forEach(target -> nearbyVisibleAdults.add((AgeableMob) target));
        return nearbyVisibleAdults;
    }
}
