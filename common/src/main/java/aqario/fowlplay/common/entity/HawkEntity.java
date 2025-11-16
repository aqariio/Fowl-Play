package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
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

public class HawkEntity extends TrustingBirdEntity implements BirdBrain<HawkEntity> {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    private int timeSinceLastFlap = this.getFlapFrequency();
    private int flapTime = 0;

    public HawkEntity(EntityType<? extends HawkEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public int getFlapFrequency() {
        return 100;
    }

    public static AttributeSupplier.Builder createHawkAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.FOLLOW_RANGE, 48)
            .add(Attributes.MAX_HEALTH, 15.0f)
            .add(Attributes.ATTACK_DAMAGE, 3.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.225f)
            .add(Attributes.FLYING_SPEED, 0.24f);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.7f;
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
        return Pair.of(40, 48);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }

    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.HAWK_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        // TODO: avoid if the target has hurt this entity and the target is not an attack target
        return entity.getType().is(FowlPlayEntityTypeTags.HAWK_AVOIDS);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().is(FowlPlayEntityTypeTags.HAWK_HUNT_TARGETS) ||
            (target.getType().is(FowlPlayEntityTypeTags.HAWK_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.hasLowHealth()) {
            return false;
        }
        Optional<LivingEntity> hurtBy = this.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY);
        return hurtBy.isPresent() && hurtBy.get().equals(target);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.HUNGER && super.canBeAffected(effect);
    }

    @Override
    public void updateAnimations() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
        this.glidingState.animateWhen(this.isFlying(), this.tickCount);
        if(this.isFlying()) {
            if(this.timeSinceLastFlap > this.getFlapFrequency()) {
                this.timeSinceLastFlap = 0;
                this.flapTime++;
            }
            else if(this.isCurrentlyFlapping()) {
                this.flapTime++;
            }
            else {
                this.timeSinceLastFlap++;
                this.flapTime = 0;
            }
        }
        else {
            this.timeSinceLastFlap = this.getFlapFrequency();
            this.flapTime = 0;
        }
        this.floatingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
    }

    private boolean isCurrentlyFlapping() {
        return this.flapTime > 0 && this.flapTime < 60;
    }

    @Override
    protected boolean isFlapping() {
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
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.5f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_HAWK_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().hawkCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 800;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_HAWK_HURT.get();
    }

    @Override
    protected Brain.Provider<HawkEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends HawkEntity>> getSensors() {
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
    public BrainActivityGroup<? extends HawkEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new FloatToSurfaceOfFluid<>()
                .riseChance(0.5F),
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            SetEntityLookTarget.create(Birds::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextIntBetweenInclusive(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends HawkEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends HawkEntity> getFightTasks() {
        return BirdBrain.fightActivity(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public BrainActivityGroup<? extends HawkEntity> getPerchTasks() {
        return BirdBrain.perchActivity(
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public BrainActivityGroup<? extends HawkEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CustomBehaviours.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends HawkEntity> getRestTasks() {
        return BirdBrain.restActivity(
            new SetPerchWalkTarget<>()
                .startCondition(Predicate.not(Birds::isPerched)),
            CustomBehaviours.idleIfPerched()
        );
    }

    @Override
    public BrainActivityGroup<? extends HawkEntity> getSoarTasks() {
        return BirdBrain.soarActivity(
            new SetRandomFlightTarget<>()
                .startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.RAPTOR.get();
    }

    @Override
    protected void customServerAiStep() {
        Brain<?> brain = this.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        this.tickBrain(this);
        if(activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
        super.customServerAiStep();
    }
}
