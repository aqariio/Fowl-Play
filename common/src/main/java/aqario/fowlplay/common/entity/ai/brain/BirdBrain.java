package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.util.ActivityListBuilder;
import aqario.fowlplay.core.FPActivities;
import aqario.fowlplay.core.FPMemoryTypes;
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
    default void addActivities(final ActivityListBuilder<E> builder) {
        builder.add(this.avoidActivity());
        builder.add(this.deliverActivity());
        builder.add(this.followActivity());
        builder.add(this.forageActivity());
        builder.add(this.huntActivity());
        builder.add(this.perchActivity());
        builder.add(this.pickUpActivity());
        builder.add(this.restActivity());
        builder.add(this.soarActivity());
    }

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
            .requireAndWipeMemoriesOnUse(FPMemoryTypes.IS_AVOIDING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> deliver(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FPActivities.DELIVER.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FPMemoryTypes.RECIPIENT.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> fight(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.FIGHT).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(MemoryModuleType.ATTACK_TARGET);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> follow(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FPActivities.FOLLOW.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FPMemoryTypes.IS_FOLLOWING.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> forage(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FPActivities.FORAGE.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> hunt(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FPActivities.HUNT.get()).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> idle(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.IDLE).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> pickUp(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FPActivities.PICK_UP.get()).priority(10).behaviours(behaviours)
            .requireAndWipeMemoriesOnUse(FPMemoryTypes.SEES_FOOD.get());
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> rest(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(Activity.REST).priority(10).behaviours(behaviours);
    }

    @SafeVarargs
    static <T extends BirdEntity & BirdBrain<T>> BrainActivityGroup<T> soar(Behavior<? super T>... behaviours) {
        return new BrainActivityGroup<T>(FPActivities.SOAR.get()).priority(10).behaviours(behaviours);
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
        ActivityListBuilder<E> builder = new ActivityListBuilder<>();
        this.addActivities(builder);
        return builder.build();
    }

    @Override
    default List<Activity> getActivityPriorities() {
        return ObjectArrayList.of(
            FPActivities.DELIVER.get(),
            Activity.AVOID,
            Activity.FIGHT,
            FPActivities.FOLLOW.get(),
            FPActivities.PICK_UP.get(),
            FPActivities.HUNT.get(),
            FPActivities.FORAGE.get(),
            FPActivities.SOAR.get(),
            Activity.IDLE,
            Activity.REST
        );
    }

    @Override
    default Set<Activity> getScheduleIgnoringActivities() {
        return ObjectArraySet.of(
            FPActivities.DELIVER.get(),
            Activity.AVOID,
            Activity.FIGHT,
            FPActivities.FOLLOW.get(),
            FPActivities.PICK_UP.get()
        );
    }

    @Override
    default Activity getDefaultActivity() {
        return Activity.REST;
    }
}
