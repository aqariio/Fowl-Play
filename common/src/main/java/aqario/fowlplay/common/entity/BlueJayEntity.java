package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
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
import java.util.function.Predicate;

public class BlueJayEntity extends FlyingBirdEntity implements BirdBrain<BlueJayEntity> {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public BlueJayEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.475f;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.BLUE_JAY_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.BLUE_JAY_AVOIDS);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public int getFlapFrequency() {
        return 7;
    }

    @Override
    protected void updateAnimations() {
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
        return 0.5f;
    }

    @Override
    public float getFlapPitch() {
        return 1.0f;
    }

    @Override
    public float getWaterline() {
        return 0.45F;
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_BLUE_JAY_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().blueJayCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 480;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_BLUE_JAY_HURT.get();
    }

    @Override
    protected Brain.Profile<BlueJayEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends BlueJayEntity>> getSensors() {
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
    public BrainActivityGroup<? extends BlueJayEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new FloatToSurfaceOfFluid<>(),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(Birds::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                CompositeBehaviours.tryForage(),
                CompositeBehaviours.tryPerch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getPerchTasks() {
        return BirdBrain.perchActivity(
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CustomBehaviours.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends BlueJayEntity> getRestTasks() {
        return BirdBrain.restActivity(
            new SetPerchWalkTarget<>()
                .startCondition(Predicate.not(Birds::isPerched)),
            CustomBehaviours.idleIfPerched()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.FORAGER.get();
    }

    @Override
    protected void mobTick() {
        this.tickBrain(this);
        super.mobTick();
    }
}
