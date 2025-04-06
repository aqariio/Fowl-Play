package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.control.BirdFlightMoveControl;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class HawkEntity extends TrustingBirdEntity {
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    private int timeSinceLastFlap = this.getFlapFrequency();
    private int flapTime = 0;

    public HawkEntity(EntityType<? extends HawkEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.7f;
    }

    @Override
    public int getFlapFrequency() {
        return 100;
    }

    public static DefaultAttributeContainer.Builder createHawkAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.27f);
    }

    @Override
    protected BirdFlightMoveControl getFlightMoveControl() {
        return new BirdFlightMoveControl(this, 15, 10);
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
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return effect.getEffectType() != StatusEffects.HUNGER && super.canHaveStatusEffect(effect);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
            if (this.isFlying()) {
                if (this.timeSinceLastFlap > this.getFlapFrequency()) {
                    this.timeSinceLastFlap = 0;
                    this.flapTime++;
                }
                else if (this.flapTime > 0 && this.flapTime < 60) {
                    this.flapTime++;
                    this.glidingState.stop();
                    this.flappingState.startIfNotRunning(this.age);
                }
                else {
                    this.timeSinceLastFlap++;
                    this.flapTime = 0;
                    this.flappingState.stop();
                    this.glidingState.startIfNotRunning(this.age);
                }
            }
            else {
                this.timeSinceLastFlap = this.getFlapFrequency();
                this.flapTime = 0;
                this.flappingState.stop();
                this.glidingState.stop();
            }
            this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
        }

        super.tick();
    }

    @Override
    public float getWaterline() {
        return 0.5F;
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
