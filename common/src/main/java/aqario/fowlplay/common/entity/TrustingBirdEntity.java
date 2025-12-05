package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlayEntityDataSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TrustingBirdEntity extends FlyingBirdEntity {
    protected static final EntityDataAccessor<List<UUID>> TRUSTED = SynchedEntityData.defineId(TrustingBirdEntity.class, FowlPlayEntityDataSerializers.UUID_LIST);

    protected TrustingBirdEntity(EntityType<? extends BirdEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public int getFleeRange(LivingEntity target) {
        return !this.getTrustedUuids().isEmpty() && target instanceof Player ? 8 : super.getFleeRange(target);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TRUSTED, new ArrayList<>());
    }

    protected ListTag toNbtList(List<UUID> uuids) {
        ListTag nbtList = new ListTag();

        for(UUID uuid : uuids) {
            nbtList.add(NbtUtils.createUUID(uuid));
        }

        return nbtList;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("trusted", this.toNbtList(this.getTrustedUuids()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if(nbt.contains("trusted")) {
            ListTag list = (ListTag) nbt.get("trusted");
            if(list != null) {
                list.forEach(element -> this.addTrustedUuid(NbtUtils.loadUUID(element)));
            }
        }
    }

    @Override
    protected void pickUpItem(ItemEntity item) {
        super.pickUpItem(item);
        UUID thrower = item.getOwner() != null ? item.getOwner().getUUID() : null;
        if(thrower != null && !this.trustsUuid(thrower)) {
            if(this.random.nextInt(3) == 0) {
                this.addTrustedUuid(thrower);
                this.level().broadcastEntityEvent(this, EntityEvent.VILLAGER_HAPPY);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if(status == EntityEvent.VILLAGER_HAPPY) {
            if(this.forcedAgeTimer == 0) {
                this.forcedAgeTimer = 20;
            }
        }
        else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return this.trusts(player) ? super.mobInteract(player, hand) : InteractionResult.PASS;
    }

    @Override
    protected boolean shouldBeAmbient() {
        return super.shouldBeAmbient() && this.getTrustedUuids().isEmpty();
    }

    public List<UUID> getTrustedUuids() {
        return this.entityData.get(TRUSTED);
    }

    public void addTrustedUuid(UUID uuid) {
        List<UUID> trusted = this.entityData.get(TRUSTED);
        trusted.add(uuid);
        this.entityData.set(TRUSTED, trusted);
    }

    public void removeTrustedUuid(UUID uuid) {
        List<UUID> trusted = this.entityData.get(TRUSTED);
        trusted.remove(uuid);
        this.entityData.set(TRUSTED, trusted);
    }

    public void stopTrusting(Player player) {
        this.removeTrustedUuid(player.getUUID());
    }

    public List<Player> getTrusted() {
        List<UUID> uuids = this.getTrustedUuids();
        List<Player> entities = new ArrayList<>();
        for(UUID uuid : uuids) {
            entities.add(this.level().getPlayerByUUID(uuid));
        }
        return entities;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return (!(target instanceof Player player) || !this.trusts(player)) && super.canAttack(target);
    }

    public boolean trusts(Player player) {
        return this.getTrusted().contains(player);
    }

    public boolean trustsUuid(UUID uuid) {
        return this.getTrustedUuids().contains(uuid);
    }
}
