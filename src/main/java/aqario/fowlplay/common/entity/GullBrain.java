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

public class GullBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super GullEntity>>> SENSORS = ImmutableList.of(
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
    private static final UniformIntProvider FOLLOW_ADULT_RANGE = UniformIntProvider.create(5, 16);
    private static final UniformIntProvider STAY_NEAR_ENTITY_RANGE = UniformIntProvider.create(16, 32);
    private static final int PICK_UP_RANGE = 32;
    private static final float RUN_SPEED = 1.4F;
    private static final float WALK_SPEED = 1.0F;
    private static final float FLY_SPEED = 2.0F;

    public static Brain.Profile<GullEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<GullEntity> brain) {
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

    public static void reset(GullEntity gull) {
        Brain<GullEntity> brain = gull.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        brain.resetPossibleActivities(
            ImmutableList.of(
                Activity.IDLE,
                FowlPlayActivities.FLY,
                Activity.AVOID,
                FowlPlayActivities.PICKUP_FOOD,
                Activity.FIGHT
            )
        );
        if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
    }

    private static void addCoreActivities(Brain<GullEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                FlightControlTask.stopFalling(),
                new FleeTask<>(RUN_SPEED),
                AvoidTask.run(),
                PickupFoodTask.run(Bird::canPickupFood),
                new LookAroundTask(45, 90),
                new MoveToTargetTask(),
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<GullEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(1, new BreedTask(FowlPlayEntityType.GULL, WALK_SPEED, 20)),
                Pair.of(2, WalkTowardClosestAdultTask.create(FOLLOW_ADULT_RANGE, WALK_SPEED)),
                Pair.of(3, LookAtMobTask.create(GullBrain::isPlayerHoldingFood, 32.0F)),
                Pair.of(4, UpdateAttackTargetTask.create(gull -> !gull.isInsideWaterOrBubbleColumn(), GullBrain::getAttackTarget)),
                Pair.of(5, GoToClosestEntityTask.create(STAY_NEAR_ENTITY_RANGE, WALK_SPEED)),
                Pair.of(6, new RandomLookAroundTask(
                    UniformIntProvider.create(150, 250),
                    30.0F,
                    0.0F,
                    0.0F
                )),
                Pair.of(
                    7,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(TaskTriggerer.runIf(
                                entity -> !entity.getWorld().getBlockState(entity.getBlockPos().down()).isIn(FowlPlayBlockTags.PASSERINES_SPAWNABLE_ON),
                                StrollTask.create(WALK_SPEED)
                            ), 4),
                            Pair.of(TaskTriggerer.predicate(Entity::isInsideWaterOrBubbleColumn), 3),
                            Pair.of(new WaitTask(100, 300), 3),
                            Pair.of(FlightControlTask.startFlying(gull -> gull.getRandom().nextFloat() < 0.1F), 1)
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

    private static void addFlyActivities(Brain<GullEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.FLY,
            ImmutableList.of(
                Pair.of(1, FlightControlTask.tryStopFlying(gull -> true)),
                Pair.of(2, UpdateAttackTargetTask.create(GullBrain::getAttackTarget)),
                Pair.of(3, GoToClosestEntityTask.create(STAY_NEAR_ENTITY_RANGE, FLY_SPEED)),
                Pair.of(
                    4,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(FlyTask.create(FLY_SPEED, 24, 16), 1)
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

    private static void addAvoidActivities(Brain<GullEntity> brain) {
        brain.setTaskList(
            Activity.AVOID,
            10,
            ImmutableList.of(
                FlightControlTask.startFlying(gull -> true),
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    gull -> gull.isFlying() ? FLY_SPEED : RUN_SPEED,
                    true
                ),
                makeRandomFollowTask(),
                makeRandomWanderTask(),
                AvoidTask.forget()
            ),
            FowlPlayMemoryModuleType.IS_AVOIDING
        );
    }

    private static void addPickupFoodActivities(Brain<GullEntity> brain) {
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
                Pair.of(2, ForgetTask.create(GullBrain::noFoodInRange, FowlPlayMemoryModuleType.SEES_FOOD))
            ),
            Set.of(
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addFightActivities(Brain<GullEntity> brain) {
        brain.setTaskList(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                FlightControlTask.startFlying(gull -> true),
                ForgetAttackTargetTask.create(),
                RangedApproachTask.create(FLY_SPEED),
                MeleeAttackTask.create(20),
                ForgetTask.create(LookTargetUtil::hasBreedTarget, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    private static Optional<? extends LivingEntity> getAttackTarget(GullEntity gull) {
        return LookTargetUtil.hasBreedTarget(gull) ? Optional.empty() : gull.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static ImmutableList<Pair<SingleTickTask<LivingEntity>, Integer>> createLookTasks() {
        return ImmutableList.of(
            Pair.of(LookAtMobTask.create(FowlPlayEntityType.GULL, 8.0F), 1),
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

    private static RandomTask<GullEntity> makeRandomWanderTask() {
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

    public static void onAttacked(GullEntity gull, LivingEntity attacker) {
        if (attacker instanceof GullEntity) {
            return;
        }
        Brain<GullEntity> brain = gull.getBrain();
        brain.forget(FowlPlayMemoryModuleType.SEES_FOOD);
        if (attacker instanceof PlayerEntity player) {
            brain.remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
            gull.stopTrusting(player);
        }
        brain.remember(MemoryModuleType.AVOID_TARGET, attacker, 160L);
        alertOthers(gull, attacker);
    }

    protected static void alertOthers(GullEntity gull, LivingEntity attacker) {
        getNearbyVisibleGulls(gull).forEach(other -> {
            if (attacker instanceof PlayerEntity player) {
                other.getBrain().remember(FowlPlayMemoryModuleType.CANNOT_PICKUP_FOOD, true, 1200L);
                ((GullEntity) other).stopTrusting(player);
            }
            runAwayFrom((GullEntity) other, attacker);
        });
    }

    protected static void runAwayFrom(GullEntity gull, LivingEntity target) {
        gull.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        gull.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, 160L);
    }

    protected static List<? extends PassiveEntity> getNearbyVisibleGulls(GullEntity gull) {
        return gull.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).orElse(ImmutableList.of());
    }

    private static boolean noFoodInRange(GullEntity gull) {
        Optional<ItemEntity> item = gull.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(gull, PICK_UP_RANGE);
    }

    public static boolean isPlayerHoldingFood(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(stack -> getFood().test(stack));
    }

    public static Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.GULL_FOOD);
    }
}
