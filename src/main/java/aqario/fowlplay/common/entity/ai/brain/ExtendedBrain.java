package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.mixin.ExtendedSensorAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class ExtendedBrain<E extends LivingEntity & SmartBrainOwner<E>> extends SmartBrain<E> {
    public ExtendedBrain(E owner, List<MemoryModuleType<?>> memories, List<? extends ExtendedSensor<E>> extendedSensors, @Nullable List<BrainActivityGroup<E>> taskList) {
        super(memories, extendedSensors, taskList);

        // apply a random offset to each sensor
        extendedSensors.forEach(sensor ->
            ((ExtendedSensorAccessor) sensor).fowlplay$setNextTickTime(owner.getRandom().nextInt(20))
        );
    }

    @Override
    public void tick(ServerLevel level, E entity) {
        entity.level().getProfiler().push("ExtendedBrain");

        if(this.sortBehaviours) {
            this.behaviours.sort(Comparator.comparingInt(ActivityBehaviours::priority));
            this.sortBehaviours = false;
        }

        this.forgetOutdatedMemories();
        this.tickSensors(level, entity);
        this.checkForNewBehaviours(level, entity);
        this.tickRunningBehaviours(level, entity);
        this.findAndSetActiveActivity(entity);
        this.stopBehavioursFromInactiveActivity(level, entity);

        entity.level().getProfiler().pop();

        if(entity instanceof Mob mob) {
            mob.setAggressive(BrainUtils.hasMemory(mob, MemoryModuleType.ATTACK_TARGET));
        }
    }

    protected void stopBehavioursFromInactiveActivity(ServerLevel level, E entity) {
        long gameTime = level.getGameTime();

        for(ActivityBehaviours<E> behaviourGroup : this.behaviours) {
            for(Pair<Activity, List<BehaviorControl<? super E>>> pair : behaviourGroup.behaviours()) {
                if(!this.getActiveActivities().contains(pair.getFirst())) {
                    for(BehaviorControl<? super E> behaviour : pair.getSecond()) {
                        if(behaviour.getStatus() == Behavior.Status.RUNNING) {
                            behaviour.doStop(level, entity, gameTime);
                        }
                    }
                }
            }
        }
    }
}
