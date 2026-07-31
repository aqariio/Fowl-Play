//~ expect_platform
package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.fabric.common.entity.ai.brain.ExtendedBrainProviderImpl;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Extension of {@link net.tslat.smartbrainlib.api.core.SmartBrainProvider} that uses the
 * ExtendedBrain class for its activity-bound behaviour stopping
 */
public class ExtendedBrainProvider<E extends LivingEntity & ExtendedBrainOwner<E>> extends Brain.Provider<E> {
    private static final Map<EntityType<? extends LivingEntity>, ImmutableList<MemoryModuleType<?>>> BRAIN_MEMORY_CACHE = new Object2ObjectOpenHashMap<>();
    private final E owner;
    private final boolean nonStaticMemories;

    public ExtendedBrainProvider(E owner) {
        this(owner, false);
    }

    public ExtendedBrainProvider(E owner, boolean nonStaticMemories) {
        super(List.of(), List.of());

        this.owner = owner;
        this.nonStaticMemories = nonStaticMemories;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public final SmartBrain<E> makeBrain(Dynamic<?> codecLoader) {
        List<? extends ExtendedSensor<? extends E>> sensors = this.owner.getSensors();
        List<BrainActivityGroup<? extends E>> taskList = this.compileTasks();
        ImmutableList<MemoryModuleType<?>> memories;

        if(!this.nonStaticMemories && BRAIN_MEMORY_CACHE.containsKey(this.owner.getType())) {
            memories = BRAIN_MEMORY_CACHE.get(this.owner.getType());
        }
        else {
            memories = this.createMemoryList(taskList, sensors);

            if(!this.nonStaticMemories) {
                BRAIN_MEMORY_CACHE.put((EntityType<? extends LivingEntity>) this.owner.getType(), memories);
            }
        }

        SmartBrain<E> brain = constructBrain(this.owner, memories, (List<ExtendedSensor<E>>) sensors, (List) taskList);

        this.finaliseBrain(brain);

        return brain;
    }

    protected static <E extends LivingEntity & ExtendedBrainOwner<E>> SmartBrain<E> constructBrain(
        E owner,
        List<MemoryModuleType<?>> memories,
        List<? extends ExtendedSensor<E>> sensors,
        List<BrainActivityGroup<E>> taskList
    ) {
        return ExtendedBrainProviderImpl.constructBrain(owner, memories, sensors, taskList);
    }

    private ImmutableList<MemoryModuleType<?>> createMemoryList(List<BrainActivityGroup<? extends E>> taskList, List<? extends ExtendedSensor<?>> sensors) {
        Set<MemoryModuleType<?>> memoryTypes = new ObjectOpenHashSet<>();

        taskList.forEach(activityGroup -> activityGroup.getBehaviours().forEach(behavior -> this.collectMemoriesFromTask(memoryTypes, behavior)));
        sensors.forEach(sensor -> memoryTypes.addAll(sensor.memoriesUsed()));

        return ImmutableList.copyOf(memoryTypes);
    }

    private void collectMemoriesFromTask(Set<MemoryModuleType<?>> memories, BehaviorControl<?> behaviour) {
        if(behaviour instanceof GateBehavior<?> gateBehaviour) {
            gateBehaviour.behaviors.stream().forEach(subBehaviour -> this.collectMemoriesFromTask(memories, subBehaviour));
        }
        else if(behaviour instanceof GroupBehaviour<?> groupBehaviour) {
            groupBehaviour.getBehaviours().forEachRemaining(subBehaviour -> this.collectMemoriesFromTask(memories, subBehaviour));
        }
        else if(behaviour instanceof Behavior<?> behaviour2) {
            memories.addAll(behaviour2.entryCondition.keySet());
        }
    }

    private List<BrainActivityGroup<? extends E>> compileTasks() {
        List<BrainActivityGroup<? extends E>> tasks = new ObjectArrayList<>();
        BrainActivityGroup<? extends E> activityGroup;

        if(!(activityGroup = this.owner.getCoreTasks()).getBehaviours().isEmpty()) {
            tasks.add(activityGroup);
        }

        if(!(activityGroup = this.owner.getIdleTasks()).getBehaviours().isEmpty()) {
            tasks.add(activityGroup);
        }

        if(!(activityGroup = this.owner.getFightTasks()).getBehaviours().isEmpty()) {
            tasks.add(activityGroup);
        }

        tasks.addAll(this.owner.getAdditionalTasks().values());

        return tasks;
    }

    private void finaliseBrain(SmartBrain<E> brain) {
        brain.setCoreActivities(this.owner.getAlwaysRunningActivities());
        brain.setDefaultActivity(this.owner.getDefaultActivity());
        brain.useDefaultActivity();
        brain.setSchedule(this.owner.getSchedule());
        this.owner.handleAdditionalBrainSetup(brain);
    }
}
