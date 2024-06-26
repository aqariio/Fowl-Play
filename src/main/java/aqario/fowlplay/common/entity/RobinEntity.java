package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.common.entity.ai.goal.BirdWanderGoal;
import aqario.fowlplay.common.entity.ai.goal.FlyAroundGoal;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class RobinEntity extends BirdEntity implements VariantProvider<RobinEntity.Variant> {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(RobinEntity.class, TrackedDataHandlerRegistry.STRING);
    public final AnimationState idleState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState flyState = new AnimationState();
    public final AnimationState floatState = new AnimationState();
    private int flapAnimationTimeout = 0;
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0f;
    private int eatingTime;
    private boolean isFlightMoveControl;

    public RobinEntity(EntityType<? extends RobinEntity> entityType, World world) {
        super(entityType, world);
        this.setMoveControl(false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    private void setMoveControl(boolean isFlying) {
        if (isFlying) {
            this.moveControl = new BirdFlightMoveControl(this, 40, false);
            this.isFlightMoveControl = true;
        } else {
            this.moveControl = new MoveControl(this);
            this.isFlightMoveControl = false;
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, /*Util.getRandom(Variant.VARIANTS, random).toString()*/ Variant.AMERICAN.toString());
    }

    @Override
    public Variant getVariant() {
        return Variant.valueOf(this.dataTracker.get(VARIANT));
    }

    @Override
    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", this.getVariant().toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("variant")) {
            this.setVariant(Variant.valueOf(nbt.getString("variant")));
        }
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

    public static DefaultAttributeContainer.Builder createAttributes() {
        return BirdEntity.createAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(3, new FlyAroundGoal(this));
        this.goalSelector.add(4, new FleeEntityGoal<>(this, PlayerEntity.class, 10.0f, 1.2, 1.5, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
        this.goalSelector.add(5, new BirdWanderGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 20.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.3f;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(stack);
        if (!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND && super.canEquip(stack);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
        return itemStack.isEmpty() || this.eatingTime > 0 && item.isFood() && !itemStack.getItem().isFood();
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
    }

    @Override
    public int getFlapFrequency() {
        return 7;
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            if (!this.isOnGround()) {
                this.flapState.start(this.age);
            } else if (this.flapAnimationTimeout <= 0) {
                this.flapAnimationTimeout = this.getFlapFrequency();
                this.flapState.restart(this.age);
            } else {
                --this.flapAnimationTimeout;
            }

            if (this.isOnGround() && !this.isInsideWaterOrBubbleColumn()) {
                this.idleState.start(this.age);
            } else {
                this.idleState.stop();
            }

            if (this.isFlying()) {
                this.flyState.start(this.age);
            } else {
                this.flyState.stop();
            }

            if (this.isInsideWaterOrBubbleColumn()) {
                this.floatState.start(this.age);
            } else {
                this.floatState.stop();
            }
        }

        super.tick();
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if (!this.getWorld().isClient) {
            if (this.isFlying() != this.isFlightMoveControl) {
                this.setMoveControl(this.isFlying());
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isFlying()) {
            this.glide();
        } else {
            this.flapWings();
        }
        if (!this.getWorld().isClient && this.isAlive() && this.movesIndependently()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsing(this.getWorld(), this);
                    if (!itemStack2.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                    }
                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1f) {
                    this.playSound(this.getEatSound(itemStack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, (byte) 45);
                }
            }
        }
    }

    private void glide() {
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float) (this.isOnGround() || this.hasVehicle() ? -1 : 4) * 0.3f;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0f, 1.0f);
        if (!this.isOnGround() && this.flapSpeed < 1.0f) {
            this.flapSpeed = 1.0f;
        }
        this.flapSpeed *= 0.9f;
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.9, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0f;
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && this.getTarget() == null && this.isOnGround() && !this.isSleeping();
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    public static boolean canSpawn(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
        return world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.PASSERINES_SPAWNABLE_ON) && isBrightEnoughForNaturalSpawn(world, pos);
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent == FowlPlaySoundEvents.ENTITY_ROBIN_SONG) {
            this.playSound(soundEvent, 4.0F, this.getSoundPitch());
        } else {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getWorld().isDay() && this.random.nextFloat() < 0.1F) {
            return FowlPlaySoundEvents.ENTITY_ROBIN_SONG;
        }

        return FowlPlaySoundEvents.ENTITY_ROBIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_ROBIN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    public enum Variant {
        AMERICAN("american"),
        REDBREAST("redbreast");

        public static final List<RobinEntity.Variant> VARIANTS = List.of(Arrays.stream(values())
            .toArray(Variant[]::new));

        private final String id;

        Variant(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
