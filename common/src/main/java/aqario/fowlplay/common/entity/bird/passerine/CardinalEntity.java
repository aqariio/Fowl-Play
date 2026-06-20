package aqario.fowlplay.common.entity.bird.passerine;

import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.ExtendedBrainProvider;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPSchedules;
import aqario.fowlplay.core.FPSoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardinalEntity extends FlyingBirdEntity implements BirdBrain<CardinalEntity> {
    public CardinalEntity(EntityType<? extends BirdEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.475f;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.CARDINAL_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.CARDINAL_AVOIDS);
    }

    @Override
    public void updateAnimationStates() {
        if(this.isSleeping()) {
            this.sleepingState.start(this.tickCount);
            this.standingState.stop();
            this.swimmingState.stop();
            this.idleAnimStates.stopAll();
        }
        else {
            this.sleepingState.stop();
            this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
            this.flappingState.animateWhen(this.isFlying(), this.tickCount);
            this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
        }
    }

    @Override
    public float getFlapVolume() {
        return 0.5f;
    }

    @Override
    public float getFlapPitch() {
        return 1.0f;
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FPSoundEvents.CARDINAL_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FPSoundEvents.CARDINAL_SONG.get();
    }

    @Override
    public int getCallDelay() {
        return 180;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FPSoundEvents.CARDINAL_HURT.get();
    }

    @Override
    protected Brain.Provider<CardinalEntity> brainProvider() {
        return new ExtendedBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends CardinalEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends CardinalEntity> coreActivity() {
        return BirdBrain.core(
            new WakeUp<>(),
            new FloatToSurfaceOfFluid<>(),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextIntBetweenInclusive(45, 90)),
            new BirdMoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends CardinalEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends CardinalEntity> forageActivity() {
        return BirdBrain.forage(
            new OneRandomBehaviour<>(
                CompositeBehaviours.forage(),
                CompositeBehaviours.perch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends CardinalEntity> idleActivity() {
        return BirdBrain.idle(
            CompositeBehaviours.perch()
        );
    }

    @Override
    public BrainActivityGroup<? extends CardinalEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends CardinalEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.sleepIfPerched()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FPSchedules.FORAGER.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
