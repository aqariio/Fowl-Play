package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
import aqario.fowlplay.common.util.AnimationStateList;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
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

public class SparrowEntity extends FlyingBirdEntity implements BirdBrain<SparrowEntity>, Flocking {
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
        return Ingredient.of(FowlPlayItemTags.SPARROW_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.SPARROW_AVOIDS);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected AnimationStateList createIdleAnimations() {
        return new AnimationStateList()
            .with(this.scratchingState, 1)
            .with(this.preeningState, 3);
    }

    @Override
    protected void updateAnimations() {
        // on land
        if(!this.isFlying() && !this.isInWaterOrBubble()) {
            if(this.random.nextInt(1000) < this.idleAnimationChance++ && !this.isMoving()) {
                this.resetIdleAnimationDelay();
                this.standingState.stop();
                this.preeningState.stop();
                this.scratchingState.stop();
                if(this.getRandom().nextFloat() < 0.75f) {
                    this.preeningState.start(this.tickCount);
                }
                else {
                    this.scratchingState.start(this.tickCount);
                }
            }
            else if(this.isMoving()) {
                this.preeningState.stop();
                this.scratchingState.stop();
            }
            if(!(this.preeningState.isStarted() || this.scratchingState.isStarted())) {
                this.standingState.startIfStopped(this.tickCount);
            }
            else {
                this.standingState.stop();
            }
        }
        else {
            this.standingState.stop();
            this.preeningState.stop();
            this.scratchingState.stop();
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
        return FowlPlaySoundEvents.ENTITY_SPARROW_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_SPARROW_SONG.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().sparrowCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().sparrowSongVolume;
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
        return FowlPlaySoundEvents.ENTITY_SPARROW_HURT.get();
    }

    @Override
    protected Brain.Provider<SparrowEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends SparrowEntity>> getSensors() {
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
    public BrainActivityGroup<? extends SparrowEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new FloatToSurfaceOfFluid<>(),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(Birds::isPlayerHoldingFood),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                CompositeBehaviours.tryForage(),
                CompositeBehaviours.tryPerch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getPerchTasks() {
        return BirdBrain.perchActivity(
            new LeaderlessFlocking(
                3,
                0.03f,
                0.6f,
                0.05f,
                3f
            ),
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getRestTasks() {
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

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }
}
