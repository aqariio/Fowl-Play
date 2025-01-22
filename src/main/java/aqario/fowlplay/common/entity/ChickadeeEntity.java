package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChickadeeEntity extends FlyingBirdEntity {
    public final AnimationState idleState = new AnimationState();
    public final AnimationState glideState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState floatState = new AnimationState();

    public ChickadeeEntity(EntityType<? extends ChickadeeEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
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

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.CHICKADEE_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.CHICKADEE_AVOIDS);
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
            this.idleState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.flapState.setRunning(this.isFlying(), this.age);
            this.floatState.setRunning(this.isInsideWaterOrBubbleColumn(), this.age);
        }

        super.tick();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.getWorld().isClient) {
            return false;
        }
        if (bl && source.getAttacker() instanceof LivingEntity entity) {
            ChickadeeBrain.onAttacked(this, entity);
        }

        return bl;
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_CHICKADEE_CALL;
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_CHICKADEE_SONG;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().chickadeeCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().chickadeeSongVolume;
    }

    @Override
    public int getCallDelay() {
        return 480;
    }

    @Override
    public int getSongDelay() {
        return 240;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_CHICKADEE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected Brain.Profile<ChickadeeEntity> createBrainProfile() {
        return ChickadeeBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ChickadeeBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<ChickadeeEntity> getBrain() {
        return (Brain<ChickadeeEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("chickadeeBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("chickadeeActivityUpdate");
        ChickadeeBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }
}
