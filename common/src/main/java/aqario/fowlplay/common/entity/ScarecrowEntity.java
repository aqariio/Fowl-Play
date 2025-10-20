package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlayItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class ScarecrowEntity extends LivingEntity implements InventoryChangedListener, NamedScreenHandlerFactory {
    private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE = entity -> entity instanceof AbstractMinecartEntity
        && ((AbstractMinecartEntity) entity).getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE;
    private static final EulerAngle DEFAULT_HEAD_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
    private static final EulerAngle DEFAULT_BODY_ROTATION = new EulerAngle(0.0F, 0.0F, 0.0F);
    private static final EulerAngle DEFAULT_LEFT_ARM_ROTATION = new EulerAngle(0.0F, 0.0F, -90.0F);
    private static final EulerAngle DEFAULT_RIGHT_ARM_ROTATION = new EulerAngle(0.0F, 0.0F, 90.0F);
    public static final TrackedData<EulerAngle> HEAD_ROTATION = DataTracker.registerData(ScarecrowEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> BODY_ROTATION = DataTracker.registerData(ScarecrowEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> LEFT_ARM_ROTATION = DataTracker.registerData(ScarecrowEntity.class, TrackedDataHandlerRegistry.ROTATION);
    public static final TrackedData<EulerAngle> RIGHT_ARM_ROTATION = DataTracker.registerData(ScarecrowEntity.class, TrackedDataHandlerRegistry.ROTATION);
    private EulerAngle headRotation = DEFAULT_HEAD_ROTATION;
    private EulerAngle bodyRotation = DEFAULT_BODY_ROTATION;
    private EulerAngle leftArmRotation = DEFAULT_LEFT_ARM_ROTATION;
    private EulerAngle rightArmRotation = DEFAULT_RIGHT_ARM_ROTATION;
    private static final String POSE_KEY = "pose";
    private static final String HEAD_ROTATION_KEY = "head";
    private static final String BODY_ROTATION_KEY = "body";
    private static final String LEFT_ARM_ROTATION_KEY = "left_arm";
    private static final String RIGHT_ARM_ROTATION_KEY = "right_arm";
    private static final String ITEMS_KEY = "items";
    private static final String SLOT_KEY = "slot";
    private static final int INVENTORY_SIZE = 4;
    private static final int HEAD_SLOT = 0;
    private static final int CHEST_SLOT = 1;
    private static final int MAINHAND_SLOT = 2;
    private static final int OFFHAND_SLOT = 3;
    protected SimpleInventory inventory;

    public ScarecrowEntity(EntityType<? extends ScarecrowEntity> entityType, World world) {
        super(entityType, world);
        this.inventory = new SimpleInventory(INVENTORY_SIZE);
        this.inventory.addListener(this);
        this.setStepHeight(0.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.72f;
    }

    public static DefaultAttributeContainer.Builder createScarecrowAttributes() {
        return createLivingAttributes()
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected float turnHead(float bodyRotation, float headRotation) {
        this.prevBodyYaw = this.prevYaw;
        this.bodyYaw = this.getYaw();
        return 0.0F;
    }

    @Override
    public void setBodyYaw(float bodyYaw) {
        this.prevBodyYaw = this.prevYaw = bodyYaw;
        this.prevHeadYaw = this.headYaw = bodyYaw;
    }

    @Override
    public void setHeadYaw(float headYaw) {
        this.prevBodyYaw = this.prevYaw = headYaw;
        this.prevHeadYaw = this.headYaw = headYaw;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        if(this.inventory != null) {
            for(int i = 0; i < this.inventory.size(); ++i) {
                ItemStack itemStack = this.inventory.getStack(i);
                if(itemStack.isEmpty()) {
                    continue;
                }
                this.dropStack(itemStack);
            }
        }
    }

    @Override
    public Iterable<ItemStack> getHandItems() {
        return ImmutableList.of(this.inventory.getStack(MAINHAND_SLOT), this.inventory.getStack(OFFHAND_SLOT));
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return ImmutableList.of(this.inventory.getStack(HEAD_SLOT), this.inventory.getStack(CHEST_SLOT));
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return switch(slot) {
            case HEAD -> this.inventory.getStack(HEAD_SLOT);
            case CHEST -> this.inventory.getStack(CHEST_SLOT);
            case MAINHAND -> this.inventory.getStack(MAINHAND_SLOT);
            case OFFHAND -> this.inventory.getStack(OFFHAND_SLOT);
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        this.processEquippedStack(stack);
        switch(slot) {
            case HEAD -> this.inventory.setStack(HEAD_SLOT, stack);
            case CHEST -> this.inventory.setStack(CHEST_SLOT, stack);
            case MAINHAND -> this.inventory.setStack(MAINHAND_SLOT, stack);
            case OFFHAND -> this.inventory.setStack(OFFHAND_SLOT, stack);
        }
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = getPreferredEquipmentSlot(stack);
        return this.getEquippedStack(equipmentSlot).isEmpty();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        this.dataTracker.startTracking(BODY_ROTATION, DEFAULT_BODY_ROTATION);
        this.dataTracker.startTracking(LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
        this.dataTracker.startTracking(RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList nbtList = new NbtList();

        for(int i = 0; i < this.inventory.size(); ++i) {
            ItemStack itemStack = this.inventory.getStack(i);
            if(!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte(SLOT_KEY, (byte) i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }
        nbt.put(ITEMS_KEY, nbtList);
        nbt.put(POSE_KEY, this.poseToNbt());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        NbtList nbtList = nbt.getList(ITEMS_KEY, NbtElement.COMPOUND_TYPE);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte(SLOT_KEY) & 255;
            if(j < this.inventory.size()) {
                this.inventory.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }
        NbtCompound poseNbt = nbt.getCompound(POSE_KEY);
        this.readPoseNbt(poseNbt);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if(!player.getWorld().isClient() && player.getStackInHand(hand).isEmpty() && !player.shouldCancelInteraction()) {
            return ActionResult.CONSUME;
        }
        return super.interact(player, hand);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
        List<Entity> list = this.getWorld().getOtherEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE);
        for(Entity entity : list) {
            if(this.squaredDistanceTo(entity) <= 0.2) {
                entity.pushAwayFrom(this);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(this.getWorld().isClient() || this.isRemoved()) {
            return false;
        }
        if(source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if(this.isInvulnerableTo(source)) {
            return false;
        }
        if(source.getSource() instanceof FireworkRocketEntity) {
            return false;
        }
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            this.updateHealth(source, amount);
            return false;
        }
        if(source.isOf(DamageTypes.LAVA)) {
            this.setOnFireFor(10);
            if(this.isOnFire()) {
                this.updateHealth(source, 1.0F);
            }
            return false;
        }
        if(source.isIn(DamageTypeTags.IS_FIRE)) {
            this.setOnFireFor(5);
            if(this.isOnFire()) {
                this.updateHealth(source, 0.05F);
            }
            return false;
        }
        if(source.getSource() instanceof PersistentProjectileEntity) {
            return true;
        }
        if(source.getAttacker() instanceof PlayerEntity player) {
            if(!player.isSneaking()) {
                this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
                return true;
            }
            if(!player.getAbilities().allowModifyWorld) {
                return false;
            }
            if(source.isSourceCreativePlayer()) {
                this.onBreak(source);
                this.spawnBreakParticles();
                this.kill();
                return false;
            }
            this.breakAndDropThis(source);
            this.spawnBreakParticles();
            this.kill();
        }
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource.isIn(DamageTypeTags.IS_FALL);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.emitGameEvent(GameEvent.ENTITY_DIE);
    }

    protected void spawnBreakParticles() {
        if(this.getWorld() instanceof ServerWorld world) {
            world.spawnParticles(
                this.getParticle(),
                this.getX(),
                this.getBodyY(0.6666666666666666),
                this.getZ(),
                10,
                this.getWidth() / 4.0F,
                this.getHeight() / 4.0F,
                this.getWidth() / 4.0F,
                0.05
            );
        }
    }

    private void updateHealth(DamageSource damageSource, float amount) {
        float f = this.getHealth();
        f -= amount;
        if(f <= 0.5F) {
            this.onBreak(damageSource);
            this.kill();
        }
        else {
            this.setHealth(f);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getAttacker());
        }
    }

    private void breakAndDropThis(DamageSource damageSource) {
        Block.dropStack(this.getWorld(), this.getBlockPos(), this.getItem());
        this.onBreak(damageSource);
    }

    private void onBreak(DamageSource damageSource) {
        this.playBreakSound();
        this.drop(damageSource);
    }

    private void playBreakSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), this.getDeathSound(), this.getSoundCategory(), 1.0F, 1.0F);
    }

    private void readPoseNbt(NbtCompound nbt) {
        NbtList head = nbt.getList(HEAD_ROTATION_KEY, NbtElement.FLOAT_TYPE);
        NbtList body = nbt.getList(BODY_ROTATION_KEY, NbtElement.FLOAT_TYPE);
        NbtList leftArm = nbt.getList(LEFT_ARM_ROTATION_KEY, NbtElement.FLOAT_TYPE);
        NbtList rightArm = nbt.getList(RIGHT_ARM_ROTATION_KEY, NbtElement.FLOAT_TYPE);

        this.setHeadRotation(head.isEmpty() ? DEFAULT_HEAD_ROTATION : new EulerAngle(head));
        this.setBodyRotation(body.isEmpty() ? DEFAULT_BODY_ROTATION : new EulerAngle(body));
        this.setLeftArmRotation(leftArm.isEmpty() ? DEFAULT_LEFT_ARM_ROTATION : new EulerAngle(leftArm));
        this.setRightArmRotation(rightArm.isEmpty() ? DEFAULT_RIGHT_ARM_ROTATION : new EulerAngle(rightArm));
    }

    private NbtCompound poseToNbt() {
        NbtCompound nbt = new NbtCompound();
        if(!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            nbt.put(HEAD_ROTATION_KEY, this.headRotation.toNbt());
        }

        if(!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            nbt.put(BODY_ROTATION_KEY, this.bodyRotation.toNbt());
        }

        if(!DEFAULT_LEFT_ARM_ROTATION.equals(this.leftArmRotation)) {
            nbt.put(LEFT_ARM_ROTATION_KEY, this.leftArmRotation.toNbt());
        }

        if(!DEFAULT_RIGHT_ARM_ROTATION.equals(this.rightArmRotation)) {
            nbt.put(RIGHT_ARM_ROTATION_KEY, this.rightArmRotation.toNbt());
        }

        return nbt;
    }

    public EulerAngle getHeadRotation() {
        return this.headRotation;
    }

    public EulerAngle getBodyRotation() {
        return this.bodyRotation;
    }

    public EulerAngle getLeftArmRotation() {
        return this.leftArmRotation;
    }

    public EulerAngle getRightArmRotation() {
        return this.rightArmRotation;
    }

    public void setHeadRotation(EulerAngle angle) {
        this.headRotation = angle;
        this.dataTracker.set(HEAD_ROTATION, angle);
    }

    public void setBodyRotation(EulerAngle angle) {
        this.bodyRotation = angle;
        this.dataTracker.set(BODY_ROTATION, angle);
    }

    public void setLeftArmRotation(EulerAngle angle) {
        this.leftArmRotation = angle;
        this.dataTracker.set(LEFT_ARM_ROTATION, angle);
    }

    public void setRightArmRotation(EulerAngle angle) {
        this.rightArmRotation = angle;
        this.dataTracker.set(RIGHT_ARM_ROTATION, angle);
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public ItemStack getItem() {
        return FowlPlayItems.SCARECROW.get().getDefaultStack();
    }

    public ParticleEffect getParticle() {
        return new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.HAY_BLOCK.getDefaultState());
    }

    public SoundEvent getPlaceSound() {
        return SoundEvents.BLOCK_WOOD_PLACE;
    }

    @Override
    public FallSounds getFallSounds() {
        return new LivingEntity.FallSounds(SoundEvents.BLOCK_WOOD_FALL, SoundEvents.BLOCK_WOOD_FALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_WOOD_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    @Override
    public boolean isAffectedBySplashPotions() {
        return false;
    }

    @Override
    public boolean isMobOrPlayer() {
        return false;
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return this.getItem();
    }
}