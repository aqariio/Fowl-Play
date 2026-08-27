package aqario.fowlplay.common.entity.bird.passerine;

import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.common.util.BiPredicates;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPMemoryTypes;
import aqario.fowlplay.core.FPSchedules;
import aqario.fowlplay.core.FPSoundEvents;
import aqario.fowlplay.core.tags.FPEntityTypeTags;
import aqario.fowlplay.core.tags.FPItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
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
        return Ingredient.of(FPItemTags.RAVEN_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().is(FPEntityTypeTags.RAVEN_HUNT_TARGETS) ||
            (target.getType().is(FPEntityTypeTags.RAVEN_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.hasLowHealth()) {
            return false;
        }
        LivingEntity hurtBy = BrainUtils.getLastAttacker(this);
        if(!target.getType().is(FPEntityTypeTags.RAVEN_ATTACK_TARGETS) && (hurtBy == null || !hurtBy.equals(target))) {
            return false;
        }
        Optional<List<? extends AgeableMob>> nearbyAdults = Optional.ofNullable(BrainUtils.getMemory(this, FPMemoryTypes.NEAREST_VISIBLE_ADULTS.get()));
        return nearbyAdults.filter(passiveEntities -> passiveEntities.size() >= 2).isPresent();
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FPEntityTypeTags.RAVEN_AVOIDS);
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
        return FPSoundEvents.RAVEN_CALL.get();
    }

    @Override
    public int getCallDelay() {
        return 1200;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FPSoundEvents.RAVEN_HURT.get();
    }

    @Override
    public List<? extends ExtendedSensor<? extends RavenEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<RavenEntity>()
                .setRadius(32),
            new NearbyPlayersSensor<RavenEntity>()
                .setRadius(24),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>(),
            new AttackTargetSensor<>(),
            new HuntTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> coreActivity() {
        return BirdBrain.core(
            new WakeUp<>(),
            new FloatToSurfaceOfFluid<>()
                .riseChance(0.5F),
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new BirdMoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> fightActivity() {
        return BirdBrain.fight(
            new InvalidateAttackTarget<>(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> forageActivity() {
        return BirdBrain.forage(
            new SetHuntTarget<>()
                .targetPredicate(BiPredicates.not(BirdUtils::isSelfAndTargetInWater)),
            new OneRandomBehaviour<>(
                CompositeBehaviours.forage(),
                CompositeBehaviours.perch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> idleActivity() {
        return BirdBrain.idle(
            CompositeBehaviours.perch()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.sleepIfPerched()
        );
    }

    @Override
    public BrainActivityGroup<? extends RavenEntity> soarActivity() {
        return BirdBrain.soar(
            new SetHuntTarget<>()
                .targetPredicate(BiPredicates.not(BirdUtils::isSelfAndTargetInWater)),
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
    public SmartBrainSchedule getSchedule() {
        return FPSchedules.FORAGER.get();
    }
}
