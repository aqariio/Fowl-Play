package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackTargetSensor;
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
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Panic;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
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
import java.util.Optional;
import java.util.function.Predicate;

public class CrowEntity extends TrustingBirdEntity implements SmartBrainOwner<CrowEntity> {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public CrowEntity(EntityType<? extends CrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.55f;
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createCrowAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.22f);
    }

    @Override
    public int getMaxYawChange() {
        return 18;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.CROW_FOOD);
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.hasLowHealth()) {
            return false;
        }
        Optional<LivingEntity> hurtBy = Optional.ofNullable(BrainUtils.getMemory(this, MemoryModuleType.HURT_BY_ENTITY));
        if(!target.getType().isIn(FowlPlayEntityTypeTags.CROW_ATTACK_TARGETS) && (hurtBy.isEmpty() || !hurtBy.get().equals(target))) {
            return false;
        }
        Optional<List<? extends PassiveEntity>> nearbyAdults = Optional.ofNullable(BrainUtils.getMemory(this, FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get()));
        return nearbyAdults.filter(passiveEntities -> passiveEntities.size() >= 4).isPresent();
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.CROW_AVOIDS);
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
        return 0.5F;
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_CROW_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().crowCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 600;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_CROW_HURT.get();
    }

    @Override
    protected Brain.Profile<CrowEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends CrowEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyItemsSensor<>(),
            new NearbyAdultSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<CrowEntity>()
                .setScanRate(bird -> 10),
            new AvoidTargetSensor<>(),
            new AttackTargetSensor<>()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends CrowEntity> getCoreTasks() {
        return new BrainActivityGroup<CrowEntity>(Activity.CORE)
            .priority(0)
            .behaviours(
                new FloatToSurfaceOfFluid<>()
                    .riseChance(0.5F),
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
    public BrainActivityGroup<? extends CrowEntity> getIdleTasks() {
        return new BrainActivityGroup<CrowEntity>(Activity.IDLE)
            .priority(10)
            .behaviours(
                new BreedWithPartner<>(),
                new FollowParent<>(),
                FindLookTargetTask.create(Birds::isPlayerHoldingFood, 32.0F),
                new SetAttackTarget<CrowEntity>()
                    .attackPredicate(Birds::canAttack),
                SetWalkTargetToClosestAdult.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.WALK_SPEED),
                new SetRandomLookTarget<>()
                    .lookTime(entity -> entity.getRandom().nextBetween(150, 250)),
                new OneRandomBehaviour<>(
                    Pair.of(
                        new SetRandomWalkTarget<CrowEntity>()
                            .speedModifier((entity, target) -> Birds.WALK_SPEED)
                            .setRadius(16, 8)
                            .startCondition(Predicate.not(Birds::isPerched)),
                        4
                    ),
                    Pair.of(
                        new Idle<CrowEntity>()
                            .runFor(entity -> entity.getRandom().nextBetween(100, 300)),
                        4
                    ),
                    Pair.of(
                        FlightControlTask.startFlying()
                            .startCondition(entity -> entity.isInsideWaterOrBubbleColumn() || entity.getRandom().nextFloat() < 0.3F),
                        1
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends CrowEntity> getFlyTasks() {
        return new BrainActivityGroup<CrowEntity>(FowlPlayActivities.FLY.get())
            .priority(10)
            .behaviours(
                new SetAttackTarget<CrowEntity>()
                    .attackPredicate(Birds::canAttack),
                SetWalkTargetToClosestAdult.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.FLY_SPEED),
                new OneRandomBehaviour<>(
                    Pair.of(
                        TargetlessFlyTask.perch(Birds.FLY_SPEED),
                        1
                    ),
                    Pair.of(
                        TargetlessFlyTask.create(Birds.FLY_SPEED, 24, 16),
                        1
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends CrowEntity> getAvoidTasks() {
        return new BrainActivityGroup<CrowEntity>(Activity.AVOID)
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
    public BrainActivityGroup<? extends CrowEntity> getPickupFoodTasks() {
        return new BrainActivityGroup<CrowEntity>(FowlPlayActivities.PICK_UP.get())
            .priority(10)
            .behaviours(
                FlightControlTask.startFlying(Birds::canPickupFood),
                GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                ),
                new InvalidateMemory<CrowEntity, Boolean>(FowlPlayMemoryModuleType.SEES_FOOD.get())
                    .invalidateIf((entity, memory) -> !Birds.canPickupFood(entity))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends CrowEntity> getFightTasks() {
        return new BrainActivityGroup<CrowEntity>(Activity.FIGHT)
            .priority(10)
            .behaviours(
                new InvalidateAttackTarget<>(),
                FlightControlTask.startFlying(),
                new SetWalkTargetToAttackTarget<>()
                    .speedMod((entity, target) -> Birds.FLY_SPEED),
                new AnimatableMeleeAttack<>(0),
                new InvalidateMemory<CrowEntity, LivingEntity>(MemoryModuleType.ATTACK_TARGET)
                    .invalidateIf((entity, memory) -> LookTargetUtil.hasBreedTarget(entity))
            )
            .requireAndWipeMemoriesOnUse(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    public Map<Activity, BrainActivityGroup<? extends CrowEntity>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends CrowEntity>> taskList = new Object2ObjectOpenHashMap<>();
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
            FowlPlayActivities.PICK_UP.get(),
            Activity.FIGHT
        );
    }

    @Override
    protected void mobTick() {
        Brain<?> brain = this.getBrain();
        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
        this.tickBrain(this);
        if(activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
        super.mobTick();
    }
}
