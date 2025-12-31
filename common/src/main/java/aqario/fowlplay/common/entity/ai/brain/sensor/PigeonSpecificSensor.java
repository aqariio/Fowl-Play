package aqario.fowlplay.common.entity.ai.brain.sensor;

import aqario.fowlplay.common.entity.PigeonEntity;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import aqario.fowlplay.core.FowlPlaySensorTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;

import java.util.List;
import java.util.UUID;

public class PigeonSpecificSensor extends PredicateSensor<UUID, PigeonEntity> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(
        FowlPlayMemoryTypes.IS_FOLLOWING.get(),
        FowlPlayMemoryTypes.RECIPIENT.get()
    );
    protected UniformInt range = UniformInt.of(5, 10);

    public PigeonSpecificSensor setRange(int min, int max) {
        this.range = UniformInt.of(min, max);
        return this;
    }

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
        if(pigeon.getOwner() != null
            && pigeon.distanceToSqr(pigeon.getOwner()) > this.range.getMaxValue() * this.range.getMaxValue()
        ) {
            pigeon.setMemory(FowlPlayMemoryTypes.IS_FOLLOWING.get(), Unit.INSTANCE);
        }
        else {
            pigeon.clearMemory(FowlPlayMemoryTypes.IS_FOLLOWING.get());
        }
    }
}
