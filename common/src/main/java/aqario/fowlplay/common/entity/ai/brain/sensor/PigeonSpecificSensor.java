package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.archaeopteryx.common.ai.sensing.ExtendedSensor;
import aqario.archaeopteryx.common.ai.sensing.PredicateSensor;
import aqario.fowlplay.common.entity.bird.pigeon.PigeonEntity;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import aqario.fowlplay.core.FowlPlaySensorTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;

import java.util.List;
import java.util.UUID;

public class PigeonSpecificSensor extends PredicateSensor<UUID, PigeonEntity> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
        FowlPlayMemoryTypes.RECIPIENT.get()
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
        return FowlPlaySensorTypes.PIGEON_SPECIFIC_SENSOR.get();
    }

    @Override
    protected void doTick(ServerLevel world, PigeonEntity pigeon) {
        if(this.predicate().test(null, pigeon)) {
            pigeon.setMemory(FowlPlayMemoryTypes.RECIPIENT.get(), pigeon.getRecipientUuid());
        }
        else {
            pigeon.clearMemory(FowlPlayMemoryTypes.RECIPIENT.get());
        }
    }
}
