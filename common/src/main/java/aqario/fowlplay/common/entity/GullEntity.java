package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.control.BirdFloatMoveControl;
import aqario.fowlplay.common.entity.ai.navigation.AmphibiousNavigation;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.core.*;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
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
import java.util.Optional;

public class GullEntity extends TrustingBirdEntity implements BirdBrain<GullEntity>, VariantHolder<Holder<GullVariant>> {
    private static final EntityDataAccessor<Holder<GullVariant>> VARIANT = SynchedEntityData.defineId(
        GullEntity.class,
        FowlPlayEntityDataSerializers.GULL_VARIANT
    );

    public GullEntity(EntityType<? extends GullEntity> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.WATER_BORDER, 0.0f);
        this.setPathfindingMalus(PathType.WATER, 0.0f);
    }

    @Override
    protected MoveControl createMoveControl() {
        return new BirdFloatMoveControl(this);
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        FowlPlayBuiltInRegistries.GULL_VARIANT.getRandom(level.getRandom()).ifPresent(this::setVariant);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, FowlPlayBuiltInRegistries.GULL_VARIANT.getHolderOrThrow(GullVariant.HERRING));
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
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("variant", this.getVariant().unwrapKey().orElse(GullVariant.HERRING).location().toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        Optional.ofNullable(ResourceLocation.tryParse(nbt.getString("variant")))
            .map(variant -> ResourceKey.create(FowlPlayRegistries.GULL_VARIANT, variant))
            .flatMap(FowlPlayBuiltInRegistries.GULL_VARIANT::getHolder)
            .ifPresent(this::setVariant);
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
    public void updateAnimations() {
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
        return new SmartBrainProvider<>(this);
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
            new AttackTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getFightTasks() {
        return BirdBrain.fightActivity(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0),
            CustomBehaviours.forgetUnderwaterAttackTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                Pair.of(
                    CompositeBehaviours.trySetNonAirWalkTarget(),
                    1
                ),
                Pair.of(
                    CustomBehaviours.idleIfNotFlying()
                        .runForBetween(100, 300),
                    2
                )
            ),
            new SetRandomFlightTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getIdleTasks() {
        return BirdBrain.idleActivity(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTarget.create(Birds::isPlayerHoldingFood),
            new SetRandomLookTarget<>()
                .lookChance(0.02f),
            new OneRandomBehaviour<>(
                CompositeBehaviours.trySetNonAirWalkTarget(),
                CustomBehaviours.idleIfNotFlying()
                    .runForBetween(100, 300)
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends GullEntity> getRestTasks() {
        return BirdBrain.restActivity(
            CompositeBehaviours.trySetWaterRestTarget(),
            CustomBehaviours.idleIfInWater()
        );
    }

    @Override
    public BrainActivityGroup<GullEntity> getSoarTasks() {
        return BirdBrain.soarActivity(
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
        Brain<?> brain = this.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        this.tickBrain(this);
        if(activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
        super.customServerAiStep();
    }
}
