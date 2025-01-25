package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.FowlPlayActivities;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
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

public class SparrowBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super SparrowEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_PLAYERS,
        SensorType.NEAREST_ITEMS,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY,
        SensorType.IS_IN_WATER,
        FowlPlaySensorType.NEARBY_LIVING_ENTITIES,
        FowlPlaySensorType.IS_FLYING,
        FowlPlaySensorType.AVOID_TARGETS,
        FowlPlaySensorType.NEARBY_ADULTS
    );
    private static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.MOBS,
        MemoryModuleType.VISIBLE_MOBS,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.PATH,
        MemoryModuleType.NEAREST_VISIBLE_PLAYER,
        MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
        MemoryModuleType.AVOID_TARGET,
        MemoryModuleType.NEAREST_VISIBLE_ADULT,
        MemoryModuleType.GAZE_COOLDOWN_TICKS,
        MemoryModuleType.HURT_BY,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.IS_IN_WATER,
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
    private static final UniformIntProvider STAY_NEAR_ENTITY_RANGE = UniformIntProvider.create(16, 32);
    private static final int PICK_UP_RANGE = 32;
    private static final float RUN_SPEED = 1.4F;
    private static final float WALK_SPEED = 1.0F;
    private static final float FLY_SPEED = 2.0F;

    public static Brain.Profile<SparrowEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<SparrowEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFlyActivities(brain);
        addAvoidActivities(brain);
        addPickupFoodActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void reset(SparrowEntity sparrow) {
        sparrow.getBrain().resetPossibleActivities(
            ImmutableList.of(
                Activity.IDLE,
                FowlPlayActivities.FLY,
                Activity.AVOID,
                FowlPlayActivities.PICKUP_FOOD
            )
        );
    }

    private static void addCoreActivities(Brain<SparrowEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new StayAboveWaterTask(0.5F),
                FlightControlTask.stopFalling(),
                new FleeTask(RUN_SPEED),
                AvoidTask.run(),
                PickupFoodTask.run(Bird::canPickupFood),
                new LookAroundTask(45, 90),
                new WanderAroundTask(),
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<SparrowEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(1, LookAtMobTask.create(SparrowBrain::isPlayerHoldingFood, 32.0F)),
                Pair.of(2, GoToClosestEntityTask.create(STAY_NEAR_ENTITY_RANGE, WALK_SPEED)),
                Pair.of(3, new RandomLookAroundTask(
                    UniformIntProvider.create(150, 250),
                    30.0F,
                    0.0F,
                    0.0F
                )),
                Pair.of(
                    4,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(TaskTriggerer.runIf(
                                entity -> !entity.getWorld().getBlockState(entity.getBlockPos().down()).isIn(FowlPlayBlockTags.PASSERINES_SPAWNABLE_ON),
                                StrollTask.create(WALK_SPEED)
                            ), 4),
                            Pair.of(TaskTriggerer.predicate(Entity::isInsideWaterOrBubbleColumn), 3),
                            Pair.of(new WaitTask(100, 300), 3),
                            Pair.of(FlightControlTask.startFlying(sparrow -> sparrow.getRandom().nextFloat() < 0.1F), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addFlyActivities(Brain<SparrowEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.FLY,
            ImmutableList.of(
                Pair.of(0, FlightControlTask.tryStopFlying(sparrow -> true)),
                Pair.of(1, new LeaderlessFlockTask(
                    0.03f,
                    0.6f,
                    0.05f,
                    3f
                )),
                Pair.of(
                    2,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(FlyTask.perch(FLY_SPEED), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addAvoidActivities(Brain<SparrowEntity> brain) {
        brain.setTaskList(
            Activity.AVOID,
            10,
            ImmutableList.of(
                FlightControlTask.startFlying(sparrow -> true),
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    sparrow -> sparrow.isFlying() ? FLY_SPEED : RUN_SPEED,
                    true
                ),
                makeRandomFollowTask(),
                makeRandomWanderTask(),
                AvoidTask.forget()
            ),
            FowlPlayMemoryModuleType.IS_AVOIDING
        );
    }

    private static void addPickupFoodActivities(Brain<SparrowEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.PICKUP_FOOD,
            ImmutableList.of(
                Pair.of(0, FlightControlTask.startFlying(Bird::canPickupFood)),
                Pair.of(1, GoToNearestWantedItemTask.create(
                    Bird::canPickupFood,
                    entity -> entity.isFlying() ? FLY_SPEED : RUN_SPEED,
                    true,
                    PICK_UP_RANGE
                )),
                Pair.of(2, ForgetTask.create(SparrowBrain::noFoodInRange, FowlPlayMemoryModuleType.SEES_FOOD))
            ),
            Set.of(
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static ImmutableList<Pair<SingleTickTask<LivingEntity>, Integer>> createLookTasks() {
        return ImmutableList.of(
            Pair.of(LookAtMobTask.create(FowlPlayEntityType.SPARROW, 8.0F), 1),
            Pair.of(LookAtMobTask.create(8.0F), 1)
        );
    }

    private static RandomTask<LivingEntity> makeRandomFollowTask() {
        return new RandomTask<>(
            ImmutableList.<Pair<? extends Task<? super LivingEntity>, Integer>>builder()
                .addAll(createLookTasks())
                .add(Pair.of(new WaitTask(30, 60), 1))
                .build()
        );
    }

    private static RandomTask<SparrowEntity> makeRandomWanderTask() {
        return new RandomTask<>(
            ImmutableList.of(
                Pair.of(StrollTask.create(0.6F), 2),
                Pair.of(TaskTriggerer.runIf(
                    livingEntity -> true,
                    GoTowardsLookTargetTask.create(0.6F, 3)
                ), 2),
                Pair.of(new WaitTask(30, 60), 1)
            )
        );
    }

    public static void onAttacked(SparrowEntity sparrow, LivingEntity attacker) {
        if (attacker instanceof SparrowEntity) {
            return;
        }
        Brain<SparrowEntity> brain = sparrow.getBrain();
        brain.forget(FowlPlayMemoryModuleType.SEES_FOOD);
        if (attacker instanceof PlayerEntity) {
            brain.remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
        }
        brain.remember(MemoryModuleType.AVOID_TARGET, attacker, 160L);
        alertOthers(sparrow, attacker);
    }

    protected static void alertOthers(SparrowEntity sparrow, LivingEntity attacker) {
        getNearbyVisibleSparrows(sparrow).forEach(other -> {
            if (attacker instanceof PlayerEntity) {
                other.getBrain().remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
            }
            runAwayFrom((SparrowEntity) other, attacker);
        });
    }

    protected static void runAwayFrom(SparrowEntity sparrow, LivingEntity target) {
        sparrow.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        sparrow.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, 160L);
    }

    protected static List<? extends PassiveEntity> getNearbyVisibleSparrows(SparrowEntity sparrow) {
        return sparrow.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).orElse(ImmutableList.of());
    }

    private static boolean noFoodInRange(SparrowEntity sparrow) {
        Optional<ItemEntity> item = sparrow.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(sparrow, PICK_UP_RANGE);
    }

    public static boolean isPlayerHoldingFood(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(stack -> getFood().test(stack));
    }

    public static Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.SPARROW_FOOD);
    }
}
