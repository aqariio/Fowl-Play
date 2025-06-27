package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayActivities;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Panic;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyItemsSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyAdultSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class BlueJayEntity extends FlyingBirdEntity implements SmartBrainOwner<BlueJayEntity> {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public BlueJayEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.475f;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.BLUE_JAY_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.BLUE_JAY_AVOIDS);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public int getFlapFrequency() {
        return 7;
    }

    @Override
    public void tick() {
        if(this.getWorld().isClient()) {
            this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.flappingState.setRunning(this.isFlying(), this.age);
            this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
        }

        super.tick();
    }

    @Override
    public float getWaterline() {
        return 0.45F;
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_BLUE_JAY_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().blueJayCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 480;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_BLUE_JAY_HURT.get();
    }

    @Override
    protected Brain.Profile<BlueJayEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends BlueJayEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyItemsSensor<>(),
            new NearbyAdultSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<BlueJayEntity>()
                .setScanRate(bird -> 10),
            new AvoidTargetSensor<>()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getCoreTasks() {
        return new BrainActivityGroup<BlueJayEntity>(Activity.CORE)
            .priority(0)
            .behaviours(
                new FloatToSurfaceOfFluid<>(),
                FlightControlTask.stopFalling(),
                new Panic<>(),
                AvoidTask.run(),
                PickupFoodTask.run(Birds::canPickupFood),
                new LookAtTarget<>()
                    .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
                new MoveToWalkTarget<>()
            );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getIdleTasks() {
        return new BrainActivityGroup<BlueJayEntity>(Activity.IDLE)
            .priority(10)
            .behaviours(
                new BreedWithPartner<>(),
                new FollowParent<>(),
                FindLookTargetTask.create(Birds::isPlayerHoldingFood, 32.0F),
                SetWalkTargetToClosestAdult.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.WALK_SPEED),
                new SetRandomLookTarget<>()
                    .lookTime(entity -> entity.getRandom().nextBetween(150, 250)),
                new OneRandomBehaviour<>(
                    Pair.of(
                        new SetRandomWalkTarget<BlueJayEntity>()
                            .speedModifier((entity, target) -> Birds.WALK_SPEED)
                            .setRadius(16, 8)
                            .startCondition(Predicate.not(Birds::isPerched)),
                        4
                    ),
                    Pair.of(
                        new Idle<BlueJayEntity>()
                            .runFor(entity -> entity.getRandom().nextBetween(100, 300)),
                        4
                    ),
                    Pair.of(
                        FlightControlTask.startFlying(entity -> entity.getRandom().nextFloat() < 0.3F),
                        1
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends BlueJayEntity> getFlyTasks() {
        return new BrainActivityGroup<BlueJayEntity>(FowlPlayActivities.FLY.get())
            .priority(10)
            .behaviours(
                SetWalkTargetToClosestAdult.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.FLY_SPEED),
                new OneRandomBehaviour<>(
                    Pair.of(
                        TargetlessFlyTask.perch(Birds.FLY_SPEED),
                        1
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends BlueJayEntity> getAvoidTasks() {
        return new BrainActivityGroup<BlueJayEntity>(Activity.AVOID)
            .priority(10)
            .behaviours(
                FlightControlTask.startFlying(),
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true
                ),
                AvoidTask.forget()
            )
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.IS_AVOIDING.get());
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends BlueJayEntity> getPickupFoodTasks() {
        return new BrainActivityGroup<BlueJayEntity>(FowlPlayActivities.PICK_UP.get())
            .priority(10)
            .behaviours(
                FlightControlTask.startFlying(Birds::canPickupFood),
                GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                ),
                new InvalidateMemory<BlueJayEntity, Boolean>(FowlPlayMemoryModuleType.SEES_FOOD.get())
                    .invalidateIf((entity, memory) -> !Birds.canPickupFood(entity))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @Override
    public Map<Activity, BrainActivityGroup<? extends BlueJayEntity>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends BlueJayEntity>> taskList = new Object2ObjectOpenHashMap<>();
        taskList.put(FowlPlayActivities.FLY.get(), this.getFlyTasks());
        taskList.put(Activity.AVOID, this.getAvoidTasks());
        taskList.put(FowlPlayActivities.PICK_UP.get(), this.getPickupFoodTasks());
        return taskList;
    }

    @Override
    public List<Activity> getActivityPriorities() {
        return ObjectArrayList.of(
            Activity.IDLE,
            FowlPlayActivities.FLY.get(),
            Activity.AVOID,
            FowlPlayActivities.PICK_UP.get()
        );
    }

    @Override
    protected void mobTick() {
        this.tickBrain(this);
        super.mobTick();
    }
}
