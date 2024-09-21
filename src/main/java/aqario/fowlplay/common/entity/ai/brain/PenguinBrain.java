package aqario.fowlplay.common.entity.ai.brain;

import aqario.fowlplay.common.entity.FowlPlayEntityType;
import aqario.fowlplay.common.entity.PenguinEntity;
import aqario.fowlplay.common.entity.ai.brain.sensor.FowlPlaySensorType;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.unmapped.C_lygsomtd;
import net.minecraft.util.math.int_provider.UniformIntProvider;
import net.minecraft.util.random.RandomGenerator;

public class PenguinBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super PenguinEntity>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY,
        FowlPlaySensorType.PENGUIN_TEMPTATIONS,
        SensorType.IS_IN_WATER
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
        MemoryModuleType.IS_PANICKING
    );
    private static final UniformIntProvider FOLLOW_ADULT_RANGE = UniformIntProvider.create(5, 16);
    private static final float PANICKING_SPEED = 1.5F;
    private static final float TEMPTED_SPEED = 0.8F;
    private static final float WALK_SPEED = 1.0F;
    private static final float SWIM_SPEED = 4.0F;

    public static void initialize(PenguinEntity penguin, RandomGenerator random) {
    }

    public static Brain.Profile<PenguinEntity> createProfile() {
        return Brain.createProfile(MEMORIES, SENSORS);
    }

    public static Brain<?> create(Brain<PenguinEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.CORE,
            0,
            ImmutableList.of(
                new WalkTask<>(PANICKING_SPEED),
                new LookAroundTask(45, 90),
                new WanderAroundTask(),
                new ReduceCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new ReduceCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<PenguinEntity> brain) {
        brain.setTaskList(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, C_lygsomtd.method_47069(EntityType.PLAYER, 16.0f, UniformIntProvider.create(30, 60))),
                Pair.of(1, new BreedTask(FowlPlayEntityType.PENGUIN, WALK_SPEED, 20)),
                Pair.of(2, new TemptTask(penguin -> TEMPTED_SPEED)),
                Pair.of(3, WalkTowardClosestAdultTask.create(FOLLOW_ADULT_RANGE, WALK_SPEED)),
                Pair.of(4, new RandomLookAroundTask(UniformIntProvider.create(150, 250), 30.0F, 0.0F, 0.0F)),
                Pair.of(
                    5,
                    new RandomTask<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
                        ImmutableList.of(
                            Pair.of(MeanderTask.create(WALK_SPEED), 1),
                            Pair.of(GoTowardsLookTarget.create(WALK_SPEED, 3), 2),
                            Pair.of(TaskBuilder.triggerIf(Entity::isInsideWaterOrBubbleColumn), 2),
                            Pair.of(new PenguinBrain.RandomSlideTask(20), 1),
                            Pair.of(new WaitTask(400, 800), 2)
                        )
                    )
                )
            ),
            ImmutableSet.of(
                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)
            )
        );
    }

    public static void reset(PenguinEntity penguin) {
        penguin.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static Ingredient getTemptIngredient() {
        return Ingredient.ofTag(FowlPlayItemTags.PENGUIN_FOOD);
    }

    public static class RandomSlideTask extends Task<PenguinEntity> {
        private final int minimalSlideTicks;

        public RandomSlideTask(int seconds) {
            super(ImmutableMap.of());
            this.minimalSlideTicks = seconds * 20;
        }

        @Override
        protected boolean shouldRun(ServerWorld world, PenguinEntity penguin) {
            return !penguin.isTouchingWater()
                && penguin.getAnimationTicks() >= (long) this.minimalSlideTicks
                && !penguin.hasPassengers()
                && penguin.isOnGround()
                && (penguin.isSliding() || world.getBlockState(penguin.getBlockPos()).isIn(FowlPlayBlockTags.PENGUINS_SLIDE_ON));
        }

        @Override
        protected void run(ServerWorld serverWorld, PenguinEntity penguin, long l) {
            if (penguin.isSliding()) {
                penguin.standUp();
            }
            else {
                penguin.startSliding();
            }
        }
    }
}
