package aqario.fowlplay.common.entity.ai.brain.task;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.*;

public class CompositeTasks {
    public static ImmutableList<Pair<SingleTickTask<LivingEntity>, Integer>> createLookTasks(EntityType<? extends LivingEntity> type) {
        return ImmutableList.of(
            Pair.of(LookAtMobTask.create(type, 8.0F), 1),
            Pair.of(LookAtMobTask.create(8.0F), 1)
        );
    }

    public static RandomTask<BirdEntity> makeRandomFollowTask(EntityType<? extends LivingEntity> type) {
        return new RandomTask<>(
            ImmutableList.<Pair<? extends Task<? super BirdEntity>, Integer>>builder()
                .addAll(createLookTasks(type))
                .add(Pair.of(new WaitTask(30, 60), 1))
                .build()
        );
    }

    public static RandomTask<BirdEntity> makeRandomWanderTask() {
        return new RandomTask<>(
            ImmutableList.of(
                Pair.of(StrollTask.create(0.6F), 2),
                Pair.of(TaskTriggerer.runIf(
                    bird -> true,
                    GoTowardsLookTargetTask.create(0.6F, 3)
                ), 2),
                Pair.of(new WaitTask(30, 60), 1)
            )
        );
    }
}
