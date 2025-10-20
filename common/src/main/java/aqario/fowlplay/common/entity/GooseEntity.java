package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.entity.ai.control.BirdFloatMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.AmphibiousNavigation;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlayRegistries;
import aqario.fowlplay.core.FowlPlaySchedules;
import aqario.fowlplay.core.FowlPlaySoundEvents;
import aqario.fowlplay.core.FowlPlayTrackedDataHandlerRegistry;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import aqario.fowlplay.core.tags.FowlPlayVariantTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomSwimTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetAttackTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class GooseEntity extends TrustingBirdEntity implements BirdBrain<GooseEntity>, VariantHolder<GooseVariant>, Flocking {
    private static final TrackedData<GooseVariant> VARIANT = DataTracker.registerData(
        GooseEntity.class,
        FowlPlayTrackedDataHandlerRegistry.GOOSE_VARIANT
    );
    private static final String AGGRESSIVE_KEY = "aggressive";
    private boolean aggressive;
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();

    public GooseEntity(EntityType<? extends GooseEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.9f;
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
    protected EntityNavigation getLandNavigation() {
        return new AmphibiousNavigation(this, this.getWorld());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        switch(spawnReason) {
            case BREEDING -> FowlPlayRegistries.GOOSE_VARIANT.get()
                .fowlplay$getRandomEntry(FowlPlayVariantTags.Goose.DOMESTIC, world.getRandom())
                .ifPresent(this::setVariant);

            case CHUNK_GENERATION, NATURAL -> FowlPlayRegistries.GOOSE_VARIANT.get()
                .fowlplay$getRandomEntry(FowlPlayVariantTags.Goose.NATURAL, world.getRandom())
                .ifPresent(this::setVariant);

            default -> FowlPlayRegistries.GOOSE_VARIANT.get()
                .fowlplay$getRandom(world.getRandom())
                .ifPresent(this::setVariant);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected boolean canSwim() {
        return true;
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createGooseAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.5f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.22f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, GooseVariant.GREYLAG.get());
    }

    @Override
    public GooseVariant getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(GooseVariant variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", FowlPlayRegistries.GOOSE_VARIANT.get().fowlplay$getId(this.getVariant()).toString());
        if(this.aggressive) {
            nbt.putBoolean(AGGRESSIVE_KEY, true);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        GooseVariant variant = FowlPlayRegistries.GOOSE_VARIANT.get().fowlplay$get(Identifier.tryParse(nbt.getString("variant")));
        if(variant != null) {
            this.setVariant(variant);
        }
        if(nbt.contains(AGGRESSIVE_KEY, NbtElement.NUMBER_TYPE)) {
            this.aggressive = nbt.getBoolean(AGGRESSIVE_KEY);
        }
    }

    public boolean isAggressive() {
        return this.aggressive;
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

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return super.canPickupItem(stack) || (this.isAggressive() && stack.getItem() instanceof SwordItem);
    }

    @Override
    public boolean shouldDropBeakItem(ItemStack stack) {
        return super.shouldDropBeakItem(stack) && !(this.isAggressive() && stack.getItem() instanceof SwordItem);
    }

    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.GOOSE_FOOD);
    }

    @Override
    public boolean shouldAttack(LivingEntity target) {
        if(this.isAggressive()) {
            return target instanceof PlayerEntity;
        }
        if(this.hasLowHealth()) {
            return false;
        }
        return Birds.wasHurtBy(this, target);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.GOOSE_AVOIDS) && !this.isAggressive();
    }

    @Override
    public void updateAnimations() {
        this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
        this.flappingState.setRunning(this.isFlying(), this.age);
        this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if(!this.aggressive && name != null && name.getString().equalsIgnoreCase("untitled")) {
            this.aggressive = true;
        }
    }

    @Override
    protected boolean isFlappingWings() {
        return this.isFlying();
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
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
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
    public SquareRadius getWalkRange() {
        return new SquareRadius(24, 12);
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    @Override
    protected Brain.Profile<GooseEntity> createBrainProfile() {
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
            new AttackedSensor<GooseEntity>()
                .setScanRate(bird -> 10),
            new AvoidTargetSensor<GooseEntity>()
                .setScanRate(bird -> 10),
            new AttackTargetSensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            FlightTasks.stopFalling(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CompositeTasks.setAvoidEntityWalkTarget()
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

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends GooseEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new LeaderlessFlockTask(
                5,
                0.04f,
                0.6f,
                0.06f,
                3f
            ),
            new OneRandomBehaviour<>(
                Pair.of(
                    CompositeTasks.setWaterfowlForagingTarget(),
                    1
                ),
                Pair.of(
                    new Idle<FlyingBirdEntity>()
                        .runFor(entity -> entity.getRandom().nextBetween(100, 300))
                        .startCondition(Predicate.not(FlyingBirdEntity::isFlying)),
                    2
                )
            )
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends GooseEntity> getIdleTasks() {
        return BirdBrain.idleActivity(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTargetTask.create(Birds::isPlayerHoldingFood),
            new LookAroundTask<>()
                .lookChance(0.02f),
            new OneRandomBehaviour<>(
                new SetRandomSwimTarget<>()
                    .setRadius(24, 8),
                new Idle<GooseEntity>()
                    .runFor(entity -> entity.getRandom().nextBetween(100, 300))
                    .startCondition(entity -> !entity.isFlying())
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeTasks.setNearestFoodWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> getRestTasks() {
        return BirdBrain.restActivity(
            CompositeTasks.setWaterWalkTarget(),
            CompositeTasks.idleIfInWater()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.WATERFOWL.get();
    }

    @Override
    protected void mobTick() {
        this.tickBrain(this);
        super.mobTick();
    }
}
