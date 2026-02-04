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
    default ActivityGroup<? extends E> avoidActivity() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> deliverActivity() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> forageActivity() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> perchActivity() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> pickupFoodActivity() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> restActivity() {
        return ActivityGroup.empty();
    }

    default ActivityGroup<? extends E> soarActivity() {
        return ActivityGroup.empty();
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> core(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.CORE).priority(0).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> avoid(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.AVOID).priority(10).behaviors(behaviors)
            .presentMemories(FowlPlayMemoryTypes.IS_AVOIDING.get())
            .clearMemories(FowlPlayMemoryTypes.IS_AVOIDING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> deliver(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.DELIVER.get()).priority(10).behaviors(behaviors)
            .presentMemories(FowlPlayMemoryTypes.RECIPIENT.get())
            .clearMemories(FowlPlayMemoryTypes.RECIPIENT.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> fight(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.FIGHT).priority(10).behaviors(behaviors)
            .presentMemories(MemoryModuleType.ATTACK_TARGET)
            .clearMemories(MemoryModuleType.ATTACK_TARGET);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> forage(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.FORAGE.get()).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> idle(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.IDLE).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> perch(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.PERCH.get()).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> pickupFood(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.PICK_UP.get()).priority(10).behaviors(behaviors)
            .presentMemories(FowlPlayMemoryTypes.SEES_FOOD.get())
            .clearMemories(FowlPlayMemoryTypes.SEES_FOOD.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> rest(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(Activity.REST).priority(10).behaviors(behaviors);
    }

    @SafeVarargs
    static <T extends BirdEntity & BrainHolder<T>> ActivityGroup<T> soar(Behavior<? super T>... behaviors) {
        return new ActivityGroup<T>(FowlPlayActivities.SOAR.get()).priority(10).behaviors(behaviors);
    }

    @Override
    default void addActivities(final ActivityListBuilder<E> builder) {
        BrainHolder.super.addActivities(builder);

        builder.add(this.deliverActivity());
        builder.add(this.avoidActivity());
        builder.add(this.pickupFoodActivity());
        builder.add(this.forageActivity());
        builder.add(this.soarActivity());
        builder.add(this.perchActivity());
        builder.add(this.restActivity());
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
