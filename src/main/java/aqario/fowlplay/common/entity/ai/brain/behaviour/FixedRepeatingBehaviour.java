package aqario.fowlplay.common.entity.ai.brain.behaviour;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.RepeatingBehaviour;

import java.util.List;

public class FixedRepeatingBehaviour<E extends LivingEntity> extends RepeatingBehaviour<E> {
    public FixedRepeatingBehaviour(ExtendedBehaviour<E> child) {
        super(child);

        for(Pair<MemoryModuleType<?>, MemoryStatus> memoryReq : this.getMemoryRequirements()) {
            this.entryCondition.put(memoryReq.getFirst(), memoryReq.getSecond());
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return this.child == null ? List.of() : super.getMemoryRequirements();
    }
}
