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
import aqario.fowlplay.core.tags.FowlPlayVariantTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
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

public class GooseEntity extends TrustingBirdEntity implements BirdBrain<GooseEntity>, VariantHolder<Holder<GooseVariant>>, Flocking {
    private static final EntityDataAccessor<Holder<GooseVariant>> VARIANT = SynchedEntityData.defineId(
        GooseEntity.class,
        FowlPlayEntityDataSerializers.GOOSE_VARIANT
    );
    private static final String AGGRESSIVE_KEY = "aggressive";
    private static final String VARIANT_KEY = "variant";
    private boolean aggressive;

    public GooseEntity(EntityType<? extends GooseEntity> entityType, Level world) {
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
        return Pair.of(18, 24);
    }

    @Override
    protected PathNavigation getLandNavigation() {
        return new AmphibiousNavigation(this, this.level());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        FowlPlayBuiltInRegistries.GOOSE_VARIANT
            .getRandomElementOf(FowlPlayVariantTags.Goose.NATURAL, world.getRandom())
            .ifPresent(this::setVariant);

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        GooseEntity goose = FowlPlayEntityTypes.GOOSE.get().create(world);
        if(goose != null && entity instanceof GooseEntity parent2) {
            getRandomOf(goose.getRandom(), this, parent2).getVariant().value().domesticId()
                .flatMap(FowlPlayBuiltInRegistries.GOOSE_VARIANT::getHolder)
                .ifPresent(goose::setVariant);
        }
        return goose;
    }

    @SafeVarargs
    private static <T> T getRandomOf(RandomSource random, T... selections) {
        return selections[random.nextInt(selections.length)];
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.4F : 1.0F;
    }

    @Override
    public boolean canStartFlying() {
        return !this.isBaby() && super.canStartFlying();
    }

    @Override
    protected boolean canSwim() {
        return true;
    }

    public static AttributeSupplier.Builder createGooseAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.MAX_HEALTH, 10.0f)
            .add(Attributes.ATTACK_DAMAGE, 1.5f)
            .add(Attributes.MOVEMENT_SPEED, 0.23f)
            .add(Attributes.FLYING_SPEED, 0.22f)
            .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5f);
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetFromBrain();
    }

    public boolean isDomestic() {
        return this.getVariant().is(FowlPlayVariantTags.Goose.DOMESTIC);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, FowlPlayBuiltInRegistries.GOOSE_VARIANT.getHolderOrThrow(GooseVariant.CANADA));
    }

    @Override
    public Holder<GooseVariant> getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<GooseVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    public ResourceKey<GooseVariant> getVariantKey() {
        return this.getVariant().unwrapKey().orElse(GooseVariant.CANADA);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString(VARIANT_KEY, this.getVariantKey().location().toString());
        if(this.aggressive) {
            nbt.putBoolean(AGGRESSIVE_KEY, true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        Optional.ofNullable(ResourceLocation.tryParse(nbt.getString(VARIANT_KEY)))
            .map(variant -> ResourceKey.create(FowlPlayRegistries.GOOSE_VARIANT, variant))
            .flatMap(FowlPlayBuiltInRegistries.GOOSE_VARIANT::getHolder)
            .ifPresent(this::setVariant);
        if(nbt.contains(AGGRESSIVE_KEY, Tag.TAG_ANY_NUMERIC)) {
            this.aggressive = nbt.getBoolean(AGGRESSIVE_KEY);
        }
    }

    public boolean isAggressive() {
        return this.aggressive;
    }

    @Override
    public boolean canHoldItem(ItemStack stack) {
        return super.canHoldItem(stack) || (this.isAggressive() && stack.getItem() instanceof SwordItem);
    }

    @Override
    public boolean shouldDropBeakItem(ItemStack stack) {
        return super.shouldDropBeakItem(stack) && !(this.isAggressive() && stack.getItem() instanceof SwordItem);
    }

    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.GOOSE_FOOD);
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.isAggressive()) {
            return target instanceof Player;
        }
        if(this.hasLowHealth()) {
            return false;
        }
        return Birds.wasHurtBy(this, target);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.GOOSE_AVOIDS) && !this.isAggressive();
    }

    @Override
    public void updateAnimations() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble(), this.tickCount);
        this.flappingState.animateWhen(this.isFlying(), this.tickCount);
        this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        if(!this.aggressive && name != null && name.getString().equalsIgnoreCase("untitled")) {
            this.aggressive = true;
        }
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
        return FowlPlaySoundEvents.ENTITY_GOOSE_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().gooseCallVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_GOOSE_HURT.get();
    }

    @Override
    public float getWaterline() {
        return 0.35F;
    }

    @Override
    public CylindricalRadius getWalkRange() {
        return this.isDomestic()
            ? new CylindricalRadius(64, 12)
            : new CylindricalRadius(32, 12);
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    @Override
    protected Brain.Provider<GooseEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends GooseEntity>> getSensors() {
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
    public BrainActivityGroup<? extends GooseEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getFightTasks() {
        return BirdBrain.fightActivity(
            new InvalidateAttackTarget<>(),
            new SetWalkTargetToAttackTarget<>()
                .speedMod((entity, target) -> Birds.FAST_SPEED),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new LeaderlessFlocking(
                5,
                0.04f,
                0.6f,
                0.06f,
                3f
            ),
            new OneRandomBehaviour<>(
                Pair.of(
                    CompositeBehaviours.trySetWaterWalkTarget(),
                    1
                ),
                Pair.of(
                    CustomBehaviours.idleIfNotFlying()
                        .runForBetween(100, 300),
                    2
                )
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getIdleTasks() {
        return BirdBrain.idleActivity(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTarget.create(Birds::isPlayerHoldingFood),
            new SetRandomLookTarget<>()
                .lookChance(0.02f),
            new OneRandomBehaviour<>(
                CompositeBehaviours.trySetWaterWalkTarget(),
                CustomBehaviours.idleIfNotFlying()
                    .runForBetween(100, 300)
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getRestTasks() {
        return BirdBrain.restActivity(
            CompositeBehaviours.trySetWaterRestTarget(),
            CustomBehaviours.idleIfInWater()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.WATERFOWL.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
