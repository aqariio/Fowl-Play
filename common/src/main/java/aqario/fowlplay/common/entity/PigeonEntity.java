package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.sensor.AttackedSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.AvoidTargetSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.NearbyAdultsSensor;
import aqario.fowlplay.common.entity.ai.brain.sensor.PigeonSpecificSensor;
import aqario.fowlplay.common.entity.ai.brain.task.*;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.*;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.InvalidateMemory;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Panic;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FollowParent;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyItemsSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyAdultSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class PigeonEntity extends TameableBirdEntity implements SmartBrainOwner<PigeonEntity>, VariantHolder<PigeonVariant>, Flocking {
    private static final TrackedData<Optional<UUID>> RECIPIENT = DataTracker.registerData(
        PigeonEntity.class,
        TrackedDataHandlerRegistry.OPTIONAL_UUID
    );
    private static final TrackedData<PigeonVariant> VARIANT = DataTracker.registerData(
        PigeonEntity.class,
        FowlPlayTrackedDataHandlerRegistry.PIGEON_VARIANT
    );
    public final AnimationState standingState = new AnimationState();
    public final AnimationState glidingState = new AnimationState();
    public final AnimationState flappingState = new AnimationState();
    public final AnimationState floatingState = new AnimationState();
    public final AnimationState sittingState = new AnimationState();

    public PigeonEntity(EntityType<? extends PigeonEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -3.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 12.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5f;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        FowlPlayRegistries.PIGEON_VARIANT.get()
            .getRandom(world.getRandom())
            .ifPresent(variant -> this.setVariant(variant.value()));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty difficulty) {
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 1.0f);
        this.setEquipmentDropChance(EquipmentSlot.OFFHAND, 1.0f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RECIPIENT, Optional.empty());
        this.dataTracker.startTracking(VARIANT, PigeonVariant.BANDED.get());
    }

    @Override
    public PigeonVariant getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(PigeonVariant variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", FowlPlayRegistries.PIGEON_VARIANT.get().getId(this.getVariant()).toString());
        if(this.getRecipientUuid() != null) {
            nbt.putUuid("recipient", this.getRecipientUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        PigeonVariant variant = FowlPlayRegistries.PIGEON_VARIANT.get().get(Identifier.tryParse(nbt.getString("variant")));
        if(variant != null) {
            this.setVariant(variant);
        }
        if(nbt.containsUuid("recipient")) {
            this.setRecipientUuid(nbt.getUuid("recipient"));
        }
        else {
            this.setRecipientUuid(null);
        }
    }

    @Override
    public float getWaterline() {
        return 0.45F;
    }

    @Override
    public int getFlapFrequency() {
        return 7;
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

    public static DefaultAttributeContainer.Builder createPigeonAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.26f);
    }

    @Override
    protected EntityNavigation getLandNavigation() {
        MobNavigation mobNavigation = new MobNavigation(this, this.getWorld());
        mobNavigation.setCanPathThroughDoors(false);
        mobNavigation.setCanEnterOpenDoors(true);
        mobNavigation.setCanSwim(false);
        return mobNavigation;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack playerStack = player.getStackInHand(hand);
        ItemStack bundleStack = this.getStackInHand(Hand.OFF_HAND);

        // Equip bundle
        if(bundleStack.isEmpty() && playerStack.getItem() instanceof BundleItem && playerStack.hasCustomName() && this.isTamed()) {
            if(!this.getWorld().isClient) {
                this.setStackInHand(Hand.OFF_HAND, playerStack);
                player.setStackInHand(hand, ItemStack.EMPTY);
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        // Unequip bundle
        if(playerStack.isEmpty() && bundleStack.getItem() instanceof BundleItem) {
            if(!this.getWorld().isClient) {
                player.setStackInHand(hand, bundleStack);
                this.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        // Taming
        if(this.isBreedingItem(playerStack) && !this.isTamed()) {
            if(!this.getWorld().isClient) {
                this.eat(player, hand, playerStack);
                if(this.random.nextInt(4) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                }
                else {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        // Sitting
        if(this.isOnGround() && this.isTamed() && this.isOwner(player)) {
            if(!this.getWorld().isClient) {
                this.setSitting(!this.isSitting());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }

            return ActionResult.success(this.getWorld().isClient);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !this.isTamed() && this.getFood().test(stack);
    }

    @Override
    public boolean canEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = getPreferredEquipmentSlot(stack);
        if(!this.getEquippedStack(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND && super.canEquip(stack);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.fromTag(FowlPlayItemTags.PIGEON_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.PIGEON_AVOIDS);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        this.dropStack(this.getEquippedStack(EquipmentSlot.MAINHAND));
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.dropStack(this.getEquippedStack(EquipmentSlot.OFFHAND));
        this.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if(this.getWorld().isClient()) {
            this.standingState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn() && !this.isInSittingPose(), this.age);
            this.flappingState.setRunning(this.isFlying(), this.age);
            this.floatingState.setRunning(!this.isFlying() && this.isInsideWaterOrBubbleColumn(), this.age);
            this.sittingState.setRunning(this.isInSittingPose(), this.age);
        }

        super.tick();
    }

    @Override
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15f, 1.0f);
    }

    @Nullable
    public UUID getRecipientUuid() {
        return this.dataTracker.get(RECIPIENT).orElse(null);
    }

    public void setRecipientUuid(@Nullable UUID uuid) {
        this.dataTracker.set(RECIPIENT, Optional.ofNullable(uuid));
    }

    @Override
    public Vec3d getLeashOffset() {
        return new Vec3d(0.0, 0.5f * this.getStandingEyeHeight(), this.getWidth() * 0.4f);
    }

    @Override
    protected boolean canSing() {
        if(this.getWorld().isDay()) {
            return false;
        }
        List<PlayerEntity> list = this.getWorld()
            .getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(16.0, 16.0, 16.0), EntityPredicates.EXCEPT_SPECTATOR);
        if(list.isEmpty()) {
            return false;
        }
        return super.canSing();
    }

    @Nullable
    @Override
    protected SoundEvent getCallSound() {
        return FowlPlaySoundEvents.ENTITY_PIGEON_CALL.get();
    }

    @Nullable
    @Override
    protected SoundEvent getSongSound() {
        return FowlPlaySoundEvents.ENTITY_PIGEON_SONG.get();
    }

    @Override
    public int getCallDelay() {
        return 120;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().pigeonCallVolume;
    }

    @Override
    protected float getSongVolume() {
        return FowlPlayConfig.getInstance().pigeonSongVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    @Override
    protected Brain.Profile<PigeonEntity> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends PigeonEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyItemsSensor<>(),
            new NearbyAdultSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<PigeonEntity>()
                .setScanRate(bird -> 10),
            new AvoidTargetSensor<>(),
            new PigeonSpecificSensor()
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends PigeonEntity> getCoreTasks() {
        return new BrainActivityGroup<PigeonEntity>(Activity.CORE)
            .priority(0)
            .behaviours(
                new FloatToSurfaceOfFluid<>()
                    .riseChance(0.5F),
                FlightControlTask.stopFalling(),
                new TeleportToTargetTask(),
                new Panic<>(),
                new FollowOwnerTask(Birds.WALK_SPEED, 5, 10),
                AvoidTask.run(),
                PickupFoodTask.<PigeonEntity>run(Birds::canPickupFood)
                    .startCondition(pigeon -> !pigeon.isSitting() && pigeon.getRecipientUuid() == null),
                new LookAtTarget<>()
                    .runFor(entity -> entity.getRandom().nextBetween(45, 90)),
                new MoveToWalkTarget<>()
            );
    }

    @SuppressWarnings("unchecked")
    @Override
    public BrainActivityGroup<? extends PigeonEntity> getIdleTasks() {
        return new BrainActivityGroup<PigeonEntity>(Activity.IDLE)
            .priority(10)
            .behaviours(
                new BreedWithPartner<>(),
                new FollowParent<>(),
                FindLookTargetTask.create(Birds::isPlayerHoldingFood, 32.0F),
                SetWalkTargetToClosestAdult.create(Birds.STAY_NEAR_ENTITY_RANGE, Birds.WALK_SPEED),
                new SetRandomLookTarget<>()
                    .lookTime(entity -> entity.getRandom().nextBetween(150, 250)),
                new OneRandomBehaviour<>(
                    Pair.of(
                        new SetRandomWalkTarget<PigeonEntity>()
                            .speedModifier((entity, target) -> Birds.WALK_SPEED)
                            .setRadius(16, 8)
                            .startCondition(Predicate.not(Birds::isPerched)),
                        4
                    ),
                    Pair.of(
                        new Idle<PigeonEntity>()
                            .runFor(entity -> entity.getRandom().nextBetween(100, 300)),
                        3
                    ),
                    Pair.of(
                        FlightControlTask.<PigeonEntity>startFlying(entity -> !entity.isSitting() && entity.getRandom().nextFloat() < 0.1F),
                        1
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.RECIPIENT.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends PigeonEntity> getFlyTasks() {
        return new BrainActivityGroup<PigeonEntity>(FowlPlayActivities.FLY.get())
            .priority(10)
            .behaviours(
                new LeaderlessFlockTask(
                    5,
                    0.03f,
                    0.6f,
                    0.05f,
                    3f
                ),
                new OneRandomBehaviour<>(
                    Pair.of(
                        TargetlessFlyTask.perch(Birds.FLY_SPEED),
                        1
                    )
                ).startCondition(entity -> !BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_FLYING.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.RECIPIENT.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends PigeonEntity> getDeliverTasks() {
        return new BrainActivityGroup<PigeonEntity>(FowlPlayActivities.DELIVER.get())
            .priority(10)
            .behaviours(
                FlightControlTask.<PigeonEntity>stopFlying()
                    .startCondition(PigeonEntity::shouldStopFlyingToRecipient),
                FlightControlTask.startFlying(PigeonEntity::shouldFlyToRecipient),
                DeliverBundleTask.run(Birds.truePredicate(), pigeon -> pigeon.isFlying() ? Birds.FLY_SPEED : Birds.WALK_SPEED)
            )
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.RECIPIENT.get());
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends PigeonEntity> getAvoidTasks() {
        return new BrainActivityGroup<PigeonEntity>(Activity.AVOID)
            .priority(10)
            .behaviours(
                FlightControlTask.startFlying(),
                MoveAwayFromTargetTask.entity(
                    MemoryModuleType.AVOID_TARGET,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true
                ),
                AvoidTask.forget()
            )
            .requireAndWipeMemoriesOnUse(FowlPlayMemoryModuleType.IS_AVOIDING.get());
    }

    @SuppressWarnings("unchecked")
    public BrainActivityGroup<? extends PigeonEntity> getPickupFoodTasks() {
        return new BrainActivityGroup<PigeonEntity>(FowlPlayActivities.PICK_UP.get())
            .priority(10)
            .behaviours(
                FlightControlTask.startFlying(pigeon -> !pigeon.isTamed() && Birds.canPickupFood(pigeon)),
                GoToNearestWantedItemTask.create(
                    Birds::canPickupFood,
                    entity -> entity.isFlying() ? Birds.FLY_SPEED : Birds.RUN_SPEED,
                    true,
                    Birds.ITEM_PICK_UP_RANGE
                ),
                new InvalidateMemory<PigeonEntity, Boolean>(FowlPlayMemoryModuleType.SEES_FOOD.get())
                    .invalidateIf((entity, memory) -> !Birds.canPickupFood(entity))
            )
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.SEES_FOOD.get(), MemoryModuleState.VALUE_PRESENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.IS_AVOIDING.get(), MemoryModuleState.VALUE_ABSENT)
            .onlyStartWithMemoryStatus(FowlPlayMemoryModuleType.RECIPIENT.get(), MemoryModuleState.VALUE_ABSENT);
    }

    @Override
    public Map<Activity, BrainActivityGroup<? extends PigeonEntity>> getAdditionalTasks() {
        Object2ObjectOpenHashMap<Activity, BrainActivityGroup<? extends PigeonEntity>> taskList = new Object2ObjectOpenHashMap<>();
        taskList.put(FowlPlayActivities.FLY.get(), this.getFlyTasks());
        taskList.put(FowlPlayActivities.DELIVER.get(), this.getDeliverTasks());
        taskList.put(Activity.AVOID, this.getAvoidTasks());
        taskList.put(FowlPlayActivities.PICK_UP.get(), this.getPickupFoodTasks());
        return taskList;
    }

    @Override
    public List<Activity> getActivityPriorities() {
        return ObjectArrayList.of(
            Activity.IDLE,
            FowlPlayActivities.FLY.get(),
            FowlPlayActivities.DELIVER.get(),
            Activity.AVOID,
            FowlPlayActivities.PICK_UP.get()
        );
    }

    private static boolean shouldFlyToRecipient(PigeonEntity pigeon) {
        UUID recipientUuid = pigeon.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.RECIPIENT.get()).orElse(null);
        if(recipientUuid == null) {
            return false;
        }
        PlayerEntity recipient = pigeon.getWorld().getPlayerByUuid(recipientUuid);
        if(recipient == null) {
            return false;
        }
        return pigeon.squaredDistanceTo(recipient) > 64;
    }

    private static boolean shouldStopFlyingToRecipient(PigeonEntity pigeon) {
        UUID recipientUuid = pigeon.getBrain().getOptionalRegisteredMemory(FowlPlayMemoryModuleType.RECIPIENT.get()).orElse(null);
        if(recipientUuid == null) {
            return true;
        }
        PlayerEntity recipient = pigeon.getWorld().getPlayerByUuid(recipientUuid);
        if(recipient == null) {
            return true;
        }
        return pigeon.squaredDistanceTo(recipient) < 16;
    }

    @Override
    protected void mobTick() {
        this.tickBrain(this);
        super.mobTick();

        if(this.getServer() == null) {
            return;
        }

        if(!this.isTamed()) {
            return;
        }

        ItemStack stack = this.getEquippedStack(EquipmentSlot.OFFHAND);
        ServerPlayerEntity recipient = this.getServer().getPlayerManager().getPlayer(stack.getName().getString());

        if(!(stack.getItem() instanceof BundleItem) || !stack.hasCustomName() || recipient == null || recipient.getUuid() == null) {
            this.setRecipientUuid(null);
            return;
        }

        this.setRecipientUuid(recipient.getUuid());
    }
}
