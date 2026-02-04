package aqario.fowlplay.common.entity.bird.passerine;

import aqario.archaeopteryx.common.ai.ActivityGroup;
import aqario.archaeopteryx.common.ai.ExtendedBrainProvider;
import aqario.archaeopteryx.common.ai.behaviour.OneRandomBehaviour;
import aqario.archaeopteryx.common.ai.behaviour.look.LookAtTarget;
import aqario.archaeopteryx.common.ai.behaviour.move.FloatToSurfaceOfFluid;
import aqario.archaeopteryx.common.ai.behaviour.move.MoveToWalkTarget;
import aqario.archaeopteryx.common.ai.schedule.ExtendedSchedule;
import aqario.archaeopteryx.common.ai.sensing.ExtendedSensor;
import aqario.archaeopteryx.common.ai.sensing.vanilla.InWaterSensor;
import aqario.archaeopteryx.common.ai.sensing.vanilla.NearbyLivingEntitySensor;
import aqario.archaeopteryx.common.ai.sensing.vanilla.NearbyPlayersSensor;
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
        return new ExtendedBrainProvider<>(this);
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
    public ActivityGroup<? extends ChickadeeEntity> coreActivity() {
        return BirdBrain.core(
            new FloatToSurfaceOfFluid<>(),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runtime(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public ActivityGroup<? extends ChickadeeEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public ActivityGroup<? extends ChickadeeEntity> forageActivity() {
        return BirdBrain.forage(
            new OneRandomBehaviour<>(
                CompositeBehaviours.tryForage(),
                CompositeBehaviours.tryPerch()
            )
        );
    }

    @Override
    public ActivityGroup<? extends ChickadeeEntity> perchActivity() {
        return BirdBrain.perch(
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public ActivityGroup<? extends ChickadeeEntity> pickupFoodActivity() {
        return BirdBrain.pickupFood(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public ActivityGroup<? extends ChickadeeEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.idleIfPerched()
        );
    }

    @Nullable
    @Override
    public ExtendedSchedule getSchedule() {
        return FowlPlaySchedules.FORAGER.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
