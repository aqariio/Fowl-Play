package aqario.fowlplay.common.entity.ai.brain;

import aqario.archaeopteryx.common.ai.ActivityGroup;
import aqario.archaeopteryx.common.ai.BrainHolder;
import aqario.archaeopteryx.core.util.ActivityListBuilder;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.core.FowlPlayActivities;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public interface BirdBrain<E extends BirdEntity & BirdBrain<E>> extends BrainHolder<E> {
    default ActivityGroup<? extends E> getAvoidTasks() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> getDeliverTasks() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> getForageTasks() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> getPerchTasks() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> getPickupFoodTasks() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> getRestTasks() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> getSoarTasks() {
        return ActivityGroup.empty();
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> coreActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.CORE).priority(0).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> avoidActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.AVOID).priority(10).behaviors(behaviors)
            .presentMemories(FowlPlayMemoryTypes.IS_AVOIDING.get())
            .clearMemories(FowlPlayMemoryTypes.IS_AVOIDING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> deliverActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.DELIVER.get()).priority(10).behaviors(behaviors)
            .presentMemories(FowlPlayMemoryTypes.RECIPIENT.get())
            .clearMemories(FowlPlayMemoryTypes.RECIPIENT.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> fightActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.FIGHT).priority(10).behaviors(behaviors)
            .presentMemories(MemoryModuleType.ATTACK_TARGET)
            .clearMemories(MemoryModuleType.ATTACK_TARGET);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> forageActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.FORAGE.get()).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> idleActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.IDLE).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> perchActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.PERCH.get()).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> pickupFoodActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.PICK_UP.get()).priority(10).behaviors(behaviors)
            .presentMemories(FowlPlayMemoryTypes.SEES_FOOD.get())
            .clearMemories(FowlPlayMemoryTypes.SEES_FOOD.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> restActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.REST).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> soarActivity(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.SOAR.get()).priority(10).behaviors(behaviors);
    }

    @Override
    default void addActivities(final ActivityListBuilder<E> builder) {
        BrainHolder.super.addActivities(builder);

        builder.add(this.getDeliverTasks());
        builder.add(this.getAvoidTasks());
        builder.add(this.getPickupFoodTasks());
        builder.add(this.getForageTasks());
        builder.add(this.getSoarTasks());
        builder.add(this.getPerchTasks());
        builder.add(this.getRestTasks());
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
    default Set<Activity> getScheduleIgnoringActivities() {
        return ObjectArraySet.of(
            FowlPlayActivities.DELIVER.get(),
            Activity.AVOID,
            Activity.FIGHT,
            FowlPlayActivities.PICK_UP.get()
        );
    }

    @Override
    default Activity getDefaultActivity() {
        return Activity.REST;
    }
}
