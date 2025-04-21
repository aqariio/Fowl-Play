package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayActivities;
import aqario.fowlplay.core.FowlPlayEntityType;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySensorType;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class DuckBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super DuckEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_PLAYERS,
        SensorType.NEAREST_ITEMS,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY,
        SensorType.IS_IN_WATER,
        FowlPlaySensorType.NEARBY_LIVING_ENTITIES,
        FowlPlaySensorType.IS_FLYING,
        FowlPlaySensorType.NEARBY_ADULTS,
        FowlPlaySensorType.TEMPTING_PLAYER,
        FowlPlaySensorType.AVOID_TARGETS,
        FowlPlaySensorType.ATTACK_TARGETS
    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.MOBS,
        MemoryModuleType.VISIBLE_MOBS,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.PATH,
        MemoryModuleType.BREED_TARGET,
        MemoryModuleType.NEAREST_VISIBLE_PLAYER,
        MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
        MemoryModuleType.AVOID_TARGET,
        MemoryModuleType.ATTACK_COOLING_DOWN,
        MemoryModuleType.NEAREST_VISIBLE_ADULT,
        MemoryModuleType.ATTACK_TARGET,
        MemoryModuleType.HAS_HUNTING_COOLDOWN,
        MemoryModuleType.TEMPTING_PLAYER,
        MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
        MemoryModuleType.GAZE_COOLDOWN_TICKS,
        MemoryModuleType.IS_TEMPTED,
        MemoryModuleType.HURT_BY,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.NEAREST_ATTACKABLE,
        MemoryModuleType.IS_IN_WATER,
        MemoryModuleType.IS_PREGNANT,
        MemoryModuleType.IS_PANICKING,
        MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
        MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
        MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
        FowlPlayMemoryModuleType.IS_AVOIDING,
        FowlPlayMemoryModuleType.IS_FLYING,
        FowlPlayMemoryModuleType.SEES_FOOD,
        FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD,
        FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS
    );

    public static Brain.Profile<DuckEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<DuckEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFlyActivities(brain);
        addAvoidActivities(brain);
        addPickupFoodActivities(brain);
        addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void reset(DuckEntity duck) {
        Brain<DuckEntity> brain = duck.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        brain.resetPossibleActivities(
            ImmutableList.of(
                Activity.IDLE,
                FowlPlayActivities.FLY,
                Activity.AVOID,
                FowlPlayActivities.PICK_UP,
                Activity.FIGHT
            )
        );
        if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
    }

    private static void addCoreActivities(Brain<DuckEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                FlightControlTask.stopFalling(),
                new FleeTask<>(Birds.RUN_SPEED),
                AvoidTask.run(),
                PickupFoodTask.run(Birds::canPickupFood),
                new LookAroundTask(45, 90),
                new MoveToTargetTask(),
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<DuckEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(1, new BreedTask(FowlPlayEntityType.DUCK, Birds.WALK_SPEED, 20)),
                Pair.of(2, WalkTowardClosestAdultTask.create(Birds.FOLLOW_ADULT_RANGE, Birds.WALK_SPEED)),
                Pair.of(3, LookAtMobTask.create(DuckBrain::isPlayerHoldingFood, 32.0F)),
                Pair.of(4, UpdateAttackTargetTask.create(duck -> !duck.isInsideWaterOrBubbleColumn(), DuckBrain::getAttackTarget)),
                Pair.of(5, GoToClosestEntityTask.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.WALK_SPEED)),
                Pair.of(6, new RandomLookAroundTask(
                    UniformIntProvider.create(150, 250),
                    60.0F,
                    -40.0F,
                    40.0F
                )),
                Pair.of(
                    7,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(TaskTriggerer.runIf(
                                Predicate.not(Birds::isPerched),
                                StrollTask.create(Birds.WALK_SPEED)
                            ), 4),
                            Pair.of(TaskTriggerer.predicate(Entity::isInsideWaterOrBubbleColumn), 3),
                            Pair.of(new WaitTask(100, 300), 3),
                            Pair.of(FlightControlTask.startFlying(duck -> duck.getRandom().nextFloat() < 0.1F), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addFlyActivities(Brain<DuckEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.FLY,
            ImmutableList.of(
                Pair.of(1, FlightControlTask.tryStopFlying(duck -> true)),
                Pair.of(2, UpdateAttackTargetTask.create(DuckBrain::getAttackTarget)),
                Pair.of(3, GoToClosestEntityTask.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.FLY_SPEED)),
                Pair.of(
                    4,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(TargetlessFlyTask.create(Birds.FLY_SPEED, 24, 16), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addAvoidActivities(Brain<DuckEntity> brain) {
        brain.setTaskList(
            Activity.AVOID,
            10,
            ImmutableList.of(
                FlightControlTask.startFlying(duck -> true),
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    duck -> duck.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true
                ),
                CompositeTasks.makeRandomFollowTask(FowlPlayEntityType.DUCK),
                CompositeTasks.makeRandomWanderTask(),
                AvoidTask.forget()
            ),
            FowlPlayMemoryModuleType.IS_AVOIDING
        );
    }

    private static void addPickupFoodActivities(Brain<DuckEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.PICK_UP,
            ImmutableList.of(
                Pair.of(0, FlightControlTask.startFlying(Birds::canPickupFood)),
                Pair.of(1, GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                )),
                Pair.of(2, ForgetTask.create(DuckBrain::noFoodInRange, FowlPlayMemoryModuleType.SEES_FOOD))
            ),
            Set.of(
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addFightActivities(Brain<DuckEntity> brain) {
        brain.setTaskList(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                FlightControlTask.startFlying(duck -> true),
                ForgetAttackTargetTask.create(),
                RangedApproachTask.create(Birds.FLY_SPEED),
                MeleeAttackTask.create(20),
                ForgetTask.create(LookTargetUtil::hasBreedTarget, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static Optional<? extends LivingEntity> getAttackTarget(DuckEntity duck) {
        return LookTargetUtil.hasBreedTarget(duck) ? Optional.empty() : duck.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static void onAttacked(DuckEntity duck, LivingEntity attacker) {
        if (attacker instanceof DuckEntity) {
            return;
        }
        Brain<DuckEntity> brain = duck.getBrain();
        brain.forget(FowlPlayMemoryModuleType.SEES_FOOD);
        if (attacker instanceof PlayerEntity player) {
            brain.remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
            duck.stopTrusting(player);
        }
        brain.remember(MemoryModuleType.AVOID_TARGET, attacker, 160L);
        alertOthers(duck, attacker);
    }

    protected static void alertOthers(DuckEntity duck, LivingEntity attacker) {
        getNearbyVisibleDucks(duck).forEach(other -> {
            if (attacker instanceof PlayerEntity player) {
                other.getBrain().remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
                ((DuckEntity) other).stopTrusting(player);
            }
            runAwayFrom((DuckEntity) other, attacker);
        });
    }

    protected static void runAwayFrom(DuckEntity duck, LivingEntity target) {
        duck.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        duck.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, 160L);
    }

    protected static List<? extends PassiveEntity> getNearbyVisibleDucks(DuckEntity duck) {
        return duck.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).orElse(ImmutableList.of());
    }

    private static boolean noFoodInRange(DuckEntity duck) {
        Optional<ItemEntity> item = duck.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(duck, Birds.ITEM_PICK_UP_RANGE);
    }

    public static boolean isPlayerHoldingFood(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(stack -> getFood().test(stack));
    }

    public static Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.DUCK_FOOD);
    }
}
