package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SparrowEntity extends FlyingBirdEntity implements SmartBrainOwner<SparrowEntity>, Flocking {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    public final AnimationState scratchingState = new AnimationState();
    public final AnimationState preeningState = new AnimationState();
    private int timeSinceLastFlap = this.getFlapFrequency();
    private static final int FLAP_DURATION = 8;
    private int flapTime = 0;

    public SparrowEntity(EntityType<? extends SparrowEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -10.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.4f;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.SPARROW_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.SPARROW_AVOIDS);
    }

    @Override
    public int getFlapFrequency() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
    }

    private boolean isMoving() {
        return this.limbAnimator.isLimbMoving();
    }

    @Override
    protected void updateAnimations() {
        // on land
        if(!this.isFlying() && !this.isInsideWaterOrBubbleColumn()) {
            if(this.random.nextInt(1000) < this.idleAnimationChance++ && !this.isMoving()) {
                this.resetIdleAnimationDelay();
                this.standingState.stop();
                this.preeningState.stop();
                this.scratchingState.stop();
                if(this.getRandom().nextFloat() < 0.75f) {
                    this.preeningState.start(this.age);
                }
                else {
                    this.scratchingState.start(this.age);
                }
            }
            else if(this.isMoving()) {
                this.preeningState.stop();
                this.scratchingState.stop();
            }
            if(!(this.preeningState.isRunning() || this.scratchingState.isRunning())) {
                this.standingState.startIfNotRunning(this.age);
            }
            else {
                this.standingState.stop();
            }
        }
        else {
            this.standingState.stop();
            this.preeningState.stop();
            this.scratchingState.stop();
        }
        // flying
        if(this.isFlying()) {
            if(this.timeSinceLastFlap >= this.getFlapFrequency()) {
                this.timeSinceLastFlap = 0;
                this.flapTime++;
            }
            else if(this.flapTime >= 0 && this.flapTime < FLAP_DURATION) {
                this.flapTime++;
                this.glidingState.stop();
                this.flappingState.startIfNotRunning(this.age);
            }
            else {
                this.timeSinceLastFlap++;
                this.flapTime = 0;
                this.flappingState.stop();
                this.glidingState.startIfNotRunning(this.age);
            }
        }
        else {
            this.timeSinceLastFlap = this.getFlapFrequency();
            this.flapTime = 0;
            this.flappingState.stop();
            this.glidingState.stop();
        }
        // in water
        this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
    }

    @Override
    protected int getIdleAnimationDelay() {
        return 400;
    }

    @Override
    protected boolean isFlappingWings() {
        return this.isFlying() && this.flapTime >= 0 && this.flapTime < FLAP_DURATION;
    }

    @Override
    public float getFlapVolume() {
        return 0.5f;
    }

    @Override
    public float getFlapPitch() {
        return 1.0f;
    }

    @Override
    public float getWaterline() {
        return 0.45F;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_SPARROW_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_SPARROW_SONG.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().sparrowCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().sparrowSongVolume;
    }

    @Override
    public int getCallDelay() {
        return 120;
    }

    @Override
    public int getSongDelay() {
        return 360;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_SPARROW_HURT.get();
    }

    @Override
    protected Brain.Profile<SparrowEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends SparrowEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<SparrowEntity>()
                .setScanRate(bird -> 10),
            new AvoidTargetSensor<SparrowEntity>()
                .setScanRate(bird -> 10)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends SparrowEntity> getCoreTasks() {
        return new BrainActivityGroup<SparrowEntity>(Activity.CORE)
            .priority(0)
            .behaviours(
                new FloatToSurfaceOfFluid<>(),
                FlightTasks.stopFalling(),
                new LookAtTarget<>()
                    .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
                new MoveToWalkTarget<>()
            );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends SparrowEntity> getIdleTasks() {
        return new BrainActivityGroup<SparrowEntity>(Activity.IDLE)
            .priority(10)
            .behaviours(
                new BreedWithPartner<>(),
                new FollowParent<>(),
                SetEntityLookTargetTask.create(Birds::isPlayerHoldingFood),
                new SetRandomLookTarget<>()
                    .lookTime(entity -> entity.getRandom().nextBetween(150, 250)),
                new OneRandomBehaviour<>(
                    Pair.of(
                        TargetlessFlyTask.create(),
                        1
                    )
                ).startCondition(entity -> entity.isFlying() && !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)),
                new OneRandomBehaviour<>(
                    Pair.of(
                        new SetRandomWalkTarget<SparrowEntity>()
                            .setRadius(24, 12)
                            .startCondition(Predicate.not(Birds::isPerched)),
                        4
                    ),
                    Pair.of(
                        new Idle<SparrowEntity>()
                            .runFor(entity -> entity.getRandom().nextBetween(100, 300)),
                        4
                    ),
                    Pair.of(
                        SetWalkTargetToClosestAdult.create(Birds.STAY_NEAR_ENTITY_RANGE),
                        1
                    )
                ).startCondition(entity -> !entity.isFlying() && !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends SparrowEntity> getForageTasks() {
        return new BrainActivityGroup<SparrowEntity>(FowlPlayActivities.FORAGE.get())
            .priority(10)
            .behaviours(
                new SetRandomWalkTarget<SparrowEntity>()
                    .setRadius(32, 16),
                new Idle<SparrowEntity>()
                    .runFor(entity -> entity.getRandom().nextBetween(100, 300))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends SparrowEntity> getPerchTasks() {
        return new BrainActivityGroup<SparrowEntity>(FowlPlayActivities.PERCH.get())
            .priority(10)
            .behaviours(
                new LeaderlessFlockTask(
                    3,
                    0.03f,
                    0.6f,
                    0.05f,
                    3f
                ),
                TargetlessFlyTask.perch()
                    .startCondition(entity -> !Birds.isPerched(entity) && !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)),
                new OneRandomBehaviour<>(
                    Pair.of(
                        new Idle<SparrowEntity>()
                            .runFor(entity -> entity.getRandom().nextBetween(300, 1000)),
                        8
                    ),
                    Pair.of(
                        TargetlessFlyTask.perch(),
                        1
                    )
                ).startCondition(Birds::isPerched)
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends SparrowEntity> getAvoidTasks() {
        return new BrainActivityGroup<SparrowEntity>(Activity.AVOID)
            .priority(10)
            .behaviours(
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    entity -> Birds.FAST_SPEED,
                    true
                )
            )
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.IS_AVOIDING.get());
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends SparrowEntity> getPickupFoodTasks() {
        return new BrainActivityGroup<SparrowEntity>(FowlPlayActivities.PICK_UP.get())
            .priority(10)
            .behaviours(
                GoToNearestItemTask.create(
                    Birds::canPickupFood,
                    entity -> Birds.FAST_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                )
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @Override
    public Map<Activity, BrainActivityGroup<? extends SparrowEntity>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends SparrowEntity>> taskList = new Object2ObjectOpenHashMap<>();
        taskList.put(FowlPlayActivities.FORAGE.get(), this.getForageTasks());
        taskList.put(FowlPlayActivities.PERCH.get(), this.getPerchTasks());
        taskList.put(Activity.AVOID, this.getAvoidTasks());
        taskList.put(FowlPlayActivities.PICK_UP.get(), this.getPickupFoodTasks());
        return taskList;
    }

    @Override
    public List<Activity> getActivityPriorities() {
        return ObjectArrayList.of(
            Activity.AVOID,
            FowlPlayActivities.PICK_UP.get(),
            FowlPlayActivities.PERCH.get(),
            FowlPlayActivities.FORAGE.get(),
            Activity.IDLE
        );
    }

    @Override
    protected void mobTick() {
        this.tickBrain(this);
        super.mobTick();
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }
}
