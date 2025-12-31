package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.core.FowlPlayActivities;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BirdBrain<E extends BirdEntity & BirdBrain<E>> extends SmartBrainOwner<E> {
    default BrainActivityGroup<? extends E> getAvoidTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getDeliverTasks() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> getFollowTasks() {
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
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> coreActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.CORE).priority(0).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> avoidActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.AVOID).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.IS_AVOIDING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> deliverActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.DELIVER.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.RECIPIENT.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> fightActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.FIGHT).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(MemoryModuleType.ATTACK_TARGET);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> followActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.FOLLOW.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.IS_FOLLOWING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> forageActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.FORAGE.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> idleActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.IDLE).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> perchActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.PERCH.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> pickupFoodActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.PICK_UP.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.SEES_FOOD.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> restActivity(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.REST).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> soarActivity(Behavior<? super T>... behaviours) {
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
        if(!(activityGroup = this.getFollowTasks()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.FOLLOW.get(), activityGroup);
        }
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
            FowlPlayActivities.FOLLOW.get(),
            FowlPlayActivities.PICK_UP.get(),
            FowlPlayActivities.FORAGE.get(),
            FowlPlayActivities.SOAR.get(),
            FowlPlayActivities.PERCH.get(),
            Activity.IDLE,
            Activity.REST
        );
    }

    @Override
    default Set<Activity> getScheduleIgnoringActivities() {
        return ObjectArraySet.of(
            FowlPlayActivities.DELIVER.get(),
            Activity.AVOID,
            Activity.FIGHT,
            FowlPlayActivities.FOLLOW.get(),
            FowlPlayActivities.PICK_UP.get()
        );
    }

    @Override
    default Activity getDefaultActivity() {
        return Activity.REST;
    }
}
