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
import java.util.UUID;
import java.util.function.Predicate;

public class PigeonBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super PigeonEntity>>> SENSORS = ImmutableList.of(
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
        FowlPlaySensorType.PIGEON_SPECIFIC_SENSOR
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
        FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS,
        FowlPlayMemoryModuleType.TELEPORT_TARGET,
        FowlPlayMemoryModuleType.RECIPIENT
    );

    public static Brain.Profile<PigeonEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<PigeonEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFlyActivities(brain);
        addDeliverActivities(brain);
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
                FowlPlayActivities.DELIVER,
                Activity.AVOID,
                FowlPlayActivities.PICK_UP
            )
        );
    }

    private static void addCoreActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new StayAboveWaterTask(0.5F),
                FlightControlTask.stopFalling(),
                new TeleportToTargetTask(),
                new FleeTask<>(Birds.RUN_SPEED),
                new FollowOwnerTask(Birds.WALK_SPEED, 5, 10),
                AvoidTask.run(),
                PickupFoodTask.run(pigeon -> !pigeon.isSitting() && pigeon.getRecipientUuid() == null),
                new LookAroundTask(45, 90),
                new WalkToTargetTask(),
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(1, new BreedTask(FowlPlayEntityType.PIGEON, Birds.WALK_SPEED, 20)),
                Pair.of(2, WalkTowardClosestAdultTask.create(Birds.FOLLOW_ADULT_RANGE, Birds.WALK_SPEED)),
                Pair.of(3, LookAtMobTask.create(PigeonBrain::isPlayerHoldingFood, 32.0F)),
                Pair.of(4, GoToClosestEntityTask.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.WALK_SPEED)),
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
                            Pair.of(TaskTriggerer.runIf(
                                Predicate.not(Birds::isPerched),
                                StrollTask.create(Birds.WALK_SPEED)
                            ), 4),
                            Pair.of(TaskTriggerer.predicate(Entity::isInsideWaterOrBubbleColumn), 3),
                            Pair.of(new WaitTask(100, 300), 3),
                            Pair.of(FlightControlTask.startFlying(pigeon -> !pigeon.isSitting() && pigeon.getRandom().nextFloat() < 0.1F), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.RECIPIENT, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addFlyActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.FLY,
            ImmutableList.of(
                Pair.of(1, FlightControlTask.tryStopFlying(pigeon -> true)),
                Pair.of(2, GoToClosestEntityTask.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.FLY_SPEED)),
                Pair.of(
                    3,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(TargetlessFlyTask.perch(Birds.FLY_SPEED), 1)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(FowlPlayMemoryModuleType.IS_FLYING, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.RECIPIENT, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    private static void addDeliverActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.DELIVER,
            0,
            ImmutableList.of(
                FlightControlTask.stopFlying(PigeonBrain::shouldStopFlyingToRecipient),
                FlightControlTask.startFlying(PigeonBrain::shouldFlyToRecipient),
                DeliverBundleTask.run(pigeon -> true, pigeon -> pigeon.isFlying() ? Birds.FLY_SPEED : Birds.WALK_SPEED)
            ),
            FowlPlayMemoryModuleType.RECIPIENT
        );
    }

    private static void addAvoidActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            Activity.AVOID,
            10,
            ImmutableList.of(
                FlightControlTask.startFlying(pigeon -> true),
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    pigeon -> pigeon.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true
                ),
                CompositeTasks.makeRandomFollowTask(FowlPlayEntityType.PIGEON),
                CompositeTasks.makeRandomWanderTask(),
                AvoidTask.forget()
            ),
            FowlPlayMemoryModuleType.IS_AVOIDING
        );
    }

    private static void addPickupFoodActivities(Brain<PigeonEntity> brain) {
        brain.setTaskList(
            FowlPlayActivities.PICK_UP,
            ImmutableList.of(
                Pair.of(0, FlightControlTask.startFlying(pigeon -> !pigeon.isTamed() && Birds.canPickupFood(pigeon))),
                Pair.of(1, GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                )),
                Pair.of(2, ForgetTask.create(PigeonBrain::noFoodInRange, FowlPlayMemoryModuleType.SEES_FOOD))
            ),
            Set.of(
                Pair.of(FowlPlayMemoryModuleType.SEES_FOOD, MemoryModuleState.VALUE_PRESENT),
                Pair.of(FowlPlayMemoryModuleType.IS_AVOIDING, MemoryModuleState.VALUE_ABSENT),
                Pair.of(FowlPlayMemoryModuleType.RECIPIENT, MemoryModuleState.VALUE_ABSENT)
            )
        );
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

    protected static List<? extends PassiveEntity> getNearbyVisiblePigeons(PigeonEntity pigeon) {
        return pigeon.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS).orElse(ImmutableList.of());
    }

    private static boolean noFoodInRange(PigeonEntity pigeon) {
        Optional<ItemEntity> item = pigeon.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        return item.isEmpty() || !item.get().isInRange(pigeon, Birds.ITEM_PICK_UP_RANGE);
    }

    public static boolean isPlayerHoldingFood(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(stack -> getFood().test(stack));
    }

    private static boolean shouldFlyToRecipient(PigeonEntity pigeon) {
        UUID recipientUuid = pigeon.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.RECIPIENT).orElse(null);
        if (recipientUuid == null) {
            return false;
        }
        PlayerEntity recipient = pigeon.getWorld().getPlayerByUuid(recipientUuid);
        if (recipient == null) {
            return false;
        }
        return pigeon.squaredDistanceTo(recipient) > 64;
    }

    private static boolean shouldStopFlyingToRecipient(PigeonEntity pigeon) {
        UUID recipientUuid = pigeon.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.RECIPIENT).orElse(null);
        if (recipientUuid == null) {
            return true;
        }
        PlayerEntity recipient = pigeon.getWorld().getPlayerByUuid(recipientUuid);
        if (recipient == null) {
            return true;
        }
        return pigeon.squaredDistanceTo(recipient) < 16;
    }

    public static Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.PIGEON_FOOD);
    }
}
