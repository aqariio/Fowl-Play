package aqario.fowlplay.common.entity.bird.passerine;

import aqario.archaeopteryx.common.ai.ActivityGroup;
import aqario.archaeopteryx.common.ai.ExtendedBrainProvider;
import aqario.archaeopteryx.common.ai.behaviour.OneRandomBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.attack.AnimatableMeleeAttack;
import aqario.archaeopteryx.common.ai.behaviour.look.LookAtTarget;
import aqario.archaeopteryx.common.ai.behaviour.move.FloatToSurfaceOfFluid;
import aqario.archaeopteryx.common.ai.behaviour.move.MoveToWalkTarget;
import aqario.archaeopteryx.common.ai.behaviour.path.SetWalkTargetToAttackTarget;
import aqario.archaeopteryx.common.ai.behaviour.target.InvalidateAttackTarget;
import aqario.archaeopteryx.common.ai.behaviour.target.SetAttackTarget;
import aqario.archaeopteryx.common.ai.schedule.ExtendedSchedule;
import aqario.archaeopteryx.common.ai.sensing.ExtendedSensor;
import aqario.archaeopteryx.common.ai.sensing.vanilla.InWaterSensor;
import aqario.archaeopteryx.common.ai.sensing.vanilla.NearbyLivingEntitySensor;
import aqario.archaeopteryx.common.ai.sensing.vanilla.NearbyPlayersSensor;
import aqario.archaeopteryx.core.util.BrainUtils;
import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlayMemoryTypes;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class RavenEntity extends TrustingBirdEntity implements BirdBrain<RavenEntity> {
    public RavenEntity(EntityType<? extends RavenEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createRavenAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.MAX_HEALTH, 10.0f)
            .add(Attributes.ATTACK_DAMAGE, 3.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.225f)
            .add(Attributes.FLYING_SPEED, 0.24f);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
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

    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.RAVEN_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().is(FowlPlayEntityTypeTags.RAVEN_HUNT_TARGETS) ||
            (target.getType().is(FowlPlayEntityTypeTags.RAVEN_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.hasLowHealth()) {
            return false;
        }
        LivingEntity hurtBy = BrainUtils.getLastAttacker(this);
        if(!target.getType().is(FowlPlayEntityTypeTags.RAVEN_ATTACK_TARGETS) && (hurtBy == null || !hurtBy.equals(target))) {
            return false;
        }
        Optional<List<? extends AgeableMob>> nearbyAdults = Optional.ofNullable(BrainUtils.getMemory(this, FowlPlayMemoryTypes.NEAREST_VISIBLE_ADULTS.get()));
        return nearbyAdults.filter(passiveEntities -> passiveEntities.size() >= 2).isPresent();
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.RAVEN_AVOIDS);
    }

    @Override
    public void updateAnimations() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
        this.glidingState.animateWhen(this.isFlying(), this.tickCount);
        this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
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
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.5f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
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
    protected Brain.Provider<RavenEntity> brainProvider() {
        return new ExtendedBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends RavenEntity>> getSensors() {
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
    public ActivityGroup<? extends RavenEntity> coreActivity() {
        return BirdBrain.core(
            new FloatToSurfaceOfFluid<>()
                .riseChance(0.5F),
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runtime(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> fightActivity() {
        return BirdBrain.fight(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> forageActivity() {
        return BirdBrain.forage(
            new OneRandomBehaviour<>(
                CompositeBehaviours.tryForage(),
                CompositeBehaviours.tryPerch()
            )
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> perchActivity() {
        return BirdBrain.perch(
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> pickupFoodActivity() {
        return BirdBrain.pickupFood(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.idleIfPerched()
        );
    }

    @Override
    public ActivityGroup<? extends RavenEntity> soarActivity() {
        return BirdBrain.soar(
            new OneRandomBehaviour<>(
                Pair.of(
                    new SetRandomFlightTarget<>(),
                    5
                ),
                Pair.of(
                    SetAdultWalkTarget.create(BirdUtils.STAY_NEAR_ENTITY_RANGE),
                    2
                )
            ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
        );
    }

    @Nullable
    @Override
    public ExtendedSchedule getSchedule() {
        return FowlPlaySchedules.FORAGER.get();
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
