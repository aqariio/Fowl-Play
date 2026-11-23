package aqario.fowlplay.common.entity.bird;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class TameableBirdEntity extends TrustingBirdEntity implements OwnableEntity {
    protected static final EntityDataAccessor<Byte> TAMEABLE_FLAGS = SynchedEntityData.defineId(
        TameableBirdEntity.class,
        EntityDataSerializers.BYTE
    );
    protected static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(
        TameableBirdEntity.class,
        EntityDataSerializers.OPTIONAL_UUID
    );
    private static final String OWNER_KEY = "owner";
    private static final String SITTING_KEY = "sitting";
    private boolean sitting;

    protected TameableBirdEntity(EntityType<? extends BirdEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TAMEABLE_FLAGS, (byte) 0);
        builder.define(OWNER, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if(this.getOwnerUUID() != null) {
            nbt.putUUID(OWNER_KEY, this.getOwnerUUID());
        }

        nbt.putBoolean(SITTING_KEY, this.sitting);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if(nbt.hasUUID(OWNER_KEY)) {
            UUID uuid = nbt.getUUID(OWNER_KEY);
            this.setOwnerUuid(uuid);
            this.setTamed(true);
        }

        this.sitting = nbt.getBoolean(SITTING_KEY);
        this.setInSittingPose(this.sitting);
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    @Override
    public boolean handleLeashAtDistance(Entity leashHolder, float distance) {
        if(this.isInSittingPose()) {
            if(distance > 10.0F) {
                this.dropLeash(true, true);
            }
            return false;
        }
        return super.handleLeashAtDistance(leashHolder, distance);
    }

    protected void spawnTamingParticles(boolean positive) {
        ParticleOptions particleEffect = ParticleTypes.HEART;
        if(!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleEffect, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, e, f);
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if(status == EntityEvent.TAMING_SUCCEEDED) {
            this.spawnTamingParticles(true);
        }
        else if(status == EntityEvent.TAMING_FAILED) {
            this.spawnTamingParticles(false);
        }
        else {
            super.handleEntityEvent(status);
        }
    }

    public boolean isTamed() {
        return (this.entityData.get(TAMEABLE_FLAGS) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b = this.entityData.get(TAMEABLE_FLAGS);
        if(tamed) {
            this.entityData.set(TAMEABLE_FLAGS, (byte) (b | 4));
        }
        else {
            this.entityData.set(TAMEABLE_FLAGS, (byte) (b & -5));
        }
    }

    public boolean isInSittingPose() {
        return (this.entityData.get(TAMEABLE_FLAGS) & 1) != 0;
    }

    public void setInSittingPose(boolean inSittingPose) {
        byte b = this.entityData.get(TAMEABLE_FLAGS);
        if(inSittingPose) {
            this.entityData.set(TAMEABLE_FLAGS, (byte) (b | 1));
        }
        else {
            this.entityData.set(TAMEABLE_FLAGS, (byte) (b & -2));
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean bl = super.hurt(source, amount);
        if(!this.level().isClientSide() && bl) {
            this.setSitting(false);
        }
        return bl;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getOwnerUUID() != null) {
            if(!this.trustsUuid(this.getOwnerUUID())) {
                this.addTrustedUuid(this.getOwnerUUID());
            }
            if(!this.isPersistenceRequired()) {
                this.setPersistenceRequired();
            }
        }
        if(this.isFlying()) {
            this.setSitting(false);
        }
        if(!this.level().isClientSide()) {
            if(this.isSitting()) {
                this.getNavigation().stop();
                this.setInSittingPose(true);
            }
            else {
                this.setInSittingPose(false);
            }
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.entityData.set(OWNER, Optional.ofNullable(uuid));
    }

    public void setOwner(Player player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUUID());
        if(player instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer) player, this);
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level().getPlayerByUUID(uuid);
        }
        catch(IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !this.isOwnedBy(target) && super.canAttack(target);
    }

    public boolean isOwnedBy(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Override
    public PlayerTeam getTeam() {
        if(this.isTamed()) {
            LivingEntity owner = this.getOwner();
            if(owner != null) {
                return owner.getTeam();
            }
        }

        return super.getTeam();
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        if(this.isTamed()) {
            LivingEntity owner = this.getOwner();
            if(other == owner) {
                return true;
            }

            if(owner != null) {
                return owner.isAlliedTo(other);
            }
        }

        return super.isAlliedTo(other);
    }

    @Override
    public void die(DamageSource source) {
        if(!this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
            this.getOwner().sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }

        super.die(source);
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }
}
