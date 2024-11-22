package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;

import java.util.Set;

public class PigeonSpecificSensor extends Sensor<PigeonEntity> {
    @Override
    protected void sense(ServerWorld world, PigeonEntity pigeon) {
        if (pigeon.getRecipientUuid() == null) {
            pigeon.getBrain().forget(FowlPlayMemoryModuleType.RECIPIENT);
        }
        else if (pigeon.isTamed() && pigeon.getWorld().getPlayerByUuid(pigeon.getRecipientUuid()) != null) {
            pigeon.getBrain().remember(FowlPlayMemoryModuleType.RECIPIENT, pigeon.getRecipientUuid());
        }
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(
            FowlPlayMemoryModuleType.RECIPIENT
        );
    }
}
