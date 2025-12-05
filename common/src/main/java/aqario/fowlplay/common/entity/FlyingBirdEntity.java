package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.navigation.FlightNavigation;
import aqario.fowlplay.common.entity.ai.navigation.GroundNavigation;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.VisibleForTesting;

public abstract class FlyingBirdEntity extends BirdEntity {
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(
        FlyingBirdEntity.class,
        EntityDataSerializers.BOOLEAN
    );
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    private boolean isFlightNavigation;
    private float prevRoll;
    private float roll;
    public int timeFlying = 0;
    private static final int ROLL_FACTOR = 4;
    private static final float MIN_HEALTH_TO_FLY = 1.5F;
    private static final int MIN_FLIGHT_TIME = 15;
    private static final double MIN_FLIGHT_VELOCITY = 0.1;
    private static final float MAX_ROLL_CHANGE = 20;

    protected FlyingBirdEntity(EntityType<? extends BirdEntity> entityType, Level world) {
        super(entityType, world);
        this.setNavigation(false);
        this.setPathfindingMalus(PathType.LEAVES, 0.0f);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0f);
        this.setPathfindingMalus(PathType.WATER, -1.0f);
    }

    public static AttributeSupplier.Builder createFlyingBirdAttributes() {
        return BirdEntity.createBirdAttributes()
            .add(Attributes.MAX_HEALTH, 6.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.28f)
            .add(Attributes.FLYING_SPEED, 0.235f);
    }

    private static boolean hasSkyAccess(LevelAccessor world, BlockPos pos) {
        return world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY();
    }

    private static boolean isMidairSpawn(LevelAccessor world, BlockPos pos) {
        return world.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) <= pos.getY() - 32
            && world.getBlockState(pos.below()).isAir();
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnPasserines(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return hasSkyAccess(world, pos)
            && ((world.getBlockState(pos.below()).getBlock() instanceof LeavesBlock
            && world.getBlockState(pos.below()).getValue(BlockStateProperties.DISTANCE) < 7)
            || isMidairSpawn(world, pos));
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnShorebirds(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return hasSkyAccess(world, pos)
            && (world.getBlockState(pos.below()).is(FowlPlayBlockTags.SHOREBIRDS_SPAWNABLE_ON)
            || world.getFluidState(pos.below()).is(FluidTags.WATER)
            || isMidairSpawn(world, pos));
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnWaterfowl(EntityType<? extends BirdEntity> type, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return hasSkyAccess(world, pos)
            && (world.getFluidState(pos.below()).is(FluidTags.WATER)
            || isMidairSpawn(world, pos));
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        this.setNavigation(this.isFlying());
        return this.navigation;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLYING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("flying", this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFlying(nbt.getBoolean("flying"));
    }

    @Override
    protected void onFlap() {
        this.playSound(FowlPlaySoundEvents.ENTITY_BIRD_FLAP.get(), this.getFlapVolume(), this.getFlapPitch());
    }

    public abstract float getFlapVolume();

    public abstract float getFlapPitch();

    // range where the bird prefers walking over flying
    public CylindricalRadius getWalkRange() {
        return new CylindricalRadius(16, 8);
    }

    @Override
    public void tick() {
        // stop movement when perched
//        if(this.isLogicalSideForUpdatingMovement()) {
//            if(Birds.isNotFlightless(this) && Birds.isPerched(this)) {
//                this.setVelocity(new Vec3d(0, this.getVelocity().y, 0));
//            }
//        }
        super.tick();
        if(!this.level().isClientSide()) {
            if(this.isFlying()) {
                this.timeFlying++;
                this.setNoGravity(true);
                this.fallDistance = 0.0F;
                if(this.shouldStopFlying()) {
                    this.stopFlying();
                }
            }
            else {
                this.timeFlying = 0;
                this.setNoGravity(false);
            }
            if(this.isFlying() != this.isFlightNavigation) {
                this.setNavigation(this.isFlying());
            }
        }
        this.prevRoll = this.roll;
        this.roll = this.rollTowards(this.prevRoll, this.calculateRoll(this.yRotO, this.getYRot()));
    }

    protected float rollTowards(float from, float to) {
        float diff = Mth.degreesDifference(from, to);
        float angle = Mth.clamp(diff, -MAX_ROLL_CHANGE, MAX_ROLL_CHANGE);
        return from + angle;
    }

    private float calculateRoll(float prevYaw, float currentYaw) {
        float difference = currentYaw - prevYaw;
        if(difference >= 180.0F) {
            difference = 360.0F - difference;
        }
        if(difference < -180.0F) {
            difference = -(360.0F + difference);
        }
        return -difference * ROLL_FACTOR;
    }

    public float getRoll(float tickDelta) {
        return tickDelta == 1.0F ? this.roll : Mth.lerp(tickDelta, this.prevRoll, this.roll);
    }

    @Override
    protected void updateAnimations() {
        // on land
        if(!this.isFlying() && !this.isInWaterOrBubble()) {
            if(this.random.nextInt(1000) < this.idleAnimationChance++ && !this.isMoving()) {
                this.resetIdleAnimationDelay();
                this.standingState.stop();
                this.idleAnimStates.stopAll();
                this.idleAnimStates.startRandom(this.tickCount);
            }
            else if(this.isMoving()) {
                this.idleAnimStates.stopAll();
            }
            if(!this.idleAnimStates.containsStarted()) {
                this.standingState.startIfStopped(this.tickCount);
            }
            else {
                this.standingState.stop();
            }
        }
        else {
            this.standingState.stop();
            this.idleAnimStates.stopAll();
        }
        // flying
        this.glidingState.animateWhen(this.isFlying(), this.tickCount);
        // in water
        this.swimmingState.animateWhen(this.isInWaterOrBubble() && !this.isFlying(), this.tickCount);
    }

    @Override
    protected boolean isFlapping() {
        return this.isFlying();
    }

    protected PathNavigation getLandNavigation() {
        return new GroundNavigation(this, this.level());
    }

    protected FlightNavigation getFlightNavigation() {
        FlightNavigation navigation = new FlightNavigation(this, this.level());
        navigation.setCanOpenDoors(false);
        navigation.setCanPassDoors(true);
        navigation.setCanFloat(this.canSwim());
        return navigation;
    }

    // TODO: instead of affecting the pitch and yaw change directly, it should affect the steepness of its path
    public int getMaxPitchChange() {
        return 20;
    }

    public int getMaxYawChange() {
        return 20;
    }

    @Override
    public int getHeadRotSpeed() {
        return this.isFlying() ? 10 : super.getHeadRotSpeed();
    }

    protected boolean canSwim() {
        return false;
    }

    public void setNavigation(boolean isFlying) {
        if(isFlying) {
            this.navigation = this.getFlightNavigation();
            this.isFlightNavigation = true;
        }
        else {
            this.navigation = this.getLandNavigation();
            this.isFlightNavigation = false;
        }
    }

    // min and max flying height relative to ground level
    public Pair<Integer, Integer> getFlyHeightRange() {
        return Pair.of(5, 10);
    }

    @Override
    protected float getFlyingSpeed() {
        return this.isFlying() ? this.getSpeed() : super.getFlyingSpeed();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return !this.isFlying() && super.causeFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        if(!this.isFlying()) {
            super.checkFallDamage(heightDifference, onGround, landedState, landedPosition);
        }
    }

    @Override
    protected boolean canRide(Entity entity) {
        return !this.isFlying() && super.canRide(entity);
    }

    public boolean canStartFlying() {
        return !this.isFlying() && !this.isBelowWaterline() && this.getHealth() >= MIN_HEALTH_TO_FLY;
    }

    public boolean shouldStopFlying() {
        if(this.isUnderWater() || this.isPassenger()) {
            return true;
        }
        if(this.timeFlying < MIN_FLIGHT_TIME) {
            return false;
        }
        return this.onGround()
            || this.isBelowWaterline()
            || this.getDeltaMovement().length() < MIN_FLIGHT_VELOCITY
            || this.getHealth() < MIN_HEALTH_TO_FLY;
    }

    public void startFlying() {
        this.setFlying(true);
        this.setNavigation(true);
    }

    public void stopFlying() {
        this.setFlying(false);
        this.setNavigation(false);
        this.getNavigation().stop();
        Brain<?> brain = this.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        if(Birds.isNotFlightless(this) && Birds.isPerched(this)) {
            this.setDeltaMovement(Vec3.ZERO);
            this.getNavigation().stop();
        }
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    @VisibleForTesting
    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    @Override
    protected void playMuffledStepSound(BlockState state) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void playCombinationStepSounds(BlockState primaryState, BlockState secondaryState) {
    }

    @Override
    protected boolean canSing() {
        return Birds.isPerched(this) && super.canSing();
    }

    // TODO: the wings should flap faster based on positive vertical (maybe horizontal) acceleration, not velocity
    @Override
    public void calculateEntityAnimation(boolean flutter) {
        float yDelta = (float) (this.getY() - this.yo);
        float posDelta;
        if(!this.isFlying() || yDelta > 0) {
            posDelta = (float) Mth.length(this.getX() - this.xo, 0.0, this.getZ() - this.zo);
        }
        else {
            posDelta = (float) Mth.length(this.getX() - this.xo, yDelta, this.getZ() - this.zo);
        }
        float speed;
        if(this.isFlying()) {
            speed = Math.abs(1 - Math.min(posDelta * 0.8F, 1.0F));
            if(yDelta > 0) {
                speed = (float) Math.sqrt(speed * speed + yDelta * yDelta * 4.0F);
            }
        }
        else {
            speed = Math.min(posDelta * 4.0F, 1.0F);
        }
        this.walkAnimation.update(speed, 0.4F);
    }

    @Override
    protected void updateWalkAnimation(float posDelta) {
    }

    @Override
    public void travel(Vec3 movementInput) {
        if(!this.isFlying()) {
            super.travel(movementInput);
            return;
        }

        if(this.isControlledByLocalInstance()) {
            if(this.isInWater()) {
                this.moveRelative(this.isBelowWaterline() ? 0.02F : this.getSpeed(), movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            }
            else if(this.isInLava()) {
                this.moveRelative(0.02F, movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
            }
            else {
                float friction = 0.75F;

                this.moveRelative(this.getSpeed(), movementInput);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(friction));
            }
        }

        this.calculateEntityAnimation(false);
    }
}
