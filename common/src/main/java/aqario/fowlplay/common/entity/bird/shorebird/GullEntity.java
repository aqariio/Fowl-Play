package aqario.fowlplay.common.entity.bird.shorebird;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ExtendedBrainProvider;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.navigation.AmphibiousNavigation;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.common.entity.bird.VariantHolder;
import aqario.fowlplay.common.entity.variant.GullVariant;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.common.util.Utils;
import aqario.fowlplay.core.*;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class GullEntity extends TrustingBirdEntity implements BirdBrain<GullEntity>, VariantHolder<GullVariant> {
    private static final EntityDataAccessor<Holder<GullVariant>> VARIANT = SynchedEntityData.defineId(
        GullEntity.class,
        FowlPlayEntityDataSerializers.GULL_VARIANT
    );

    public GullEntity(EntityType<? extends GullEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.WATER_BORDER, 0.0f);
        this.setPathfindingMalus(PathType.WATER, 0.0f);
    }

    @Override
    protected boolean canFloat() {
        return true;
    }

    @Override
    public int getMaxPitchChange() {
        return 18;
    }

    @Override
    public int getMaxYawChange() {
        return 18;
    }

    @Override
    public Pair<Integer, Integer> getFlyHeightRange() {
        return Pair.of(24, 32);
    }

    @Override
    protected PathNavigation getLandNavigation() {
        return new AmphibiousNavigation(this, this.level())
            .setSurfaceOnly();
    }

    @Override
    public SpawnGroupData finalizeSpawn(
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        MobSpawnType spawnType,
        @Nullable SpawnGroupData spawnGroupData
    ) {
        this.withRandomVariant(level.getRandom(), this::setVariant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected boolean canSwim() {
        return true;
    }

    public static AttributeSupplier.Builder createGullAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.MAX_HEALTH, 10.0f)
            .add(Attributes.ATTACK_DAMAGE, 1.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.225f)
            .add(Attributes.FLYING_SPEED, 0.22f)
            .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5f);
    }

    @Override
    public Registry<GullVariant> variantRegistry() {
        return FowlPlayBuiltInRegistries.GULL_VARIANT;
    }

    @Override
    public ResourceKey<Registry<GullVariant>> variantRegistryKey() {
        return FowlPlayRegistries.GULL_VARIANT;
    }

    @Override
    public ResourceKey<GullVariant> defaultVariant() {
        return GullVariant.HERRING;
    }

    @Override
    public Holder<GullVariant> getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<GullVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        this.defineVariant(builder, VARIANT);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.writeVariant(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.readVariant(nbt);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.GULL_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().is(FowlPlayEntityTypeTags.GULL_HUNT_TARGETS) ||
            (target.getType().is(FowlPlayEntityTypeTags.GULL_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.GULL_AVOIDS);
    }

    @Override
    public void updateAnimationStates() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
        this.glidingState.animateWhen(this.isFlying(), this.tickCount);
        this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public float getFlapVolume() {
        return 0.8f;
    }

    @Override
    public float getFlapPitch() {
        return 0.6f;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.5f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_GULL_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_GULL_LONG_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().gullCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().gullSongVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_GULL_HURT.get();
    }

    @Override
    public CylindricalRadius getWalkRange() {
        return new CylindricalRadius(24, 8);
    }

    @Override
    protected Brain.Provider<GullEntity> brainProvider() {
        return new ExtendedBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends GullEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>(),
            new HuntTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> coreActivity() {
        return BirdBrain.core(
            new WakeUp<>(),
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> fightActivity() {
        return BirdBrain.fight(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0),
            CustomBehaviours.forgetUnderwaterAttackTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> forageActivity() {
        return BirdBrain.forage(
            new SetHuntTarget<>()
                .targetPredicate(Utils.not(BirdUtils::isSelfAndTargetInWater)),
            new OneRandomBehaviour<>(
                Pair.of(
                    CompositeBehaviours.trySetNonAirWalkTarget(),
                    1
                ),
                Pair.of(
                    CustomBehaviours.idleIfNotMoving()
                        .runForBetween(100, 300),
                    2
                )
            ),
            new SetRandomFlightTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> idleActivity() {
        return BirdBrain.idle(
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new SetRandomLookTarget<GullEntity>()
                .lookChance(0.02f)
                .startCondition(Predicate.not(FlyingBirdEntity::isFlying))
                .stopIf(FlyingBirdEntity::isFlying),
            new OneRandomBehaviour<>(
                CompositeBehaviours.trySetNonAirWalkTarget(),
                CustomBehaviours.idleIfNotMoving()
                    .runForBetween(100, 300)
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetWaterRestTarget(),
            CustomBehaviours.sleepIfInWater()
        );
    }

    @Override
    public BrainActivityGroup<GullEntity> soarActivity() {
        return BirdBrain.soar(
            new SetHuntTarget<>()
                .targetPredicate(Utils.not(BirdUtils::isSelfAndTargetInWater)),
            new SetRandomFlightTarget<>()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.SEABIRD.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
