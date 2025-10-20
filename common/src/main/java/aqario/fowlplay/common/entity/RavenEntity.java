package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class RavenEntity extends TrustingBirdEntity implements BirdBrain<RavenEntity> {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public RavenEntity(EntityType<? extends RavenEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createRavenAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.24f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.7f;
    }

    @Override
    public int getMaxYawChange() {
        return 18;
    }

    @Override
    public Pair<Integer, Integer> getFlyHeightRange() {
        return Pair.of(24, 32);
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
        return Ingredient.fromTag(FowlPlayItemTags.RAVEN_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_HUNT_TARGETS) ||
            (target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.hasLowHealth()) {
            return false;
        }
        Brain<?> brain = this.getBrain();
        Optional<LivingEntity> hurtBy = this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HURT_BY_ENTITY);
        if(!target.getType().isIn(FowlPlayEntityTypeTags.RAVEN_ATTACK_TARGETS) && (hurtBy.isEmpty() || !hurtBy.get().equals(target))) {
            return false;
        }
        Optional<List<? extends PassiveEntity>> nearbyAdults = brain.getOptionalRegisteredMemory(FowlPlayMemoryModuleType.NEAREST_VISIBLE_ADULTS.get());
        return nearbyAdults.filter(passiveEntities -> passiveEntities.size() >= 2).isPresent();
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.RAVEN_AVOIDS);
    }

    @Override
    public void updateAnimations() {
        this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
        this.glidingState.setRunning(this.isFlying(), this.age);
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
    public float getWaterline() {
        return 0.5F;
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_RAVEN_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().ravenCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 1200;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_RAVEN_HURT.get();
    }

    @Override
    protected Brain.Profile<RavenEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends RavenEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<RavenEntity>()
                .setScanRate(bird -> 10),
            new AvoidTargetSensor<RavenEntity>()
                .setScanRate(bird -> 10),
            new AttackTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new FloatToSurfaceOfFluid<>()
                .riseChance(0.5F),
            FlightTasks.stopFalling(),
            new SetAttackTarget<>(),
            SetEntityLookTargetTask.create(Birds::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CompositeTasks.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> getFightTasks() {
        return BirdBrain.fightActivity(
            new InvalidateAttackTarget<>(),
            FlightTasks.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends RavenEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                CompositeTasks.tryForage(),
                CompositeTasks.tryPerch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> getPerchTasks() {
        return BirdBrain.perchActivity(
            CompositeTasks.tryPerch()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeTasks.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> getRestTasks() {
        return BirdBrain.restActivity(
            new SetPerchWalkTargetTask<>()
                .startCondition(Predicate.not(Birds::isPerched)),
            CompositeTasks.idleIfPerched()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends RavenEntity> getSoarTasks() {
        return BirdBrain.soarActivity(
            new OneRandomBehaviour<>(
                Pair.of(
                    new SetRandomFlightTargetTask<>(),
                    5
                ),
                Pair.of(
                    SetAdultWalkTargetTask.create(Birds.STAY_NEAR_ENTITY_RANGE),
                    2
                )
            ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.FORAGER.get();
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
