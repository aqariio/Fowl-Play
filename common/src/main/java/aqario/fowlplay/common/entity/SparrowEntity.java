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
import net.minecraft.util.math.Vec3d;
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

public class SparrowEntity extends FlyingBirdEntity implements BirdBrain<SparrowEntity>, Flocking {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    public final AnimationState scratchingState = new AnimationState();
    public final AnimationState preeningState = new AnimationState();
    private int timeSinceLastFlap = this.getFlapFrequency();
    private static final int FLAP_DURATION = 8;
    private int flapTime = 0;

    public SparrowEntity(EntityType<? extends SparrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.4f;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.SPARROW_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.SPARROW_AVOIDS);
    }

    @Override
    public int getFlapFrequency() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
    }

    private boolean isMoving() {
        return this.limbAnimator.isLimbMoving();
    }

    @Override
    protected void updateAnimations() {
        // on land
        if(!this.isFlying() && !this.isInsideWaterOrBubbleColumn()) {
            if(this.random.nextInt(1000) < this.idleAnimationChance++ && !this.isMoving()) {
                this.resetIdleAnimationDelay();
                this.standingState.stop();
                this.preeningState.stop();
                this.scratchingState.stop();
                if(this.getRandom().nextFloat() < 0.75f) {
                    this.preeningState.start(this.age);
                }
                else {
                    this.scratchingState.start(this.age);
                }
            }
            else if(this.isMoving()) {
                this.preeningState.stop();
                this.scratchingState.stop();
            }
            if(!(this.preeningState.isRunning() || this.scratchingState.isRunning())) {
                this.standingState.startIfNotRunning(this.age);
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
            if(this.timeSinceLastFlap >= this.getFlapFrequency()) {
                this.timeSinceLastFlap = 0;
                this.flapTime++;
            }
            else if(this.flapTime >= 0 && this.flapTime < FLAP_DURATION) {
                this.flapTime++;
                this.glidingState.stop();
                this.flappingState.startIfNotRunning(this.age);
            }
            else {
                this.timeSinceLastFlap++;
                this.flapTime = 0;
                this.flappingState.stop();
                this.glidingState.startIfNotRunning(this.age);
            }
        }
        else {
            this.timeSinceLastFlap = this.getFlapFrequency();
            this.flapTime = 0;
            this.flappingState.stop();
            this.glidingState.stop();
        }
        // in water
        this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
    }

    @Override
    protected int getIdleAnimationDelay() {
        return 400;
    }

    @Override
    protected boolean isFlappingWings() {
        return this.isFlying() && this.flapTime >= 0 && this.flapTime < FLAP_DURATION;
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

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
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
    protected Brain.Profile<SparrowEntity> createBrainProfile() {
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
                .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @SuppressWarnings("unchecked")
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
            CustomBehaviours.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends SparrowEntity> getRestTasks() {
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

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }
}
