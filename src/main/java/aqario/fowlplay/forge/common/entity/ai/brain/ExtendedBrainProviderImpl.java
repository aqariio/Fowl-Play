//? if forge {
/*package aqario.fowlplay.forge.common.entity.ai.brain;

import aqario.fowlplay.common.entity.ai.brain.ExtendedBrainOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

public class ExtendedBrainProviderImpl {
    public static <E extends LivingEntity & ExtendedBrainOwner<E>> SmartBrain<E> constructBrain(
        E owner,
        List<MemoryModuleType<?>> memories,
        List<? extends ExtendedSensor<E>> sensors,
        List<BrainActivityGroup<E>> taskList
    ) {
        return new ExtendedBrain<>(owner, memories, sensors, taskList);
    }
}
*///?}