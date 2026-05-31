package aqario.fowlplay.common.entity.bird.raptor;

import aqario.fowlplay.common.entity.ExtendedBrainProvider;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPSchedules;
import aqario.fowlplay.core.FPSoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
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

public class VultureEntity extends TrustingBirdEntity implements BirdBrain<VultureEntity> {
    public VultureEntity(EntityType<? extends VultureEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createVultureAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.FOLLOW_RANGE, 48)
            .add(Attributes.MAX_HEALTH, 18.0f)
            .add(Attributes.ATTACK_DAMAGE, 4.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.225f)
            .add(Attributes.FLYING_SPEED, 0.22f);
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
        return Pair.of(56, 64);
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
        return Ingredient.of(FowlPlayItemTags.VULTURE_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.VULTURE_AVOIDS);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().is(FowlPlayEntityTypeTags.VULTURE_HUNT_TARGETS) ||
            (target.getType().is(FowlPlayEntityTypeTags.VULTURE_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.hasLowHealth()) {
            return false;
        }
        LivingEntity hurtBy = BrainUtils.getLastAttacker(this);
        return hurtBy != null && hurtBy.equals(target);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return !effect.is(MobEffects.HUNGER)
            && !effect.is(MobEffects.POISON)
            && super.canBeAffected(effect);
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
        return FPSoundEvents.VULTURE_CALL.get();
    }

    @Override
    public int getCallDelay() {
        return 800;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FPSoundEvents.VULTURE_HURT.get();
    }

    @Override
    protected Brain.Provider<VultureEntity> brainProvider() {
        return new ExtendedBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends VultureEntity>> getSensors() {
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
    public BrainActivityGroup<? extends VultureEntity> coreActivity() {
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
    public BrainActivityGroup<? extends VultureEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends VultureEntity> fightActivity() {
        return BirdBrain.fight(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public BrainActivityGroup<? extends VultureEntity> idleActivity() {
        return BirdBrain.idle(
            CompositeBehaviours.perch()
        );
    }

    @Override
    public BrainActivityGroup<? extends VultureEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends VultureEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.sleepIfPerched()
        );
    }

    @Override
    public BrainActivityGroup<? extends VultureEntity> soarActivity() {
        return BirdBrain.soar(
            new SetRandomFlightTarget<>()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FPSchedules.RAPTOR.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
