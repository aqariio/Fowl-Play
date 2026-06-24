package aqario.fowlplay.common.entity.bird;

import aqario.fowlplay.common.entity.CustomMobCategory;
import aqario.fowlplay.common.entity.CustomSoundSource;
import aqario.fowlplay.common.entity.ai.control.BirdBodyRotationControl;
import aqario.fowlplay.common.entity.ai.control.BirdLookControl;
import aqario.fowlplay.common.entity.ai.control.BirdMoveControl;
import aqario.fowlplay.common.network.FowlPlayDebugPackets;
import aqario.fowlplay.common.util.AnimationStateList;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FPMemoryTypes;
import aqario.fowlplay.core.FPSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrain;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class BirdEntity extends Animal {
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(
        BirdEntity.class,
        EntityDataSerializers.BOOLEAN
    );
    public final AnimationState standingState = new AnimationState();
    public final AnimationState swimmingState = new AnimationState();
    public final AnimationState sleepingState = new AnimationState();
    public final AnimationStateList idleAnimStates = this.createIdleAnimations();
    private static final String AMBIENT_KEY = "ambient";
    private static final String SLEEPING_KEY = "sleeping";
    private static final String HUNTING_COOLDOWN_KEY = "hunting_cooldown";
    private static final long HUNTING_COOLDOWN_TICKS = 18000L; // 15 mins
    private boolean ambient;
    private int eatingTime;
    protected int idleAnimationChance;

    protected BirdEntity(EntityType<? extends BirdEntity> entityType, Level world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
        this.moveControl = this.createMoveControl();
        this.lookControl = new BirdLookControl(this, 85);
        this.idleAnimationChance = this.random.nextInt(this.getIdleAnimationDelay()) - this.getIdleAnimationDelay();
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0f);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingMalus(PathType.COCOA, -1.0f);
        this.setPathfindingMalus(PathType.FENCE, -1.0f);
    }

    public static AttributeSupplier.Builder createBirdAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.FOLLOW_RANGE, 32)
            .add(Attributes.MAX_HEALTH, 6.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetFromBrain();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setYRot(level.getRandom().nextFloat() * 360.0F);
        this.setYBodyRot(this.getYRot());
        this.setYHeadRot(this.getYRot());
        if(this.shouldSpawnAsAmbient(spawnType)) {
            this.setAmbient(true);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    protected boolean shouldSpawnAsAmbient(MobSpawnType spawnType) {
        return this.shouldBeAmbient()
            && (spawnType == MobSpawnType.NATURAL
            || spawnType == MobSpawnType.CHUNK_GENERATION);
    }

    protected boolean shouldBeAmbient() {
        return this.getType().getCategory() == CustomMobCategory.AMBIENT_BIRDS.mobCategory;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SLEEPING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean(AMBIENT_KEY, this.ambient);
        nbt.putBoolean(SLEEPING_KEY, this.isSleeping());
        if(this.isMemoryPresent(MemoryModuleType.HAS_HUNTING_COOLDOWN)) {
            nbt.putLong(HUNTING_COOLDOWN_KEY, this.getMemoryExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setAmbient(nbt.getBoolean(AMBIENT_KEY));
        this.setSleeping(nbt.getBoolean(SLEEPING_KEY));
        if(nbt.contains(HUNTING_COOLDOWN_KEY)) {
            this.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, nbt.getLong(HUNTING_COOLDOWN_KEY));
        }
    }

    /**
     * ambient birds are able to despawn when far enough from the player, or when the chunks are unloaded
     */
    public boolean isAmbient() {
        return this.ambient;
    }

    protected void setAmbient(boolean ambient) {
        this.ambient = ambient;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return this.isAmbient() && !this.isPersistenceRequired() && !this.hasCustomName();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 6;
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        EquipmentSlot slot = this.getEquipmentSlotForItem(stack);
        if(!this.getItemBySlot(slot).isEmpty()) {
            return false;
        }
        return slot == EquipmentSlot.MAINHAND && super.canTakeItem(stack);
    }

    @Override
    public void setBaby(boolean baby) {
        this.setAge(baby ? -72000 : 0);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        ItemStack heldStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        return this.getFood().test(stack) && !this.getFood().test(heldStack);
    }

    public boolean shouldDropBeakItem(ItemStack stack) {
        return !stack.isEmpty() && !this.getFood().test(stack);
    }

    private void dropWithoutDelay(ItemStack stack, Entity thrower) {
        ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
        if(thrower != null) {
            item.setThrower(thrower);
        }
        this.level().addFreshEntity(item);
    }

    @Override
    protected void pickUpItem(ItemEntity item) {
        Entity thrower = item.getOwner();
        ItemStack stack = item.getItem();
        if(this.canHoldItem(stack)) {
            int i = stack.getCount();
            if(i > 1) {
                this.dropWithoutDelay(stack.split(i - 1), thrower);
            }
            // spit out current item
            this.spawnAtLocation(this.getItemBySlot(EquipmentSlot.MAINHAND));
            this.onItemPickup(item);
            this.setItemSlot(EquipmentSlot.MAINHAND, stack.split(1));
            this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            this.take(item, stack.getCount());
            item.discard();
            this.eatingTime = 0;
            this.clearMemory(FPMemoryTypes.SEES_FOOD.get());
            this.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, HUNTING_COOLDOWN_TICKS);
        }
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        this.dropBeakItem();
    }

    protected void dropBeakItem() {
        this.spawnAtLocation(this.getItemBySlot(EquipmentSlot.MAINHAND));
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean bl = super.hurt(source, amount);
        if(bl) {
            this.dropBeakItem();
        }
        return bl;
    }

    public boolean isWaterAboveFloatHeight() {
        return this.isUnderWater() || this.getFluidHeight(FluidTags.WATER) > this.getBoundingBox().getYsize() * 0.35;
    }

    protected boolean canFloat() {
        return false;
    }

    @Override
    public Vec3 getFluidFallingAdjustedMovement(double gravity, boolean isFalling, Vec3 deltaMovement) {
        if(this.canFloat() && this.isWaterAboveFloatHeight()) {
            double error = Mth.clamp(this.getFluidHeight(FluidTags.WATER) / this.getBoundingBox().getYsize(), 0, 1);
            // cubically proportional to the percentage of hitbox submerged underwater
            double floatVelocity = 0.1 * Math.pow(error, 3);
            return deltaMovement.add(0.0, deltaMovement.y < floatVelocity - 0.005 ? floatVelocity : -0.003, 0.0);
        }
        return super.getFluidFallingAdjustedMovement(gravity, isFalling, deltaMovement);
    }

    public boolean isJumping() {
        return this.jumping;
    }

    @Override
    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    private void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    public void goToSleep() {
        this.setSleeping(true);
    }

    public void wakeUp() {
        this.setSleeping(false);
    }

    private boolean canEat(ItemStack stack) {
        return this.getFood().test(stack) && !this.isSleeping();
    }

    public abstract Ingredient getFood();

    public boolean canHunt(LivingEntity target) {
        return false;
    }

    public boolean shouldAttack(LivingEntity target) {
        return false;
    }

    public boolean shouldAvoid(LivingEntity entity) {
        return false;
    }

    public int getFleeRange(LivingEntity target) {
        return BirdUtils.isNotFlightless(target) ? 32 : 16;
    }

    public boolean hasLowHealth() {
        return this.getHealth() <= this.getMaxHealth() / 2;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return super.canAttack(target) && (this.shouldAttack(target) || this.canHunt(target));
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity) {
        boolean bl = super.killedEntity(level, entity);
        if(bl) {
            if(this.canHunt(entity)) {
                this.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, HUNTING_COOLDOWN_TICKS);
            }
            return true;
        }
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(!this.level().isClientSide() && this.isAlive()) {
            ++this.eatingTime;
            ItemStack stack = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if(this.canEat(stack)) {
                if((this.eatingTime > 40 && this.random.nextFloat() < 0.05f) || this.eatingTime > 200) {
                    if(stack.getItem().components().has(DataComponents.FOOD)) {
                        // noinspection ConstantConditions
                        this.heal(stack.getItem().components().get(DataComponents.FOOD).nutrition());
                    }
                    else {
                        stack.shrink(1);
                    }
                    ItemStack usedStack = stack.finishUsingItem(this.level(), this);
                    if(!usedStack.isEmpty()) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, usedStack);
                    }
                    this.playSound(this.getEatingSound(stack), 1.0f, 1.0f);
                    this.level().broadcastEntityEvent(this, EntityEvent.FOX_EAT);
                    this.eatingTime = 0;
                    return;
                }
                if(this.eatingTime > 20 && this.random.nextFloat() < 0.05f) {
                    this.playSound(this.getEatingSound(stack), 1.0f, 1.0f);
                    this.level().broadcastEntityEvent(this, EntityEvent.FOX_EAT);
                }
            }
            else if(this.shouldDropBeakItem(stack)) {
                if(this.random.nextFloat() < 0.1f) {
                    this.spawnAtLocation(this.getItemBySlot(EquipmentSlot.MAINHAND));
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if(status == EntityEvent.FOX_EAT) {
            ItemStack food = this.getItemBySlot(EquipmentSlot.MAINHAND);
            if(!food.isEmpty()) {
                for(int i = 0; i < 8; i++) {
                    Vec3 vec3d = new Vec3(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                        .xRot(-this.getXRot() * (float) (Math.PI / 180.0))
                        .yRot(-this.getYRot() * (float) (Math.PI / 180.0));
                    this.level().addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, food),
                        this.getX() + this.getLookAngle().x / 2.0,
                        this.getY(),
                        this.getZ() + this.getLookAngle().z / 2.0,
                        vec3d.x,
                        vec3d.y + 0.05,
                        vec3d.z
                    );
                }
            }
        }
        else {
            super.handleEntityEvent(status);
        }
    }

    protected AnimationStateList createIdleAnimations() {
        return new AnimationStateList();
    }

    @Override
    public void tick() {
        if(this.level().isClientSide()) {
            this.updateAnimationStates();
        }
        super.tick();
        if(this.isAmbient() && !this.shouldBeAmbient()) {
            this.setAmbient(false);
        }
    }

    protected boolean isMoving() {
        return this.walkAnimation.isMoving();
    }

    protected void updateAnimationStates() {
        if(this.isSleeping()) {
            this.sleepingState.start(this.tickCount);
            this.standingState.stop();
            this.swimmingState.stop();
            this.idleAnimStates.stopAll();
        }
        else {
            this.sleepingState.stop();
            // on land
            if(!this.isInWaterOrBubble()) {
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
            // in water
            this.swimmingState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        }
    }

    protected int getIdleAnimationDelay() {
        return 240;
    }

    protected void resetIdleAnimationDelay() {
        this.idleAnimationChance = -(this.getIdleAnimationDelay() + this.random.nextIntBetweenInclusive(-200, 200));
    }

    public boolean canCall() {
        return !this.isSleeping();
    }

    public boolean canSing() {
        return !this.isSleeping() && this.onGround() && !this.isBaby();
    }

    public final void playCallSound() {
        this.playSound(this.getCallSound(), 1, this.getVoicePitch());
    }

    public final void playSongSound() {
        this.playSound(this.getSongSound(), 1, this.getVoicePitch());
    }

    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1, this.getVoicePitch());
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {
        if(!this.isSilent() && sound != null) {
            this.level().playSound(null, this, sound, this.getSoundSource(), volume, pitch);
        }
    }

    @Override
    public SoundSource getSoundSource() {
        return CustomSoundSource.BIRDS.soundSource;
    }

    public int getCallDelay() {
        return 240;
    }

    public int getSongDelay() {
        return 720;
    }

    @Nullable
    protected SoundEvent getCallSound() {
        return null;
    }

    @Nullable
    protected SoundEvent getSongSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public SoundEvent getEatingSound(ItemStack stack) {
        return FPSoundEvents.BIRD_EAT.get();
    }

    @Override
    public int getHeadRotSpeed() {
        return 100;
    }

    @Override
    public int getMaxHeadXRot() {
        return 100;
    }

    @Override
    public int getMaxHeadYRot() {
        return 135;
    }

    protected MoveControl createMoveControl() {
        return new BirdMoveControl(this);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BirdBodyRotationControl(this);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.SOUNDS;
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.05F + 1.0F;
    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
        FowlPlayDebugPackets.sendBirdData(this);
//        FowlPlayDebugPackets.sendGenericData(this,
//            Pair.of("fluid height", String.format("%.3f", this.getFluidHeight(FluidTags.WATER))),
//            Pair.of("is water above float height", this.isWaterAboveFloatHeight()),
//            Pair.of("y velocity", String.format("%.3f", this.getDeltaMovement().y))
//        );
    }

    /**
     * equivalent to isPresent check on optional memory
     */
    public <U> boolean isMemoryPresent(MemoryModuleType<U> memoryType) {
        return this.brain.hasMemoryValue(memoryType);
    }

    public <U> U getPresentMemory(MemoryModuleType<U> memoryType) {
        return this.getMemory(memoryType).orElseThrow();
    }

    public <U> Optional<U> getMemory(MemoryModuleType<U> memoryType) {
        return this.brain.getMemory(memoryType);
    }

    public <U> U getMemoryOrDefault(MemoryModuleType<U> memory, Supplier<U> fallback) {
        return this.brain.getMemory(memory).orElseGet(fallback);
    }

    public long getMemoryExpiry(MemoryModuleType<?> memory) {
        return this.brain.getTimeUntilExpiry(memory);
    }

    /**
     * a null value is equivalent to clearMemory
     */
    public <U> void setMemory(MemoryModuleType<U> memoryType, U value) {
        this.brain.setMemory(memoryType, value);
    }

    public <U> void setMemoryWithExpiry(MemoryModuleType<U> memory, U value, long expiryTicks) {
        this.brain.setMemoryWithExpiry(memory, value, expiryTicks);
    }

    public <U> void clearMemory(MemoryModuleType<U> memoryType) {
        this.brain.eraseMemory(memoryType);
    }

    public boolean isActivityActive(Activity activity) {
        return this.brain.isActive(activity);
    }

    public SmartBrainSchedule getActiveSchedule() {
        return ((SmartBrain<?>) this.brain).getSchedule();
    }

    public void setSchedule(SmartBrainSchedule schedule) {
        ((SmartBrain<?>) this.brain).setSchedule(schedule);
    }

    public void updateSchedule() {
        if(this instanceof SmartBrainOwner<?> brainHaver) {
            this.setSchedule(brainHaver.getSchedule());
        }
    }
}
