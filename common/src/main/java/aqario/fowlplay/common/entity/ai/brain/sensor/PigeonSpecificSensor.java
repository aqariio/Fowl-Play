package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.UUID;

public class PigeonSpecificSensor extends PredicateSensor<UUID, PigeonEntity> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
        FowlPlayMemoryModuleType.RECIPIENT.get()
    );

    public PigeonSpecificSensor() {
        super(
            (uuid, pigeon) -> pigeon.isTamed()
                && pigeon.getRecipientUuid() != null
                && pigeon.level().getPlayerByUUID(pigeon.getRecipientUuid()) != null
        );
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES;
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return FowlPlaySensorType.PIGEON_SPECIFIC_SENSOR.get();
    }

    @Override
    protected void doTick(ServerLevel world, PigeonEntity pigeon) {
        if (this.predicate().test(null, pigeon)) {
            BrainUtils.setMemory(pigeon, FowlPlayMemoryModuleType.RECIPIENT.get(), pigeon.getRecipientUuid());
        }
        else {
            BrainUtils.clearMemory(pigeon, FowlPlayMemoryModuleType.RECIPIENT.get());
        }
    }
}
