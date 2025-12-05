package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.control.BirdAquaticMoveControl;
import aqario.fowlplay.common.entity.ai.navigation.AmphibiousNavigation;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayEntityTypes;
import aqario.fowlplay.core.FowlPlayParticleTypes;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayBiomeTags;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowTemptation;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomSwimTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.ItemTemptingSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PenguinEntity extends BirdEntity implements BirdBrain<PenguinEntity> {
    private static final int SLIDING_TRANSITION_TICKS = (int) (0.75F * 20);
    private static final int STANDING_TRANSITION_TICKS = (int) (1.0F * 20);
    private static final long LAST_POSE_CHANGE_TICKS = 0L;
    public static final EntityDataAccessor<Long> LAST_POSE_TICK = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.LONG);
    private static final int SWIM_PARTICLE_COUNT = 15;
    public final AnimationState slidingState = new AnimationState();
    public final AnimationState slidingTransitionState = new AnimationState();
    public final AnimationState standingTransitionState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState dancingState = new AnimationState();
    private boolean songPlaying;
    @Nullable
    private BlockPos songSource;

    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.WATER_BORDER, 0.0f);
        this.setPathfindingMalus(PathType.WATER, 0.0f);
        this.setPathfindingMalus(PathType.POWDER_SNOW, 0.0f);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, 0.0f);
        this.lookControl = new SmoothSwimmingLookControl(this, 85);
    }

    @Override
    protected float getFlyingSpeed() {
        return this.isInWaterOrBubble() ? this.getSpeed() : super.getFlyingSpeed();
    }

    @Override
    public float getSpeed() {
        return this.getPose() == Pose.SLIDING ? super.getSpeed() * 1.5F : super.getSpeed();
    }

    @Override
    protected MoveControl createMoveControl() {
        return new BirdAquaticMoveControl(this, 85, 15, 1.0F, 1.0F, true);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadYRot();
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new AmphibiousNavigation(this, this.level());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.initLastPoseTick(level.getLevel().getGameTime());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return FowlPlayEntityTypes.PENGUIN.get().create(world);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return this.getFood().test(stack);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.PENGUIN_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().is(FowlPlayEntityTypeTags.PENGUIN_HUNT_TARGETS);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.PENGUIN_AVOIDS);
    }

    public static AttributeSupplier.Builder createPenguinAttributes() {
        return BirdEntity.createBirdAttributes()
            .add(Attributes.MAX_HEALTH, 16.0f)
            .add(Attributes.ATTACK_DAMAGE, 1.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.145f)
            .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.0f);
    }

    @Override
    public void setRecordPlayingNearby(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    @Override
    public void aiStep() {
        if(this.songSource == null
            || !this.songSource.closerToCenterThan(this.position(), 5)
            || !this.level().getBlockState(this.songSource).is(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }

        super.aiStep();
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LAST_POSE_TICK, LAST_POSE_CHANGE_TICKS);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putLong("lastPoseTick", this.entityData.get(LAST_POSE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        long l = nbt.getLong("lastPoseTick");
        if(l < LAST_POSE_CHANGE_TICKS) {
            this.setPose(Pose.SLIDING);
        }

        this.setLastPoseTick(l);
    }

    @Override
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> entries) {
        super.onSyncedDataUpdated(entries);
        this.refreshDimensions();
    }

    @Override
    public void tick() {
        if(this.getControllingPassenger() != null && this.isInWaterOrBubble()) {
            this.getControllingPassenger().stopRiding();
        }
        if(this.isInWaterOrBubble() && !this.isSliding()) {
            this.setSliding();
        }

        super.tick();

        if(this.level().isClientSide() && this.isInWaterOrBubble() && this.getDeltaMovement().lengthSqr() > 0.02) {
            this.addSwimParticles();
        }

        if(this.isSwimming()) {
            this.setPose(Pose.SWIMMING);
        }
        else if(this.isSliding()) {
            this.setPose(Pose.SLIDING);
        }
        else {
            this.setPose(Pose.STANDING);
        }
    }

    private void addSwimParticles() {
        Vec3 velocity = this.getLookAngle().reverse().scale(0.5);
        for(int i = 0; i < SWIM_PARTICLE_COUNT; i++) {
            this.level().addParticle(
                FowlPlayParticleTypes.SMALL_BUBBLE.get(),
                this.getX() + (this.random.nextFloat() * 0.75F - 0.375F),
                (this.getY() + this.getBoundingBox().getYsize() / 2) + (this.random.nextFloat() * 0.75F - 0.375F),
                this.getZ() + (this.random.nextFloat() * 0.75F - 0.375F),
                velocity.x,
                velocity.y,
                velocity.z
            );
        }
    }

    @Override
    protected void updateAnimations() {
        this.standingState.animateWhen(this.onGround() && !this.isInWaterOrBubble() && !this.isSliding(), this.tickCount);

        if(this.isInWaterOrBubble()) {
            this.standingState.stop();
            this.swimmingState.startIfStopped(this.tickCount);
        }
        else {
            this.swimmingState.stop();
        }

        if(this.shouldUpdateSlidingAnimations() && !this.isInWaterOrBubble()) {
            this.standingState.stop();
            if(this.shouldPlaySlidingTransition()) {
                this.slidingTransitionState.startIfStopped(this.tickCount);
                this.slidingState.stop();
            }
            else {
                this.slidingTransitionState.stop();
                this.slidingState.startIfStopped(this.tickCount);
            }
        }
        else {
            this.slidingTransitionState.stop();
            this.slidingState.stop();
            this.standingTransitionState.animateWhen(this.isChangingPose() && this.getLastPoseTickDelta() >= LAST_POSE_CHANGE_TICKS, this.tickCount);
        }

        if(this.isSongPlaying() && this.onGround()) {
            this.dancingState.startIfStopped(this.tickCount);
            this.setStanding();
            this.standingState.stop();
        }
        else {
            this.dancingState.stop();
        }
    }

    public boolean canStartSliding() {
        return !this.isInWaterOrBubble()
            && !this.isVehicle()
            && this.onGround()
            && (this.level().getBlockState(this.blockPosition().below()).is(FowlPlayBlockTags.PENGUINS_SLIDE_ON)
            || this.level().getBlockState(this.blockPosition()).is(FowlPlayBlockTags.PENGUINS_SLIDE_ON));
    }

    public boolean isSliding() {
        return this.entityData.get(LAST_POSE_TICK) < LAST_POSE_CHANGE_TICKS;
    }

    public boolean shouldUpdateSlidingAnimations() {
        return this.getLastPoseTickDelta() < LAST_POSE_CHANGE_TICKS != this.isSliding();
    }

    public boolean isChangingPose() {
        long l = this.getLastPoseTickDelta();
        return l < (long) (this.isSliding() ? SLIDING_TRANSITION_TICKS : STANDING_TRANSITION_TICKS);
    }

    private boolean shouldPlaySlidingTransition() {
        return this.isSliding() && this.getLastPoseTickDelta() < SLIDING_TRANSITION_TICKS && this.getLastPoseTickDelta() >= LAST_POSE_CHANGE_TICKS;
    }

    public void startSliding() {
        if(!this.isSliding()) {
            this.setPose(Pose.SLIDING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.setLastPoseTick(-this.level().getGameTime());
        }
    }

    public void stopSliding() {
        if(this.isSliding()) {
            this.setPose(Pose.STANDING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.setLastPoseTick(this.level().getGameTime());
        }
    }

    public void setStanding() {
        this.setPose(Pose.STANDING);
        this.gameEvent(GameEvent.ENTITY_ACTION);
        this.initLastPoseTick(this.level().getGameTime());
    }

    public void setSliding() {
        this.setPose(Pose.SLIDING);
        this.gameEvent(GameEvent.ENTITY_ACTION);
        this.setLastPoseTick(-Math.max(LAST_POSE_CHANGE_TICKS, this.level().getGameTime() - SLIDING_TRANSITION_TICKS - 1L));
    }

    private void setLastPoseTick(long lastPoseTick) {
        this.entityData.set(LAST_POSE_TICK, lastPoseTick);
    }

    private void initLastPoseTick(long time) {
        this.setLastPoseTick(Math.max(LAST_POSE_CHANGE_TICKS, time - STANDING_TRANSITION_TICKS - 1L));
    }

    public long getLastPoseTickDelta() {
        return this.level().getGameTime() - Math.abs(this.entityData.get(LAST_POSE_TICK));
    }

    @Override
    public void updateSwimming() {
        this.setSwimming(this.isInWaterOrBubble() && !this.isPassenger());
    }

    protected void clampPassengerYaw(Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float g = Mth.clamp(f, -105.0F, 105.0F);
        entity.yRotO += g - f;
        entity.setYRot(entity.getYRot() + g - f);
        entity.setYHeadRot(entity.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity passenger) {
        this.clampPassengerYaw(passenger);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Vec3 vec3d = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO, passenger.getBbWidth(), passenger.getYRot());
        double d = this.getX() + vec3d.x;
        double e = this.getZ() + vec3d.z;
        BlockPos blockPos = new BlockPos((int) d, (int) this.getAttackBoundingBox().maxY, (int) e);
        BlockPos blockPos2 = blockPos.below();
        if(!this.level().isWaterAt(blockPos2)) {
            List<Vec3> list = Lists.newArrayList();
            double f = this.level().getBlockFloorHeight(blockPos);
            if(DismountHelper.isBlockFloorValid(f)) {
                list.add(new Vec3(d, (double) blockPos.getY() + f, e));
            }

            double g = this.level().getBlockFloorHeight(blockPos2);
            if(DismountHelper.isBlockFloorValid(g)) {
                list.add(new Vec3(d, (double) blockPos2.getY() + g, e));
            }

            for(Pose entityPose : passenger.getDismountPoses()) {
                for(Vec3 vec3d2 : list) {
                    if(DismountHelper.canDismountTo(this.level(), vec3d2, passenger, entityPose)) {
                        passenger.setPose(entityPose);
                        return vec3d2;
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public float maxUpStep() {
        return this.getPose() == Pose.SLIDING ? 1.1F : super.maxUpStep();
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.62F : 1.0F;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions dimensions = super.getDefaultDimensions(pose);
        return pose == Pose.SLIDING || pose == Pose.SWIMMING ? dimensions.scale(1.0F, 0.35F) : dimensions;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return !this.isVehicle();
    }

    public boolean isReadyToBreed() {
        return !this.isVehicle() && !this.isPassenger() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }

    @Override
    public boolean canMate(Animal other) {
        return other != this
            && other instanceof PenguinEntity penguin
            && this.isReadyToBreed()
            && penguin.isReadyToBreed();
    }

    public boolean shouldStepDown() {
        BlockPos pos = this.blockPosition();
        return !this.onGround()
            && this.fallDistance > 0f
            && this.fallDistance < 0.1f
            && !this.level().getBlockState(pos.below()).getCollisionShape(this.level(), pos.below()).isEmpty()
            /*|| !this.getWorld().getBlockState(pos.down(2)).getCollisionShape(this.getWorld(), pos.down(2)).isEmpty()*/;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return super.canAddPassenger(passenger) && !this.isUnderWater();
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        boolean touchingWater = this.isInWater();
        boolean bl = super.updateInWaterStateAndDoFluidPushing();
        if(touchingWater != this.isInWater()) {
            this.setPose(this.isInWater() ? Pose.SWIMMING : Pose.STANDING);
            this.refreshDimensions();
        }
        return bl;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnPenguins(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return world.getBiome(pos).is(FowlPlayBiomeTags.SPAWNS_PENGUINS) && world.getBlockState(pos.below()).is(FowlPlayBlockTags.PENGUINS_SPAWNABLE_ON);
    }

    @Override
    protected void tickRidden(Player player, Vec3 input) {
        super.tickRidden(player, input);
        float sidewaysMovement = player.xxa;

        double rotation = 3;
        if(Math.abs(sidewaysMovement) == 0) {
            rotation = 0;
        }
        this.setRot((float) (this.getYRot() + (rotation * (sidewaysMovement < 0 ? 1 : -1))), this.getXRot());
        player.setYRot((float) (player.getYRot() + (rotation * (sidewaysMovement < 0 ? 1 : -1))) % 360.0F);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 input) {
        float forwardMovement = player.zza * 0.2F;
        if(this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(FowlPlayBlockTags.PENGUINS_SLIDE_ON) || this.getInBlockState().is(FowlPlayBlockTags.PENGUINS_SLIDE_ON)) {
            forwardMovement *= 2.0F;
        }

        return new Vec3(0.0, 0.0, Math.max(forwardMovement, 0));
    }

    @Override
    protected float getRiddenSpeed(Player player) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    public int getMaxAirSupply() {
        return 9600;
    }

    @Override
    protected int increaseAirSupply(int air) {
        return this.getMaxAirSupply();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        boolean bl = this.isFood(player.getItemInHand(hand));
        if(!bl && !this.isVehicle() && !player.isSecondaryUseActive() && !this.isBaby() && this.isSliding()) {
            if(!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        if(this.getPose() == Pose.SLIDING) {
            return (super.calculateFallDamage(fallDistance, damageMultiplier) - 3) / 2;
        }
        return super.calculateFallDamage(fallDistance, damageMultiplier);
    }

    @Override
    protected boolean canCall() {
        return !this.isInWaterOrBubble() && super.canCall();
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return this.isBaby() ? FowlPlaySoundEvents.ENTITY_PENGUIN_BABY_CALL.get() : FowlPlaySoundEvents.ENTITY_PENGUIN_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().penguinCallVolume;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_SWIM.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_PENGUIN_HURT.get();
    }

    @Override
    protected Brain.Provider<PenguinEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends PenguinEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new ItemTemptingSensor<PenguinEntity>()
                .temptedWith((entity, stack) -> this.getFood().test(stack)),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>(),
            new AttackTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends PenguinEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new SetBreatheTarget<>(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextIntBetweenInclusive(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends PenguinEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends PenguinEntity> getFightTasks() {
        return BirdBrain.fightActivity(
            new InvalidateAttackTarget<>(),
            SlideBehaviours.startSliding(),
            new SetWalkTargetToAttackTarget<>()
                .speedMod((entity, target) -> Birds.FAST_SPEED),
            new AnimatableMeleeAttack<>(0),
            new InvalidateMemory<PenguinEntity, LivingEntity>(MemoryModuleType.ATTACK_TARGET)
                .invalidateIf((entity, memory) -> BehaviorUtils.isBreeding(entity))
        );
    }

    @Override
    public BrainActivityGroup<? extends PenguinEntity> getIdleTasks() {
        return BirdBrain.idleActivity(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTarget.create(EntityType.PLAYER),
            new FollowTemptation<>(),
            new SetRandomLookTarget<>()
                .lookChance(0.02f),
            new OneRandomBehaviour<>(
                Pair.of(
                    new SetRandomWalkTarget<>()
                        .setRadius(64, 32),
                    5
                ),
                Pair.of(
                    new SetRandomSwimTarget<>()
                        .setRadius(32, 16),
                    2
                )
            ).startCondition(entity -> entity.isInWaterOrBubble() && !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)),
            new OneRandomBehaviour<>(
                Pair.of(
                    new SetRandomWalkTarget<>()
                        .setRadius(24, 12),
                    2
                ),
                Pair.of(
                    SlideBehaviours.toggleSliding(20),
                    5
                ),
                Pair.of(
                    new Idle<>()
                        .runFor(entity -> entity.getRandom().nextIntBetweenInclusive(400, 800)),
                    5
                ),
                Pair.of(
                    SetAdultWalkTarget.create(Birds.STAY_NEAR_ENTITY_RANGE),
                    2
                ),
                Pair.of(
                    CompositeBehaviours.slideToWater(),
                    6
                )
            ).startCondition(entity -> !entity.isInWaterOrBubble() && !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
        );
    }

    @Override
    public BrainActivityGroup<? extends PenguinEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            SlideBehaviours.startSliding(),
            CustomBehaviours.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends PenguinEntity> getRestTasks() {
        return BirdBrain.restActivity(
            new Idle<>()
        );
    }

    @Override
    protected void customServerAiStep() {
        Brain<?> brain = this.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        this.tickBrain(this);
        if(activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
        super.customServerAiStep();
    }
}
