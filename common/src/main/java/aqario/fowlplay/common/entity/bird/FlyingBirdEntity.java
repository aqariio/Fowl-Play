package aqario.fowlplay.common.entity.bird;

import aqario.fowlplay.common.entity.ai.navigation.FlightNavigation;
import aqario.fowlplay.common.entity.ai.navigation.GroundNavigation;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

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
    private double prevHorizontalVelocity;
    public int timeFlying = 0;
    private static final String FLYING_KEY = "flying";
    private static final int ROLL_ANGLE_MULTIPLIER = 4;
    private static final float MIN_HEALTH_TO_FLY = 1.5F;
    private static final int MIN_FLIGHT_TICKS = 15;
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
        nbt.putBoolean(FLYING_KEY, this.isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setFlying(nbt.getBoolean(FLYING_KEY));
    }

    @Override
    protected void onFlap() {
        // TODO: make this synced with the animation
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

    private float rollTowards(float from, float to) {
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
        return -difference * ROLL_ANGLE_MULTIPLIER;
    }

    public float getRoll(float tickDelta) {
        return tickDelta == 1.0F ? this.roll : Mth.lerp(tickDelta, this.prevRoll, this.roll);
    }

    @Override
    protected void updateAnimationStates() {
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
        return !this.isFlightless() && !this.isFlying() && !this.isWaterAboveFloatHeight() && this.getHealth() >= MIN_HEALTH_TO_FLY;
    }

    public boolean shouldStopFlying() {
        if(this.isFlightless() || this.isUnderWater() || this.isPassenger()) {
            return true;
        }
        if(this.timeFlying < MIN_FLIGHT_TICKS) {
            return false;
        }
        return this.onGround()
            || this.isWaterAboveFloatHeight()
            || this.getDeltaMovement().length() < MIN_FLIGHT_VELOCITY
            /*|| this.getHealth() < MIN_HEALTH_TO_FLY*/;
    }

    public void startFlying() {
        this.setFlying(true);
        this.setNavigation(true);
    }

    public void stopFlying() {
        this.setFlying(false);
        this.setNavigation(false);
        this.getNavigation().stop();
        this.clearMemory(MemoryModuleType.WALK_TARGET);
        if(BirdUtils.isPerchingBird(this) && BirdUtils.isPerched(this)) {
            this.setDeltaMovement(Vec3.ZERO);
            this.getNavigation().stop();
        }
    }

    public boolean hasWings() {
        return true;
    }

    public boolean isFlightless() {
        return !this.hasWings();
    }

    public boolean isFlying() {
        if(this.isFlightless()) {
            return false;
        }
        return this.entityData.get(FLYING);
    }

    private void setFlying(boolean flying) {
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
        return BirdUtils.isPerched(this) && super.canSing();
    }

    @Override
    public void calculateEntityAnimation(boolean flutter) {
        if(!this.isFlying()) {
            super.calculateEntityAnimation(flutter);
            return;
        }
        float verticalVelocity = (float) this.getDeltaMovement().y;
        float horizontalVelocity = (float) this.getDeltaMovement().horizontalDistance();
        float horizontalAcceleration = horizontalVelocity - (float) this.prevHorizontalVelocity;
        float targetSpeed = getFlapSpeed(horizontalVelocity, horizontalAcceleration, verticalVelocity);
        this.walkAnimation.update(targetSpeed, 0.4F);
        this.prevHorizontalVelocity = horizontalVelocity;
    }

    private static float getFlapSpeed(float horizontalVelocity, float horizontalAcceleration, float verticalVelocity) {
        float kHV = 1.5F; // horizontal velocity multiplier
        float kHA = 2.0F; // horizontal acceleration multiplier
        float kVV = 3.0F; // vertical velocity multiplier

        float inverseHorizontal = Mth.clamp(1.0F - horizontalVelocity, 0.0F, 1.0F); // inverted (slower speed, larger value)
        float horizontalAccel = Math.abs(horizontalAcceleration);
        float vertical = Math.max(verticalVelocity, 0.0F); // positive movement only (flying up)
        return inverseHorizontal * kHV + horizontalAccel * kHA + vertical * kVV;
    }

    @Override
    public void travel(Vec3 movementInput) {
        if(!this.isFlying()) {
            super.travel(movementInput);
            return;
        }

        if(this.isControlledByLocalInstance()) {
            if(this.isInWater()) {
                this.moveRelative(this.isWaterAboveFloatHeight() ? 0.02F : this.getSpeed(), movementInput);
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
