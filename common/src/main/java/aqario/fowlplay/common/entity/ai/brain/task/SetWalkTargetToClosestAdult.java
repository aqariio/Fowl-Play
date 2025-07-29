package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetWalkTargetToClosestAdult {
    @SuppressWarnings("unchecked")
    public static SingleTickBehaviour<BirdEntity> create(UniformIntProvider executionRange) {
        return new SingleTickBehaviour<>(
            MemoryList.create(3)
                .present(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get())
                .registered(MemoryModuleType.LOOK_TARGET)
                .absent(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                List<BirdEntity> nearbyAdults = (List<BirdEntity>) BrainUtils.getMemory(brain, FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get());
                // noinspection ConstantConditions
                if(nearbyAdults.isEmpty()) {
                    return false;
                }
                PassiveEntity nearest = nearbyAdults.get(0);
                if(bird.isInRange(nearest, executionRange.getMax() + 1)
                    && !bird.isInRange(nearest, executionRange.getMin())) {
                    WalkTarget newWalkTarget = new WalkTarget(
                        new EntityLookTarget(nearest, false), 1.0F, executionRange.getMin() - 1
                    );
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityLookTarget(nearest, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
                    return true;
                }
                return false;
            }
        );
    }
}
