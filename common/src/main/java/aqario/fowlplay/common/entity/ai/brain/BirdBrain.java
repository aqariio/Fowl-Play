package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.core.FowlPlayActivities;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;

import java.util.List;
import java.util.Map;

public interface BirdBrain<E extends BirdEntity & BirdBrain<E>> extends SmartBrainOwner<E> {
    default BrainActivityGroup<? extends E> getAvoidTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getDeliverTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getForageTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getPerchTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getPickupFoodTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getRestTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getSoarTasks() {
        return BrainActivityGroup.empty();
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> coreActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.CORE).priority(0).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> avoidActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.AVOID).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.IS_AVOIDING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> deliverActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.DELIVER.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.RECIPIENT.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> fightActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.FIGHT).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(MemoryModuleType.ATTACK_TARGET);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> forageActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.FORAGE.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> idleActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.IDLE).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> perchActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.PERCH.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> pickupFoodActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.PICK_UP.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.SEES_FOOD.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> restActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.REST).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> soarActivity(MultiTickTask<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.SOAR.get()).priority(10).behaviours(behaviours);
    }

    @Override
    default Map<Activity, BrainActivityGroup<? extends E>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends E>> taskList = new Object2ObjectOpenHashMap<>();
        BrainActivityGroup<? extends E> activityGroup;

        // core is already handled
        if(!(activityGroup = this.getDeliverTasks()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.DELIVER.get(), activityGroup);
        }
        if(!(activityGroup = this.getAvoidTasks()).getBehaviours().isEmpty()) {
            taskList.put(Activity.AVOID, activityGroup);
        }
        // fight is already handled
        if(!(activityGroup = this.getPickupFoodTasks()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.PICK_UP.get(), activityGroup);
        }
        if(!(activityGroup = this.getForageTasks()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.FORAGE.get(), activityGroup);
        }
        if(!(activityGroup = this.getSoarTasks()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.SOAR.get(), activityGroup);
        }
        if(!(activityGroup = this.getPerchTasks()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.PERCH.get(), activityGroup);
        }
        // idle is already handled
        if(!(activityGroup = this.getRestTasks()).getBehaviours().isEmpty()) {
            taskList.put(Activity.REST, activityGroup);
        }

        return taskList;
    }

    @Override
    default List<Activity> getActivityPriorities() {
        return ObjectArrayList.of(
            FowlPlayActivities.DELIVER.get(),
            Activity.AVOID,
            Activity.FIGHT,
            FowlPlayActivities.PICK_UP.get(),
            FowlPlayActivities.FORAGE.get(),
            FowlPlayActivities.SOAR.get(),
            FowlPlayActivities.PERCH.get(),
            Activity.IDLE,
            Activity.REST
        );
    }

    @Override
    default Activity getDefaultActivity() {
        return Activity.REST;
    }
}
