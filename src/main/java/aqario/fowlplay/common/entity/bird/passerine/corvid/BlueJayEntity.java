package aqario.fowlplay.common.entity.bird.passerine.corvid;

import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
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
import aqario.fowlplay.core.tags.FPEntityTypeTags;
import aqario.fowlplay.core.tags.FPItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class BlueJayEntity extends FlyingBirdEntity implements BirdBrain<BlueJayEntity> {
    public BlueJayEntity(EntityType<? extends BirdEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.of(FPItemTags.BLUE_JAY_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FPEntityTypeTags.BLUE_JAY_AVOIDS);
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
        return FPSoundEvents.BLUE_JAY_CALL.get();
    }

    @Override
    public int getCallDelay() {
        return 480;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FPSoundEvents.BLUE_JAY_HURT.get();
    }

    @Override
    public List<? extends ExtendedSensor<? extends BlueJayEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<BlueJayEntity>()
                .setRadius(24),
            new NearbyPlayersSensor<BlueJayEntity>()
                .setRadius(24),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> coreActivity() {
        return BirdBrain.core(
            new WakeUp<>(),
            new FloatToSurfaceOfFluid<>(),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new BirdMoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> forageActivity() {
        return BirdBrain.forage(
            new OneRandomBehaviour<>(
                CompositeBehaviours.forage(),
                CompositeBehaviours.perch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> idleActivity() {
        return BirdBrain.idle(
            CompositeBehaviours.perch()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> restActivity() {
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
}
