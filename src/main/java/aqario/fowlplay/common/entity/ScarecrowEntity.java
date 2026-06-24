package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FPItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class ScarecrowEntity extends LivingEntity implements ContainerListener, MenuProvider {
    private static final Predicate<Entity> RIDEABLE_MINECART_PREDICATE = entity -> entity instanceof AbstractMinecart
        && ((AbstractMinecart) entity).getMinecartType() == AbstractMinecart.Type.RIDEABLE;
    private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_LEFT_ARM_ROTATION = new Rotations(0.0F, 0.0F, -90.0F);
    private static final Rotations DEFAULT_RIGHT_ARM_ROTATION = new Rotations(0.0F, 0.0F, 90.0F);
    public static final EntityDataAccessor<Rotations> HEAD_ROTATION = SynchedEntityData.defineId(ScarecrowEntity.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> BODY_ROTATION = SynchedEntityData.defineId(ScarecrowEntity.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> LEFT_ARM_ROTATION = SynchedEntityData.defineId(ScarecrowEntity.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> RIGHT_ARM_ROTATION = SynchedEntityData.defineId(ScarecrowEntity.class, EntityDataSerializers.ROTATIONS);
    private Rotations headRotation = DEFAULT_HEAD_ROTATION;
    private Rotations bodyRotation = DEFAULT_BODY_ROTATION;
    private Rotations leftArmRotation = DEFAULT_LEFT_ARM_ROTATION;
    private Rotations rightArmRotation = DEFAULT_RIGHT_ARM_ROTATION;
    private static final String POSE_KEY = "pose";
    private static final String HEAD_ROTATION_KEY = "head";
    private static final String BODY_ROTATION_KEY = "body";
    private static final String LEFT_ARM_ROTATION_KEY = "left_arm";
    private static final String RIGHT_ARM_ROTATION_KEY = "right_arm";
    private static final String ITEMS_KEY = "items";
    private static final int INVENTORY_SIZE = 4;
    private static final int HEAD_SLOT = 0;
    private static final int CHEST_SLOT = 1;
    private static final int MAINHAND_SLOT = 2;
    private static final int OFFHAND_SLOT = 3;
    protected SimpleContainer inventory;

    public ScarecrowEntity(EntityType<? extends ScarecrowEntity> entityType, Level world) {
        super(entityType, world);
        this.inventory = new SimpleContainer(INVENTORY_SIZE);
        this.inventory.addListener(this);
    }

    public static AttributeSupplier.Builder createScarecrowAttributes() {
        return createLivingAttributes()
            .add(Attributes.STEP_HEIGHT, 0.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected float tickHeadTurn(float bodyRotation, float headRotation) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
        return 0.0F;
    }

    @Override
    public void setYBodyRot(float bodyYaw) {
        this.yBodyRotO = this.yRotO = bodyYaw;
        this.yHeadRotO = this.yHeadRot = bodyYaw;
    }

    @Override
    public void setYHeadRot(float headYaw) {
        this.yBodyRotO = this.yRotO = headYaw;
        this.yHeadRotO = this.yHeadRot = headYaw;
    }

    @Override
    public void containerChanged(Container sender) {
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();

        if(this.inventory != null) {
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemStack = this.inventory.getItem(i);
                if(itemStack.isEmpty()) {
                    continue;
                }
                this.spawnAtLocation(itemStack);
            }
        }
    }

    @Override
    public Iterable<ItemStack> getHandSlots() {
        return ImmutableList.of(this.inventory.getItem(MAINHAND_SLOT), this.inventory.getItem(OFFHAND_SLOT));
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of(this.inventory.getItem(HEAD_SLOT), this.inventory.getItem(CHEST_SLOT));
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return switch(slot) {
            case HEAD -> this.inventory.getItem(HEAD_SLOT);
            case CHEST -> this.inventory.getItem(CHEST_SLOT);
            case MAINHAND -> this.inventory.getItem(MAINHAND_SLOT);
            case OFFHAND -> this.inventory.getItem(OFFHAND_SLOT);
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        this.verifyEquippedItem(stack);
        switch(slot) {
            case HEAD -> this.inventory.setItem(HEAD_SLOT, stack);
            case CHEST -> this.inventory.setItem(CHEST_SLOT, stack);
            case MAINHAND -> this.inventory.setItem(MAINHAND_SLOT, stack);
            case OFFHAND -> this.inventory.setItem(OFFHAND_SLOT, stack);
        }
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        EquipmentSlot equipmentSlot = this.getEquipmentSlotForItem(stack);
        return this.getItemBySlot(equipmentSlot).isEmpty();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HEAD_ROTATION, DEFAULT_HEAD_ROTATION);
        builder.define(BODY_ROTATION, DEFAULT_BODY_ROTATION);
        builder.define(LEFT_ARM_ROTATION, DEFAULT_LEFT_ARM_ROTATION);
        builder.define(RIGHT_ARM_ROTATION, DEFAULT_RIGHT_ARM_ROTATION);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put(ITEMS_KEY, this.inventory.createTag(this.registryAccess()));
        nbt.put(POSE_KEY, this.poseToNbt());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if(nbt.contains(ITEMS_KEY, Tag.TAG_LIST)) {
            this.inventory.fromTag(nbt.getList(ITEMS_KEY, Tag.TAG_COMPOUND), this.registryAccess());
        }
        CompoundTag poseNbt = nbt.getCompound(POSE_KEY);
        this.readPoseNbt(poseNbt);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(!player.level().isClientSide() && player.getItemInHand(hand).isEmpty() && !player.isSecondaryUseActive()) {
            return InteractionResult.CONSUME;
        }
        return super.interact(player, hand);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player playerEntity) {
        return null;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    protected void pushEntities() {
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), RIDEABLE_MINECART_PREDICATE);
        for(Entity entity : list) {
            if(this.distanceToSqr(entity) <= 0.2) {
                entity.push(this);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(this.level().isClientSide() || this.isRemoved()) {
            return false;
        }
        if(source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if(this.isInvulnerableTo(source)) {
            return false;
        }
        if(source.getDirectEntity() instanceof FireworkRocketEntity) {
            return false;
        }
        if(source.is(DamageTypeTags.IS_EXPLOSION)) {
            this.updateHealth(source, amount);
            return false;
        }
        if(source.is(DamageTypes.LAVA)) {
            this.igniteForSeconds(10);
            if(this.isOnFire()) {
                this.updateHealth(source, 1.0F);
            }
            return false;
        }
        if(source.is(DamageTypeTags.IS_FIRE)) {
            this.igniteForSeconds(5);
            if(this.isOnFire()) {
                this.updateHealth(source, 0.05F);
            }
            return false;
        }
        if(source.getDirectEntity() instanceof AbstractArrow) {
            return true;
        }
        if(source.getEntity() instanceof Player player) {
            if(!player.isShiftKeyDown()) {
                this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
                return true;
            }
            if(!player.getAbilities().mayBuild) {
                return false;
            }
            if(source.isCreativePlayer()) {
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
        return damageSource.is(DamageTypeTags.IS_FALL);
    }

    @Override
    public void thunderHit(ServerLevel world, LightningBolt lightning) {
    }

    @Override
    public void kill() {
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    protected void spawnBreakParticles() {
        if(this.level() instanceof ServerLevel world) {
            world.sendParticles(
                this.getParticle(),
                this.getX(),
                this.getY(0.6666666666666666),
                this.getZ(),
                10,
                this.getBbWidth() / 4.0F,
                this.getBbHeight() / 4.0F,
                this.getBbWidth() / 4.0F,
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
            this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        }
    }

    private void breakAndDropThis(DamageSource damageSource) {
        Block.popResource(this.level(), this.blockPosition(), this.getItem());
        this.onBreak(damageSource);
    }

    private void onBreak(DamageSource damageSource) {
        this.playBreakSound();
        this.dropAllDeathLoot((ServerLevel) this.level(), damageSource);
    }

    private void playBreakSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), this.getDeathSound(), this.getSoundSource(), 1.0F, 1.0F);
    }

    private void readPoseNbt(CompoundTag nbt) {
        ListTag head = nbt.getList(HEAD_ROTATION_KEY, Tag.TAG_FLOAT);
        ListTag body = nbt.getList(BODY_ROTATION_KEY, Tag.TAG_FLOAT);
        ListTag leftArm = nbt.getList(LEFT_ARM_ROTATION_KEY, Tag.TAG_FLOAT);
        ListTag rightArm = nbt.getList(RIGHT_ARM_ROTATION_KEY, Tag.TAG_FLOAT);

        this.setHeadRotation(head.isEmpty() ? DEFAULT_HEAD_ROTATION : new Rotations(head));
        this.setBodyRotation(body.isEmpty() ? DEFAULT_BODY_ROTATION : new Rotations(body));
        this.setLeftArmRotation(leftArm.isEmpty() ? DEFAULT_LEFT_ARM_ROTATION : new Rotations(leftArm));
        this.setRightArmRotation(rightArm.isEmpty() ? DEFAULT_RIGHT_ARM_ROTATION : new Rotations(rightArm));
    }

    private CompoundTag poseToNbt() {
        CompoundTag nbt = new CompoundTag();
        if(!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            nbt.put(HEAD_ROTATION_KEY, this.headRotation.save());
        }

        if(!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            nbt.put(BODY_ROTATION_KEY, this.bodyRotation.save());
        }

        if(!DEFAULT_LEFT_ARM_ROTATION.equals(this.leftArmRotation)) {
            nbt.put(LEFT_ARM_ROTATION_KEY, this.leftArmRotation.save());
        }

        if(!DEFAULT_RIGHT_ARM_ROTATION.equals(this.rightArmRotation)) {
            nbt.put(RIGHT_ARM_ROTATION_KEY, this.rightArmRotation.save());
        }

        return nbt;
    }

    public Rotations getHeadRotation() {
        return this.headRotation;
    }

    public Rotations getBodyRotation() {
        return this.bodyRotation;
    }

    public Rotations getLeftArmRotation() {
        return this.leftArmRotation;
    }

    public Rotations getRightArmRotation() {
        return this.rightArmRotation;
    }

    public void setHeadRotation(Rotations angle) {
        this.headRotation = angle;
        this.entityData.set(HEAD_ROTATION, angle);
    }

    public void setBodyRotation(Rotations angle) {
        this.bodyRotation = angle;
        this.entityData.set(BODY_ROTATION, angle);
    }

    public void setLeftArmRotation(Rotations angle) {
        this.leftArmRotation = angle;
        this.entityData.set(LEFT_ARM_ROTATION, angle);
    }

    public void setRightArmRotation(Rotations angle) {
        this.rightArmRotation = angle;
        this.entityData.set(RIGHT_ARM_ROTATION, angle);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public ItemStack getItem() {
        return FPItems.SCARECROW.get().getDefaultInstance();
    }

    public ParticleOptions getParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.HAY_BLOCK.defaultBlockState());
    }

    public SoundEvent getPlaceSound() {
        return SoundEvents.WOOD_PLACE;
    }

    @Override
    public Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.WOOD_FALL, SoundEvents.WOOD_FALL);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOOD_HIT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOOD_BREAK;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.getItem();
    }
}