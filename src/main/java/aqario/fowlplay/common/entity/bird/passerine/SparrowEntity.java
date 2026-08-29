package aqario.fowlplay.common.entity.bird.passerine;

import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
import aqario.fowlplay.common.entity.bird.Flocking;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.util.AnimationList;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPSchedules;
import aqario.fowlplay.core.FPSoundEvents;
import aqario.fowlplay.core.tags.FPEntityTypeTags;
import aqario.fowlplay.core.tags.FPItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.List;

public class SparrowEntity extends FlyingBirdEntity implements BirdBrain<SparrowEntity>, Flocking {
    protected static final RawAnimation SCRATCHING_ANIM = RawAnimation.begin().thenPlay("idle.scratching");
    protected static final RawAnimation PREENING_ANIM = RawAnimation.begin().thenPlay("idle.preening");
    public final AnimationState scratchingState = new AnimationState();
    public final AnimationState preeningState = new AnimationState();
    private static final int FLAP_FREQUENCY = 1;
    private static final int FLAP_DURATION = 8;
    private int timeSinceLastFlap = FLAP_FREQUENCY;
    private int flapTime = 0;

    public SparrowEntity(EntityType<? extends SparrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.of(FPItemTags.SPARROW_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FPEntityTypeTags.SPARROW_AVOIDS);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected AnimationList createIdleAnimations() {
        return super.createIdleAnimations()
            .with(SCRATCHING_ANIM, 1)
            .with(PREENING_ANIM, 3);
    }

    @Override
    protected void updateAnimationStates() {
        if(this.isSleeping()) {
            this.sleepingState.start(this.tickCount);
            this.standingState.stop();
            this.swimmingState.stop();
            this.idleAnims.stopAll();
        }
        else {
            this.sleepingState.stop();
        }
        // on land
        if(!this.isFlying() && !this.isInWaterOrBubble() && !this.isSleeping()) {
            if(this.random.nextInt(1000) < this.idleAnimationChance++ && !this.isMoving()) {
                this.resetIdleAnimationDelay();
                this.standingState.stop();
                this.idleAnims.stopAll();
                this.idleAnims.getRandom(this.tickCount);
            }
            else if(this.isMoving()) {
                this.idleAnims.stopAll();
            }
            if(!this.idleAnims.containsStarted()) {
                this.standingState.startIfStopped(this.tickCount);
            }
            else {
                this.standingState.stop();
            }
        }
        else {
            this.standingState.stop();
            this.idleAnims.stopAll();
        }
        // flying
        if(this.isFlying()) {
            if(this.timeSinceLastFlap >= FLAP_FREQUENCY) {
                this.timeSinceLastFlap = 0;
                this.flapTime++;
            }
            else if(this.isAnimatingFlapping()) {
                this.flapTime++;
                this.glidingState.stop();
                this.flappingState.startIfStopped(this.tickCount);
            }
            else {
                this.timeSinceLastFlap++;
                this.flapTime = 0;
                this.flappingState.stop();
                this.glidingState.startIfStopped(this.tickCount);
            }
        }
        else {
            this.timeSinceLastFlap = FLAP_FREQUENCY;
            this.flapTime = 0;
            this.flappingState.stop();
            this.glidingState.stop();
        }
        // in water
        this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
    }

    private boolean isAnimatingFlapping() {
        return this.flapTime >= 0 && this.flapTime < FLAP_DURATION;
    }

    @Override
    protected boolean isFlapping() {
        return this.isFlying() && this.isAnimatingFlapping();
    }

    @Override
    protected int getIdleAnimationDelay() {
        return 400;
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
        return FPSoundEvents.SPARROW_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FPSoundEvents.SPARROW_SONG.get();
    }

    @Override
    public int getCallDelay() {
        return 120;
    }

    @Override
    public int getSongDelay() {
        return 360;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FPSoundEvents.SPARROW_HURT.get();
    }

    @Override
    public List<? extends ExtendedSensor<? extends SparrowEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<SparrowEntity>()
                .setRadius(24),
            new NearbyPlayersSensor<SparrowEntity>()
                .setRadius(24),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> coreActivity() {
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
    public BrainActivityGroup<? extends SparrowEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> forageActivity() {
        return BirdBrain.forage(
            CompositeBehaviours.forage()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> idleActivity() {
        return BirdBrain.idle(
            new LeaderlessFlocking(
                3,
                0.03f,
                0.6f,
                0.05f,
                3f
            ),
            CompositeBehaviours.perch()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> restActivity() {
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
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }
}
