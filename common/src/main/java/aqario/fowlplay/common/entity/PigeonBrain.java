package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.brain.FowlPlayActivities;
import aqario.fowlplay.common.entity.ai.brain.FowlPlayMemoryModuleType;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.int_provider.UniformIntProvider;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PigeonBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super PigeonEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        SensorType.NEAREST_PLAYERS,
        SensorType.NEAREST_ITEMS,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY,
        SensorType.IS_IN_WATER,
        FowlPlaySensorType.IS_FLYING,
        FowlPlaySensorType.NEAREST_ADULTS,
        FowlPlaySensorType.PIGEON_TEMPTATIONS
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
        FowlPlayMemoryModuleType.IS_FLYING,
        FowlPlayMemoryModuleType.SEES_FOOD,
        FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD,
        FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS,
        FowlPlayMemoryModuleType.TELEPORT_TARGET
    );
    private static final UniformIntProvider RUN_FROM_PLAYER_MEMORY_DURATION = TimeHelper.betweenSeconds(1, 3);
    private static final UniformIntProvider FOLLOW_ADULT_RANGE = UniformIntProvider.create(5, 16);
    private static final UniformIntProvider STAY_NEAR_ENTITY_RANGE = UniformIntProvider.create(16, 32);
    private static final int PICK_UP_RANGE = 32;
    private static final int AVOID_PLAYER_RADIUS = 5;
    private static final float RUN_SPEED = 1.4F;
    private static final float WALK_SPEED = 1.0F;
    private static final float FLY_SPEED = 2.0F;

    public static void init() {
    }

    public static Brain.Profile<PigeonEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<PigeonEntity> brain) {
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

    public static void reset(PigeonEntity pigeon) {
        pigeon.getBrain().resetPossibleActivities(
            ImmutableList.of(
                Activity.IDLE,
                FowlPlayActivities.FLY,
                Activity.AVOID,
                FowlPlayActivities.PICKUP_FOOD
            )
        );
    }

    private static void addCoreActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new StayAboveWaterTask(0.5F),
                FlightTaskControl.stopFalling(),
                new TeleportToTargetTask(),
                new WalkTask<>(RUN_SPEED),
                new DelivererFollowOwnerTask(WALK_SPEED, 5, 10),
                new DeliverBundleTask(WALK_SPEED, 4, 1024),
                makeAddPlayerToAvoidTargetTask(),
                LocateFoodTask.run(),
                new LookAroundTask(45, 90),
                new WanderAroundTask(),
                new ReduceCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new ReduceCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(1, new BreedTask(FowlPlayEntityType.PIGEON, WALK_SPEED, 20)),
                Pair.of(2, WalkTowardClosestAdultTask.create(FOLLOW_ADULT_RANGE, WALK_SPEED)),
                Pair.of(3, FollowMobTask.create(PigeonBrain::isPlayerHoldingFood, 32.0F)),
                Pair.of(4, StayNearClosestEntityTask.create(STAY_NEAR_ENTITY_RANGE, WALK_SPEED)),
                Pair.of(5, new RandomLookAroundTask(
                    UniformIntProvider.create(150, 250),
                    30.0F,
                    0.0F,
                    0.0F
                )),
                Pair.of(
                    6,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(MeanderTask.create(WALK_SPEED), 4),
                            Pair.of(TaskBuilder.triggerIf(Entity::isInsideWaterOrBubbleColumn), 3),
                            Pair.of(new WaitTask(100, 300), 3),
                            Pair.of(FlightTaskControl.startFlying(pigeon -> pigeon.getRandom().nextFloat() < 0.1F), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addFlyActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.FLY,
            ImmutableList.of(
                Pair.of(1, FlightTaskControl.stopFlying(pigeon -> true)),
                Pair.of(2, StayNearClosestEntityTask.create(STAY_NEAR_ENTITY_RANGE, FLY_SPEED)),
                Pair.of(
                    3,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(FlyTask.create(FLY_SPEED, 64, 32), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_PRESENT),
                Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addAvoidActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            Activity.AVOID,
            10,
            ImmutableList.of(
                FlightTaskControl.startFlying(pigeon -> true),
                BetterGoToRememberedPositionTask.toEntity(
                    MemoryModuleType.AVOID_TARGET,
                    pigeon -> pigeon.isFlying() ? FLY_SPEED : RUN_SPEED,
                    AVOID_PLAYER_RADIUS,
                    true
                ),
                makeRandomFollowTask(),
                makeRandomWanderTask(),
                ForgetTask.run(entity -> true, MemoryModuleType.AVOID_TARGET)
            ),
            MemoryModuleType.AVOID_TARGET
        );
    }

    private static void addPickupFoodActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.PICKUP_FOOD,
            ImmutableList.of(
                Pair.of(0, FlightTaskControl.startFlying(pigeon -> true)),
                Pair.of(1, BetterWalkToNearestWantedItemTask.create(
                    PigeonBrain::doesNotHaveFoodInHand,
                    entity -> entity.isFlying() ? FLY_SPEED : RUN_SPEED,
                    true,
                    PICK_UP_RANGE
                )),
                Pair.of(2, ForgetTask.run(PigeonBrain::noFoodInRange, FowlPlayMemoryModuleType.SEES_FOOD))
            ),
            Set.of(
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_PRESENT),
                Pair.of(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static ImmutableList<Pair<ReportingTaskControl<LivingEntity>, Integer>> createLookTasks() {
        return ImmutableList.of(
            Pair.of(FollowMobTask.createMatchingType(FowlPlayEntityType.PIGEON, 8.0F), 1),
            Pair.of(FollowMobTask.create(8.0F), 1)
        );
    }

    private static RandomTask<LivingEntity> makeRandomFollowTask() {
        return new RandomTask<>(
            ImmutableList.<Pair<? extends TaskControl<? super LivingEntity>, Integer>>builder()
                .addAll(createLookTasks())
                .add(Pair.of(new WaitTask(30, 60), 1))
                .build()
        );
    }

    private static RandomTask<PigeonEntity> makeRandomWanderTask() {
        return new RandomTask<>(
            ImmutableList.of(
                Pair.of(MeanderTask.create(0.6F), 2),
                Pair.of(TaskBuilder.sequence(
                    livingEntity -> true,
                    GoTowardsLookTarget.create(0.6F, 3)
                ), 2),
                Pair.of(new WaitTask(30, 60), 1)
            )
        );
    }

    private static TaskControl<PigeonEntity> makeAddPlayerToAvoidTargetTask() {
        return MemoryTransferTask.create(
            PigeonBrain::hasAvoidTarget, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.AVOID_TARGET, RUN_FROM_PLAYER_MEMORY_DURATION
        );
    }

    private static boolean hasAvoidTarget(PigeonEntity pigeon) {
        if (pigeon.isTamed()) {
            return false;
        }
        Brain<PigeonEntity> brain = pigeon.getBrain();
        if (!brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_PLAYER)) {
            return false;
        }
        PlayerEntity player = brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).get();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(player) || pigeon.isTrusted(player)) {
            return false;
        }

        return pigeon.isInRange(player, AVOID_PLAYER_RADIUS);
    }

    public static void onAttacked(PigeonEntity pigeon, LivingEntity attacker) {
        if (attacker instanceof PigeonEntity || attacker == pigeon.getOwner()) {
            return;
        }
        Brain<PigeonEntity> brain = pigeon.getBrain();
        brain.forget(FowlPlayMemoryModuleType.SEES_FOOD);
        if (attacker instanceof PlayerEntity player) {
            brain.remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
            pigeon.stopTrusting(player);
        }
        brain.remember(MemoryModuleType.AVOID_TARGET, attacker, 160L);
        alertOthers(pigeon, attacker);
    }

    protected static void alertOthers(PigeonEntity pigeon, LivingEntity attacker) {
        getNearbyVisiblePigeons(pigeon).forEach(other -> {
            if (attacker instanceof PlayerEntity player) {
                other.getBrain().remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
                ((PigeonEntity) other).stopTrusting(player);
            }
            runAwayFrom((PigeonEntity) other, attacker);
        });
    }

    protected static void runAwayFrom(PigeonEntity pigeon, LivingEntity target) {
        pigeon.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        pigeon.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, 160L);
    }

    protected static List<PassiveEntity> getNearbyVisiblePigeons(PigeonEntity pigeon) {
        return pigeon.getBrain().getOptionalMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).orElse(ImmutableList.of());
    }

    private static boolean noFoodInRange(PigeonEntity pigeon) {
        Optional<ItemEntity> item = pigeon.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(pigeon, PICK_UP_RANGE);
    }

    public static boolean isPlayerHoldingFood(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(stack -> getFood().test(stack));
    }

    private static boolean doesNotHaveFoodInHand(PigeonEntity pigeon) {
        return !getFood().test(pigeon.getMainHandStack());
    }

    public static Ingredient getFood() {
        return Ingredient.ofTag(FowlPlayItemTags.PIGEON_FOOD);
    }
}
