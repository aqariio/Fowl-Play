package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.bird.BirdEntity;
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
    default BrainActivityGroup<? extends E> coreActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> avoidActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> deliverActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> fightActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> followActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> forageActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> huntActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> idleActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> perchActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> pickUpActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> restActivity() {
        return BrainActivityGroup.empty();
    }

    default BrainActivityGroup<? extends E> soarActivity() {
        return BrainActivityGroup.empty();
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> core(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.CORE).priority(0).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> avoid(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.AVOID).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.IS_AVOIDING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> deliver(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.DELIVER.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.RECIPIENT.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> fight(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.FIGHT).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(MemoryModuleType.ATTACK_TARGET);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> follow(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.FOLLOW.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.IS_FOLLOWING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> forage(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.FORAGE.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> hunt(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.HUNT.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> idle(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.IDLE).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> perch(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.PERCH.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> pickUp(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.PICK_UP.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryTypes.SEES_FOOD.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> rest(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.REST).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> soar(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FowlPlayActivities.SOAR.get()).priority(10).behaviours(behaviours);
    }

    @Override
    default BrainActivityGroup<? extends E> getCoreTasks() {
        return this.coreActivity();
    }

    @Override
    default BrainActivityGroup<? extends E> getIdleTasks() {
        return this.idleActivity();
    }

    @Override
    default BrainActivityGroup<? extends E> getFightTasks() {
        return this.fightActivity();
    }

    @Override
    default Map<Activity, BrainActivityGroup<? extends E>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends E>> taskList = new Object2ObjectOpenHashMap<>();
        BrainActivityGroup<? extends E> activityGroup;

        // core is already handled
        if(!(activityGroup = this.deliverActivity()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.DELIVER.get(), activityGroup);
        }
        if(!(activityGroup = this.avoidActivity()).getBehaviours().isEmpty()) {
            taskList.put(Activity.AVOID, activityGroup);
        }
        // fight is already handled
        if(!(activityGroup = this.followActivity()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.FOLLOW.get(), activityGroup);
        }
        if(!(activityGroup = this.pickUpActivity()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.PICK_UP.get(), activityGroup);
        }
        if(!(activityGroup = this.forageActivity()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.FORAGE.get(), activityGroup);
        }
        if(!(activityGroup = this.soarActivity()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.SOAR.get(), activityGroup);
        }
        if(!(activityGroup = this.perchActivity()).getBehaviours().isEmpty()) {
            taskList.put(FowlPlayActivities.PERCH.get(), activityGroup);
        }
        // idle is already handled
        if(!(activityGroup = this.restActivity()).getBehaviours().isEmpty()) {
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
