package aqario.fowlplay.common.entity;

import aqario.fowlplay.core.FowlPlayTrackedDataHandlerRegistry;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TrustingBirdEntity extends FlyingBirdEntity {
    protected static final TrackedData<List<UUID>> TRUSTED = DataTracker.registerData(TrustingBirdEntity.class, FowlPlayTrackedDataHandlerRegistry.UUID_LIST);

    protected TrustingBirdEntity(EntityType<? extends BirdEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRUSTED, new ArrayList<>());
    }

    protected NbtList toNbtList(List<UUID> uuids) {
        NbtList nbtList = new NbtList();

        for (UUID uuid : uuids) {
            nbtList.add(NbtHelper.fromUuid(uuid));
        }

        return nbtList;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("trusted", this.toNbtList(this.getTrustedUuids()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("trusted")) {
            NbtList list = (NbtList) nbt.get("trusted");
            if (list != null) {
                list.forEach(element -> this.addTrustedUuid(NbtHelper.toUuid(element)));
            }
        }
    }

    @Override
    protected void loot(ItemEntity item) {
        super.loot(item);
        UUID thrower = item.getOwner() != null ? item.getOwner().getUuid() : null;
        if (thrower != null && !this.trustsUuid(thrower)) {
            if (this.random.nextInt(3) == 0) {
                this.addTrustedUuid(thrower);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_VILLAGER_HAPPY_PARTICLES);
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.ADD_VILLAGER_HAPPY_PARTICLES) {
            if (this.happyTicksRemaining == 0) {
                this.happyTicksRemaining = 20;
            }
        }
        else {
            super.handleStatus(status);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAmbient() && !this.getTrustedUuids().isEmpty()) {
            this.setAmbient(false);
        }
    }

    public List<UUID> getTrustedUuids() {
        return this.dataTracker.get(TRUSTED);
    }

    public void addTrustedUuid(UUID uuid) {
        List<UUID> trusted = this.dataTracker.get(TRUSTED);
        trusted.add(uuid);
        this.dataTracker.set(TRUSTED, trusted);
    }

    public void removeTrustedUuid(UUID uuid) {
        List<UUID> trusted = this.dataTracker.get(TRUSTED);
        trusted.remove(uuid);
        this.dataTracker.set(TRUSTED, trusted);
    }

    public void stopTrusting(PlayerEntity player) {
        this.removeTrustedUuid(player.getUuid());
    }

    public List<PlayerEntity> getTrusted() {
        List<UUID> uuids = this.getTrustedUuids();
        List<PlayerEntity> entities = new ArrayList<>();
        for (UUID uuid : uuids) {
            entities.add(this.getWorld().getPlayerByUuid(uuid));
        }
        return entities;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return (!(target instanceof PlayerEntity player) || !this.trusts(player)) && super.canTarget(target);
    }

    public boolean trusts(PlayerEntity player) {
        return this.getTrusted().contains(player);
    }

    public boolean trustsUuid(UUID uuid) {
        return this.getTrustedUuids().contains(uuid);
    }
}
