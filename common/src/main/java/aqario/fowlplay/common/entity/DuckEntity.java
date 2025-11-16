package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.CustomBehaviours;
import aqario.fowlplay.common.entity.ai.brain.behaviour.FlightBehaviours;
import aqario.fowlplay.common.entity.ai.brain.behaviour.SetEntityLookTarget;
import aqario.fowlplay.common.entity.ai.brain.behaviour.SetRandomLookTarget;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.control.BirdFloatMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.AmphibiousNavigation;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DuckEntity extends TrustingBirdEntity implements BirdBrain<DuckEntity>, VariantHolder<DuckVariant>, Flocking {
    private static final TrackedData<DuckVariant> VARIANT = DataTracker.registerData(
        DuckEntity.class,
        FowlPlayTrackedDataHandlerRegistry.DUCK_VARIANT
    );
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public DuckEntity(EntityType<? extends DuckEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.7f;
    }

    @Override
    protected MoveControl createMoveControl() {
        return new BirdFloatMoveControl(this);
    }

    @Override
    public int getMaxPitchChange() {
        return 18;
    }

    @Override
    public int getMaxYawChange() {
        return 18;
    }

    @Override
    public Pair<Integer, Integer> getFlyHeightRange() {
        return Pair.of(18, 24);
    }

    @Override
    protected EntityNavigation getLandNavigation() {
        return new AmphibiousNavigation(this, this.getWorld());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        FowlPlayRegistries.DUCK_VARIANT.get()
            .fowlplay$getRandom(world.getRandom())
            .ifPresent(this::setVariant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected boolean canSwim() {
        return true;
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createDuckAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.22f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, DuckVariant.GREEN_HEADED.get());
    }

    @Override
    public DuckVariant getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(DuckVariant variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", FowlPlayRegistries.DUCK_VARIANT.get().fowlplay$getId(this.getVariant()).toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        DuckVariant variant = FowlPlayRegistries.DUCK_VARIANT.get().fowlplay$get(Identifier.tryParse(nbt.getString("variant")));
        if(variant != null) {
            this.setVariant(variant);
        }
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
        return Ingredient.fromTag(FowlPlayItemTags.DUCK_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.DUCK_AVOIDS);
    }

    @Override
    public void updateAnimations() {
        this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
        this.flappingState.setRunning(this.isFlying(), this.age);
        this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
    }

    @Override
    protected boolean isFlappingWings() {
        return this.isFlying();
    }

    @Override
    public float getFlapVolume() {
        return 0.8f;
    }

    @Override
    public float getFlapPitch() {
        return 0.6f;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_DUCK_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().duckCallVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_DUCK_HURT.get();
    }

    @Override
    public float getWaterline() {
        return 0.35F;
    }

    @Override
    public CylindricalRadius getWalkRange() {
        return new CylindricalRadius(32, 12);
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    @Override
    protected Brain.Profile<DuckEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends DuckEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>(),
            new AttackTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> getFightTasks() {
        return BirdBrain.fightActivity(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends DuckEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                Pair.of(
                    CustomBehaviours.setWaterWalkTarget(),
                    1
                ),
                Pair.of(
                    CustomBehaviours.idleIfNotFlying()
                        .runFor(entity -> entity.getRandom().nextBetween(100, 300)),
                    2
                )
            )
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends DuckEntity> getIdleTasks() {
        return BirdBrain.idleActivity(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTarget.create(Birds::isPlayerHoldingFood),
            new SetRandomLookTarget<>()
                .lookChance(0.02f),
            new OneRandomBehaviour<>(
                CustomBehaviours.setWaterWalkTarget(),
                CustomBehaviours.idleIfNotFlying()
                    .runFor(entity -> entity.getRandom().nextBetween(100, 300))
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CustomBehaviours.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> getRestTasks() {
        return BirdBrain.restActivity(
            CustomBehaviours.setWaterRestTarget(),
            CustomBehaviours.idleIfInWater()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.WATERFOWL.get();
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
