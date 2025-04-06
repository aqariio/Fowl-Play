package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.common.entity.ai.control.BirdMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.FlightNavigation;
import aqario.fowlplay.core.tags.FowlPlayBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class FlyingBirdEntity extends BirdEntity {
    private static final TrackedData<Boolean> FLYING = DataTracker.registerData(FlyingBirdEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private boolean isFlightMoveControl;
    private float prevRoll;
    private float visualRoll;
    public int timeFlying = 0;
    private static final int ROLL_FACTOR = 4;
    private static final float MIN_HEALTH_TO_FLY = 2.0F;
    private static final int MIN_FLIGHT_TIME = 15;

    protected FlyingBirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setMoveControl(false);
    }

    public static DefaultAttributeContainer.Builder createFlyingBirdAttributes() {
        return BirdEntity.createBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.25f);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnPasserines(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY()
            && world.getBlockState(pos.down()).getBlock() instanceof LeavesBlock
            && world.getBlockState(pos.down()).get(Properties.DISTANCE_1_7) < 7;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnShorebirds(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY()
            && (world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.SHOREBIRDS_SPAWNABLE_ON)
            || world.getFluidState(pos.down()).isIn(FluidTags.WATER));
    }

    @SuppressWarnings("unused")
    public static boolean canSpawnWaterfowl(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY()
            && world.getFluidState(pos.down()).isIn(FluidTags.WATER);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isPersistent();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent() && !this.hasCustomName();
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        this.setMoveControl(this.isFlying());
        return this.navigation;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FLYING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("flying", this.isFlying());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFlying(nbt.getBoolean("flying"));
    }

    public abstract int getFlapFrequency();

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if (this.isFlying()) {
                this.timeFlying++;
                this.setNoGravity(true);
                this.fallDistance = 0.0F;
                if (this.shouldStopFlying()) {
                    this.stopFlying();
                }
            }
            else {
                this.timeFlying = 0;
                this.setNoGravity(false);
            }
            if (this.isFlying() != this.isFlightMoveControl) {
                this.setMoveControl(this.isFlying());
            }
        }
        this.prevRoll = this.visualRoll;
        this.visualRoll = this.calculateRoll(this.prevYaw, this.getYaw());
    }

    private float calculateRoll(float prevYaw, float currentYaw) {
        float difference = currentYaw - prevYaw;
        if (difference >= 180.0F) {
            difference = 360.0F - difference;
        }
        if (difference < -180.0F) {
            difference = -(360.0F + difference);
        }
        return -difference * ROLL_FACTOR;
    }

    public float getRoll(float tickDelta) {
        return tickDelta == 1.0F ? this.visualRoll : MathHelper.lerp(tickDelta, this.prevRoll, this.visualRoll);
    }

    protected MoveControl getLandMoveControl() {
        return new BirdMoveControl(this);
    }

    protected EntityNavigation getLandNavigation() {
        return new MobNavigation(this, this.getWorld());
    }

    protected BirdFlightMoveControl getFlightMoveControl() {
        return new BirdFlightMoveControl(this, 20, 15);
    }

    protected FlightNavigation getFlightNavigation() {
        FlightNavigation navigation = new FlightNavigation(this, this.getWorld());
        navigation.setCanPathThroughDoors(false);
        navigation.setCanEnterOpenDoors(true);
        navigation.setCanSwim(this.canSwim());
        return navigation;
    }

    protected boolean canSwim() {
        return false;
    }

    public void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = this.getFlightMoveControl();
            this.navigation = this.getFlightNavigation();
            this.isFlightMoveControl = true;
        }
        else {
            this.moveControl = this.getLandMoveControl();
            this.navigation = this.getLandNavigation();
            this.isFlightMoveControl = false;
        }
    }

    @Override
    protected float getOffGroundSpeed() {
        return this.isFlying() ? this.getMovementSpeed() : super.getOffGroundSpeed();
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return !this.isFlying() && super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        if (!this.isFlying()) {
            super.fall(heightDifference, onGround, landedState, landedPosition);
        }
    }

    public boolean canStartFlying() {
        return !this.isFlying() && !this.isBelowWaterline() && this.getHealth() >= MIN_HEALTH_TO_FLY;
    }

    public boolean shouldStopFlying() {
        if (this.isSubmergedInWater()) {
            return true;
        }
        if (this.timeFlying < MIN_FLIGHT_TIME) {
            return false;
        }
        return this.isOnGround() || this.isBelowWaterline() || this.getHealth() < MIN_HEALTH_TO_FLY;
    }

    public void startFlying() {
        this.setFlying(true);
        this.setMoveControl(true);
    }

    public void stopFlying() {
        this.setFlying(false);
        this.setMoveControl(false);
        this.getNavigation().stop();
    }

    public boolean isFlying() {
        return this.dataTracker.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.dataTracker.set(FLYING, flying);
    }

    @Override
    protected void playSecondaryStepSound(BlockState state) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void playCombinationStepSounds(BlockState primaryState, BlockState secondaryState) {
    }

    @Override
    public void updateLimbs(boolean flutter) {
        float yDelta = (float) (this.getY() - this.prevY);
        float posDelta;
        if (!this.isFlying() || yDelta > 0) {
            posDelta = (float) MathHelper.magnitude(this.getX() - this.prevX, 0.0, this.getZ() - this.prevZ);
        }
        else {
            posDelta = (float) MathHelper.magnitude(this.getX() - this.prevX, yDelta, this.getZ() - this.prevZ);
        }
        float speed;
        if (this.isFlying()) {
            speed = Math.abs(1 - Math.min(posDelta * 0.8F, 1.0F));
            if (yDelta > 0) {
                speed = (float) Math.sqrt(speed * speed + yDelta * yDelta * 4.0F);
            }
        }
        else {
            speed = Math.min(posDelta * 4.0F, 1.0F);
        }
        this.limbAnimator.updateLimbs(speed, 0.4F);
    }

    @Override
    protected void updateLimbs(float posDelta) {
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (!this.isFlying()) {
            super.travel(movementInput);
            return;
        }

        if (this.isLogicalSideForUpdatingMovement()) {
            if (this.isTouchingWater()) {
                this.updateVelocity(this.isBelowWaterline() ? 0.02F : this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.8F));
            }
            else if (this.isInLava()) {
                this.updateVelocity(0.02F, movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(0.5));
            }
            else {
                float friction = 0.75F;

                this.updateVelocity(this.getMovementSpeed(), movementInput);
                this.move(MovementType.SELF, this.getVelocity());
                this.setVelocity(this.getVelocity().multiply(friction));
            }
        }

        this.updateLimbs(false);
    }
}
