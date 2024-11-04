package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.entity.data.FowlPlayTrackedDataHandlerRegistry;
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
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TRUSTED, new ArrayList<>());
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
        if (!nbt.contains("trusted")) {
            return;
        }
        NbtList list = (NbtList) nbt.get("trusted");
        if (list != null) {
            list.forEach(element -> this.addTrustedUuid(NbtHelper.toUuid(element)));
        }
    }

    @Override
    protected void loot(ItemEntity item) {
        super.loot(item);
        UUID thrower = item.getOwner() != null ? item.getOwner().getUuid() : null;
        if (thrower != null && !this.isTrustedUuid(thrower)) {
            if (this.random.nextInt(3) == 0) {
                this.addTrustedUuid(thrower);
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
            }
            else {
                this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
            }
        }
    }

    protected void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = positive ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for (int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.getWorld().addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.showEmoteParticle(true);
        }
        else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.showEmoteParticle(false);
        }
        else {
            super.handleStatus(status);
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
        return (!(target instanceof PlayerEntity player) || !this.isTrusted(player)) && super.canTarget(target);
    }

    public boolean isTrusted(PlayerEntity player) {
        return this.getTrusted().contains(player);
    }

    public boolean isTrustedUuid(UUID uuid) {
        return this.getTrustedUuids().contains(uuid);
    }
}
