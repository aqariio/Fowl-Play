package aqario.fowlplay.common.entity.bird.waterfowl;

import aqario.fowlplay.common.entity.ExtendedBrainProvider;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyFoodSensor;
import aqario.fowlplay.common.entity.ai.navigation.AmphibiousNavigation;
import aqario.fowlplay.common.entity.bird.*;
import aqario.fowlplay.common.entity.bird.VariantHolder;
import aqario.fowlplay.common.entity.variant.DuckVariant;
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
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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

public class DuckEntity extends TrustingBirdEntity implements BirdBrain<DuckEntity>, VariantHolder<DuckVariant>, Domesticatable, Flocking {
    private static final EntityDataAccessor<Holder<DuckVariant>> VARIANT = SynchedEntityData.defineId(
        DuckEntity.class,
        FPEntityDataSerializers.DUCK_VARIANT
    );
    private static final EntityDataAccessor<Boolean> CLIPPED = SynchedEntityData.defineId(
        DuckEntity.class,
        EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> DOMESTIC = SynchedEntityData.defineId(
        DuckEntity.class,
        EntityDataSerializers.BOOLEAN
    );

    public DuckEntity(EntityType<? extends DuckEntity> entityType, Level world) {
        super(entityType, world);
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
        return Pair.of(18, 24);
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
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        DuckEntity child = FPEntityTypes.DUCK.get().create(level);
        if(child != null && otherParent instanceof DuckEntity parent2) {
            Holder<DuckVariant> variant = Utils.getRandomOf(child.getRandom(), this, parent2).getVariant();
            child.setVariant(variant);
            child.setDomestic(true);
        }
        return child;
    }

    @Override
    protected boolean canSwim() {
        return true;
    }

    public static AttributeSupplier.Builder createDuckAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.MAX_HEALTH, 10.0f)
            .add(Attributes.ATTACK_DAMAGE, 1.0f)
            .add(Attributes.MOVEMENT_SPEED, 0.225f)
            .add(Attributes.FLYING_SPEED, 0.22f)
            .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5f);
    }

    @Override
    public boolean isDomestic() {
        return this.entityData.get(DOMESTIC);
    }

    @Override
    public void setDomestic(boolean domestic) {
        this.entityData.set(DOMESTIC, domestic);
    }

    @Override
    public boolean hasClippedWings() {
        return this.entityData.get(CLIPPED);
    }

    @Override
    public void setClippedWings(boolean clipped) {
        this.entityData.set(CLIPPED, clipped);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CLIPPED, false);
        builder.define(DOMESTIC, false);
        this.defineVariant(builder, VARIANT);
    }

    @Override
    public Registry<DuckVariant> variantRegistry() {
        return FPBuiltInRegistries.DUCK_VARIANT;
    }

    @Override
    public ResourceKey<Registry<DuckVariant>> variantRegistryKey() {
        return FPRegistries.DUCK_VARIANT;
    }

    @Override
    public ResourceKey<DuckVariant> defaultVariant() {
        return DuckVariant.GREEN_HEADED;
    }

    @Override
    public Holder<DuckVariant> getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<DuckVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.writeClipped(nbt);
        this.writeDomestic(nbt);
        this.writeVariant(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.readClipped(nbt);
        this.readDomestic(nbt);
        this.readVariant(nbt);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.DUCK_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.DUCK_AVOIDS);
    }

    @Override
    public void updateAnimationStates() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
        this.flappingState.animateWhen(this.isFlying(), this.tickCount);
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
        return FPSoundEvents.DUCK_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FPSoundEvents.DUCK_HURT.get();
    }

    @Override
    public CylindricalRadius getWalkRange() {
        return new CylindricalRadius(32, 8);
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    @Override
    protected Brain.Provider<DuckEntity> brainProvider() {
        return new ExtendedBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends DuckEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> coreActivity() {
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
    public BrainActivityGroup<? extends DuckEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> fightActivity() {
        return BirdBrain.fight(
            new InvalidateAttackTarget<>(),
            FlightBehaviours.startFlying(),
            new SetWalkTargetToAttackTarget<>(),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> forageActivity() {
        return BirdBrain.forage(
            new OneRandomBehaviour<>(
                Pair.of(
                    CompositeBehaviours.trySetWaterWalkTarget(),
                    1
                ),
                Pair.of(
                    CustomBehaviours.idleIfNotMoving()
                        .runForBetween(100, 300),
                    2
                )
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> idleActivity() {
        return BirdBrain.idle(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new SetRandomLookTarget<>()
                .lookChance(0.02f),
            new OneRandomBehaviour<>(
                CompositeBehaviours.trySetWaterWalkTarget(),
                CustomBehaviours.idleIfNotMoving()
                    .runForBetween(100, 300)
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends DuckEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetWaterRestTarget(),
            CustomBehaviours.sleepIfInWater()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FPSchedules.WATERFOWL.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
