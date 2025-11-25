package aqario.fowlplay.common.entity.ai.brain.behaviour;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.util.MemoryList;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SetAdultWalkTarget {
    @SuppressWarnings("unchecked")
    public static AnonymousBehaviour<BirdEntity> create(UniformInt executionRange) {
        return new AnonymousBehaviour<>(
            MemoryList.create(3)
                .present(FowlPlayMemoryTypes.NEAREST_VISIBLE_ADULTS.get())
                .registered(MemoryModuleType.LOOK_TARGET)
                .absent(MemoryModuleType.WALK_TARGET),
            (bird, brain) -> {
                List<BirdEntity> nearbyAdults = (List<BirdEntity>) BrainUtils.getMemory(brain, FowlPlayMemoryTypes.NEAREST_VISIBLE_ADULTS.get());
                // noinspection ConstantConditions
                if(nearbyAdults.isEmpty()) {
                    return false;
                }
                AgeableMob nearest = nearbyAdults.getFirst();
                if(bird.closerThan(nearest, executionRange.getMaxValue() + 1)
                    && !bird.closerThan(nearest, executionRange.getMinValue())) {
                    WalkTarget newWalkTarget = new WalkTarget(
                        new EntityTracker(nearest, false), 1.0F, executionRange.getMinValue() - 1
                    );
                    BrainUtils.setMemory(brain, MemoryModuleType.LOOK_TARGET, new EntityTracker(nearest, true));
                    BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, newWalkTarget);
                    return true;
                }
                return false;
            }
        );
    }
}
