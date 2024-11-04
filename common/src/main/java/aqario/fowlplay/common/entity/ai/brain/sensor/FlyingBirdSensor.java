package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;

import java.util.Set;

public class FlyingBirdSensor extends Sensor<FlyingBirdEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(FowlPlayMemoryModuleType.IS_FLYING);
    }

    @Override
    protected void sense(ServerWorld world, FlyingBirdEntity bird) {
        if (bird.isFlying()) {
            bird.getBrain().remember(FowlPlayMemoryModuleType.IS_FLYING, Unit.INSTANCE);
        }
        else {
            bird.getBrain().forget(FowlPlayMemoryModuleType.IS_FLYING);
        }
    }
}
