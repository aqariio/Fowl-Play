package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.control.BirdBodyControl;
import aqario.fowlplay.common.entity.ai.control.BirdLookControl;
import aqario.fowlplay.common.entity.ai.control.BirdMoveControl;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayMemoryModuleType;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.platform.CustomSpawnGroup;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class BirdEntity extends AnimalEntity {
    private boolean ambient;
    private int eatingTime;
    protected int idleAnimationChance;
    protected int callChance;
    protected int songChance;

    protected BirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
        this.setCanPickUpLoot(true);
        this.moveControl = this.createMoveControl();
        this.lookControl = new BirdLookControl(this, 85);
        this.idleAnimationChance = this.random.nextInt(this.getIdleAnimationDelay()) - this.getIdleAnimationDelay();
        this.callChance = this.random.nextInt(this.getCallDelay()) - this.getCallDelay();
        this.songChance = this.random.nextInt(this.getSongDelay()) - this.getSongDelay();
    }

    public static DefaultAttributeContainer.Builder createBirdAttributes() {
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setYaw(world.getRandom().nextFloat() * 360.0F);
        this.setBodyYaw(this.getYaw());
        this.setHeadYaw(this.getYaw());
        if(this.getType().getSpawnGroup() == CustomSpawnGroup.ambientBirds()) {
            this.setAmbient(true);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("ambient", this.ambient);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("ambient")) {
            this.setAmbient(nbt.getBoolean("ambient"));
        }
        else {
            this.setAmbient(this.getType().getSpawnGroup() == CustomSpawnGroup.ambientBirds());
        }
    }

    // non-ambient birds count towards the mob cap, but they don't despawn
    public boolean isAmbient() {
        return this.ambient;
    }

    protected void setAmbient(boolean ambient) {
        this.ambient = ambient;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return this.isAmbient() && !this.isPersistent() && !this.hasCustomName();
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = getPreferredEquipmentSlot(stack);
        if(!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        ItemStack heldStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return this.getFood().test(stack) && !this.getFood().test(heldStack);
    }

    private void dropWithoutDelay(ItemStack stack, Entity thrower) {
        ItemEntity item = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), stack);
        if(thrower != null) {
            item.setThrower(thrower.getUuid());
        }
        this.getWorld().spawnEntity(item);
    }

    @Override
    protected void loot(ItemEntity item) {
        Entity thrower = item.getOwner();
        ItemStack stack = item.getStack();
        if(this.canPickupItem(stack)) {
            int i = stack.getCount();
            if(i > 1) {
                this.dropWithoutDelay(stack.split(i - 1), thrower);
            }
            this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
            this.updateDropChances(EquipmentSlot.MAINHAND);
            this.sendPickup(item, stack.getCount());
            item.discard();
            this.eatingTime = 0;
            if(this.getBrain().isMemoryInState(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_PRESENT)) {
                this.getBrain().forget(FowlPlayMemoryModuleType.SEES_FOOD.get());
            }
        }
    }

    public boolean isBelowWaterline() {
        return this.isSubmergedInWater() || this.getFluidHeight(FluidTags.WATER) > this.getWaterline();
    }

    // how much of the hitbox the water should cover (from the bottom)
    public abstract float getWaterline();

    private boolean canEat(ItemStack stack) {
        return this.getFood().test(stack)/* && !this.isSleeping()*/;
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
        return Birds.notFlightless(target) ? 32 : 12;
    }

    public boolean hasLowHealth() {
        return this.getHealth() <= this.getMaxHealth() / 2;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return super.canTarget(target) && (this.shouldAttack(target) || this.canHunt(target));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(!this.getWorld().isClient && this.isAlive()) {
            ++this.eatingTime;
            ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if(this.canEat(stack)) {
                if((this.eatingTime > 40 && this.random.nextFloat() < 0.05f) || this.eatingTime > 200) {
                    if(stack.getItem().isFood()) {
                        // noinspection ConstantConditions
                        this.heal(stack.getItem().getFoodComponent().getHunger());
                    }
                    else {
                        stack.decrement(1);
                    }
                    ItemStack usedStack = stack.finishUsing(this.getWorld(), this);
                    if(!usedStack.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, usedStack);
                    }
                    this.playSound(this.getEatSound(stack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.CREATE_EATING_PARTICLES);
                    this.eatingTime = 0;
                    return;
                }
                if(this.eatingTime > 20 && this.random.nextFloat() < 0.05f) {
                    this.playSound(this.getEatSound(stack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.CREATE_EATING_PARTICLES);
                }
            }
            else if(!stack.isEmpty() && !this.getFood().test(stack)) {
                if(this.random.nextFloat() < 0.1f) {
                    this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
                    this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if(status == EntityStatuses.CREATE_EATING_PARTICLES) {
            ItemStack food = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if(!food.isEmpty()) {
                for(int i = 0; i < 8; i++) {
                    Vec3d vec3d = new Vec3d(((double) this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                        .rotateX(-this.getPitch() * (float) (Math.PI / 180.0))
                        .rotateY(-this.getYaw() * (float) (Math.PI / 180.0));
                    this.getWorld().addParticle(
                        new ItemStackParticleEffect(ParticleTypes.ITEM, food),
                        this.getX() + this.getRotationVector().x / 2.0,
                        this.getY(),
                        this.getZ() + this.getRotationVector().z / 2.0,
                        vec3d.x,
                        vec3d.y + 0.05,
                        vec3d.z
                    );
                }
            }
        }
        else {
            super.handleStatus(status);
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.getWorld().getProfiler().push("birdBaseTick");
        if(this.isAlive() && this.random.nextInt(1000) < this.callChance++) {
            this.resetCallDelay();
            if(this.canCall()) {
                this.playCallSound();
            }
        }
        else if(this.isAlive() && this.random.nextInt(1000) < this.songChance++) {
            this.resetSongDelay();
            if(this.canSing()) {
                this.playSongSound();
            }
        }

        this.getWorld().getProfiler().pop();
    }

    @Override
    public void tick() {
        if(this.getWorld().isClient()) {
            this.updateAnimations();
        }
        super.tick();
    }

    protected void updateAnimations() {
    }

    protected int getIdleAnimationDelay() {
        return 240;
    }

    protected void resetIdleAnimationDelay() {
        this.idleAnimationChance = -(this.getIdleAnimationDelay() + this.random.nextBetween(-200, 200));
    }

    protected boolean canCall() {
        return this.getWorld().isDay() || this.random.nextFloat() < 0.05F;
    }

    protected boolean canSing() {
        return this.getWorld().isDay() && this.isOnGround() && !this.isBaby();
    }

    private void resetCallDelay() {
        this.callChance = -(this.getCallDelay() + this.random.nextBetween(-150, 150));
    }

    private void resetSongDelay() {
        this.songChance = -(this.getSongDelay() + this.random.nextBetween(-150, 150));
    }

    public final void playCallSound() {
        SoundEvent call = this.getCallSound();
        if(call != null) {
            this.playSound(call, this.getCallVolume(), this.getSoundPitch());
        }
    }

    public final void playSongSound() {
        SoundEvent song = this.getSongSound();
        if(song != null) {
            this.playSound(song, this.getSongVolume(), this.getSoundPitch());
        }
    }

    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.resetCallDelay();
        this.resetSongDelay();
        SoundEvent hurt = this.getHurtSound(damageSource);
        if(hurt != null) {
            this.playSound(hurt, this.getCallVolume(), this.getSoundPitch());
        }
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
    public SoundEvent getEatSound(ItemStack stack) {
        return FowlPlaySoundEvents.ENTITY_BIRD_EAT.get();
    }

    protected float getCallVolume() {
        return 1.0F;
    }

    protected float getSongVolume() {
        return 1.0F;
    }

    @Override
    public int getMaxLookYawChange() {
        return 100;
    }

    @Override
    public int getMaxLookPitchChange() {
        return 100;
    }

    @Override
    public int getMaxHeadRotation() {
        return 90;
    }

    protected MoveControl createMoveControl() {
        return new BirdMoveControl(this);
    }

    @Override
    protected BodyControl createBodyControl() {
        return new BirdBodyControl(this);
    }

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.SOUNDS;
    }

    @Override
    public float getSoundPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.05F + 1.0F;
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }
}
