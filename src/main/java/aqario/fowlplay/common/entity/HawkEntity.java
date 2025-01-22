package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import aqario.fowlplay.common.tags.FowlPlayBlockTags;
import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HawkEntity extends TrustingBirdEntity {
    public final AnimationState idleState = new AnimationState();
    public final AnimationState glideState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState floatState = new AnimationState();
    private int timeSinceLastFlap = this.getFlapFrequency();
    private int flapTime = 0;

    public HawkEntity(EntityType<? extends HawkEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBiome(pos).isIn(FowlPlayBiomeTags.SPAWNS_HAWKS) && world.getBlockState(pos.down()).isIn(FowlPlayBlockTags.PASSERINES_SPAWNABLE_ON);
    }

    @Override
    public int getFlapFrequency() {
        return 100;
    }

    public static DefaultAttributeContainer.Builder createHawkAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.235f);
    }

    @Override
    protected BirdFlightMoveControl getFlightMoveControl() {
        return new BirdFlightMoveControl(this, 12, 8);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.getWorld().isClient) {
            return false;
        }
        if (bl && source.getAttacker() instanceof LivingEntity entity) {
            HawkBrain.onAttacked(this, entity);
        }

        return bl;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.HAWK_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.HAWK_AVOIDS);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().isIn(FowlPlayEntityTypeTags.HAWK_HUNT_TARGETS) ||
            (target.getType().isIn(FowlPlayEntityTypeTags.HAWK_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        Optional<LivingEntity> hurtBy = this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HURT_BY_ENTITY);
        return hurtBy.isPresent() && hurtBy.get().equals(target);
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() != StatusEffects.HUNGER && super.canHaveStatusEffect(effect);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            this.idleState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
            if (this.isFlying()) {
                if (this.timeSinceLastFlap > this.getFlapFrequency()) {
                    this.timeSinceLastFlap = 0;
                    this.flapTime++;
                }
                else if (this.flapTime > 0 && this.flapTime < 60) {
                    this.flapTime++;
                    this.glideState.stop();
                    this.flapState.startIfNotRunning(this.age);
                }
                else {
                    this.timeSinceLastFlap++;
                    this.flapTime = 0;
                    this.flapState.stop();
                    this.glideState.startIfNotRunning(this.age);
                }
            }
            else {
                this.timeSinceLastFlap = this.getFlapFrequency();
                this.flapTime = 0;
                this.flapState.stop();
                this.glideState.stop();
            }
            this.floatState.setRunning(this.isInsideWaterOrBubbleColumn(), this.age);
        }

        super.tick();
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
        return FowlPlaySoundEvents.ENTITY_HAWK_CALL;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().hawkCallVolume;
    }

    @Override
    public int getCallDelay() {
        return 800;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_HAWK_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected Brain.Profile<HawkEntity> createBrainProfile() {
        return HawkBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return HawkBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<HawkEntity> getBrain() {
        return (Brain<HawkEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("hawkBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("hawkActivityUpdate");
        HawkBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }
}
