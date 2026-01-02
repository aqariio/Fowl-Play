package aqario.fowlplay.common.entity.bird.chickadee;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.CompositeBehaviours;
import aqario.fowlplay.common.entity.ai.brain.behaviour.CustomBehaviours;
import aqario.fowlplay.common.entity.ai.brain.behaviour.FlightBehaviours;
import aqario.fowlplay.common.entity.ai.brain.behaviour.SetEntityLookTarget;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChickadeeEntity extends FlyingBirdEntity implements BirdBrain<ChickadeeEntity> {
    public ChickadeeEntity(EntityType<? extends ChickadeeEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.CHICKADEE_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.CHICKADEE_AVOIDS);
    }

    @Override
    protected void updateAnimations() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
        this.flappingState.animateWhen(this.isFlying(), this.tickCount);
        this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
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
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.5f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_CHICKADEE_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_CHICKADEE_SONG.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().chickadeeCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().chickadeeSongVolume;
    }

    @Override
    public int getCallDelay() {
        return 480;
    }

    @Override
    public int getSongDelay() {
        return 240;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_CHICKADEE_HURT.get();
    }

    @Override
    protected Brain.Provider<ChickadeeEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends ChickadeeEntity>> getSensors() {
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
    public BrainActivityGroup<? extends ChickadeeEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new FloatToSurfaceOfFluid<>(),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends ChickadeeEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends ChickadeeEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                CompositeBehaviours.tryForage(),
                CompositeBehaviours.tryPerch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends ChickadeeEntity> getPerchTasks() {
        return BirdBrain.perchActivity(
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public BrainActivityGroup<? extends ChickadeeEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends ChickadeeEntity> getRestTasks() {
        return BirdBrain.restActivity(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.idleIfPerched()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.FORAGER.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
