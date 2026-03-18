package aqario.fowlplay.common.entity.bird.dove;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.navigation.GroundNavigation;
import aqario.fowlplay.common.entity.bird.Flocking;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TameableBirdEntity;
import aqario.fowlplay.common.entity.variant.PigeonVariant;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.*;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.InWaterSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PigeonEntity extends TameableBirdEntity implements BirdBrain<PigeonEntity>, VariantHolder<Holder<PigeonVariant>>, Flocking {
    private static final EntityDataAccessor<Optional<UUID>> RECIPIENT = SynchedEntityData.defineId(
        PigeonEntity.class,
        EntityDataSerializers.OPTIONAL_UUID
    );
    private static final EntityDataAccessor<Holder<PigeonVariant>> VARIANT = SynchedEntityData.defineId(
        PigeonEntity.class,
        FowlPlayEntityDataSerializers.PIGEON_VARIANT
    );
    public final AnimationState sittingState = new AnimationState();

    public PigeonEntity(EntityType<? extends PigeonEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        float f = level.getRandom().nextFloat();
        if(f < 0.5f) { // 50% chance for banded
            FowlPlayBuiltInRegistries.PIGEON_VARIANT.getHolder(PigeonVariant.BANDED).ifPresent(this::setVariant);
        }
        else if(f < 0.75f) { // 25% chance for checkered
            FowlPlayBuiltInRegistries.PIGEON_VARIANT.getHolder(PigeonVariant.CHECKERED).ifPresent(this::setVariant);
        }
        else if(f < 0.95f) { // 20% chance for gray
            FowlPlayBuiltInRegistries.PIGEON_VARIANT.getHolder(PigeonVariant.GRAY).ifPresent(this::setVariant);
        }
        else if(f < 0.99f) { // 4% chance for rusty
            FowlPlayBuiltInRegistries.PIGEON_VARIANT.getHolder(PigeonVariant.RUSTY).ifPresent(this::setVariant);
        }
        else { // 1% chance for white
            FowlPlayBuiltInRegistries.PIGEON_VARIANT.getHolder(PigeonVariant.WHITE).ifPresent(this::setVariant);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setDropChance(EquipmentSlot.MAINHAND, 1.0f);
        this.setDropChance(EquipmentSlot.OFFHAND, 1.0f);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RECIPIENT, Optional.empty());
        builder.define(VARIANT, FowlPlayBuiltInRegistries.PIGEON_VARIANT.getHolderOrThrow(PigeonVariant.BANDED));
    }

    @Override
    public Holder<PigeonVariant> getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<PigeonVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("variant", this.getVariant().unwrapKey().orElse(PigeonVariant.BANDED).location().toString());
        if(this.getRecipientUuid() != null) {
            nbt.putUUID("recipient", this.getRecipientUuid());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        Optional.ofNullable(ResourceLocation.tryParse(nbt.getString("variant")))
            .map(variant -> ResourceKey.create(FowlPlayRegistries.PIGEON_VARIANT, variant))
            .flatMap(FowlPlayBuiltInRegistries.PIGEON_VARIANT::getHolder)
            .ifPresent(this::setVariant);

        if(nbt.hasUUID("recipient")) {
            this.setRecipientUuid(nbt.getUUID("recipient"));
        }
        else {
            this.setRecipientUuid(null);
        }
    }

    @Override
    public Pair<Integer, Integer> getFlyHeightRange() {
        return Pair.of(10, 12);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    public static AttributeSupplier.Builder createPigeonAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(Attributes.MAX_HEALTH, 8.0)
            .add(Attributes.MOVEMENT_SPEED, 0.2f)
            .add(Attributes.FLYING_SPEED, 0.26f);
    }

    @Override
    protected PathNavigation getLandNavigation() {
        GroundNavigation navigation = new GroundNavigation(this, this.level());
        navigation.setCanOpenDoors(false);
        navigation.setCanPassDoors(true);
        navigation.setCanFloat(false);
        return navigation;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);
        ItemStack bundleStack = this.getItemInHand(InteractionHand.OFF_HAND);

        // Equip bundle
        if(bundleStack.isEmpty() && playerStack.getItem() instanceof BundleItem && playerStack.getComponents().has(DataComponents.CUSTOM_NAME) && this.isTamed()) {
            if(!this.level().isClientSide) {
                this.setItemInHand(InteractionHand.OFF_HAND, playerStack);
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Unequip bundle
        if(playerStack.isEmpty() && bundleStack.getItem() instanceof BundleItem) {
            if(!this.level().isClientSide) {
                player.setItemInHand(hand, bundleStack);
                this.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Taming
        if(this.isFood(playerStack) && !this.isTamed()) {
            if(!this.level().isClientSide) {
                this.usePlayerItem(player, hand, playerStack);
                if(this.random.nextInt(4) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
                }
                else {
                    this.level().broadcastEntityEvent(this, EntityEvent.TAMING_FAILED);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Sitting
        if(this.onGround() && this.isTamed() && this.isOwner(player)) {
            if(!this.level().isClientSide) {
                this.setSitting(!this.isSitting());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return !this.isTamed() && this.getFood().test(stack);
    }

    @Override
    public boolean canTakeItem(ItemStack stack) {
        EquipmentSlot equipmentSlot = this.getEquipmentSlotForItem(stack);
        if(!this.getItemBySlot(equipmentSlot).isEmpty()) {
            return false;
        }
        return equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND && super.canTakeItem(stack);
    }

    @Override
    public Ingredient getFood() {
        return Ingredient.of(FowlPlayItemTags.PIGEON_FOOD);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.PIGEON_AVOIDS);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();

        this.spawnAtLocation(this.getItemBySlot(EquipmentSlot.MAINHAND));
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        this.spawnAtLocation(this.getItemBySlot(EquipmentSlot.OFFHAND));
        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
    }

    @Override
    public void updateAnimations() {
        this.standingState.animateWhen(!this.isFlying() && !this.isInWaterOrBubble() && !this.isInSittingPose(), this.tickCount);
        this.flappingState.animateWhen(this.isFlying(), this.tickCount);
        this.swimmingState.animateWhen(!this.isFlying() && this.isInWaterOrBubble(), this.tickCount);
        this.sittingState.animateWhen(this.isInSittingPose(), this.tickCount);
    }

    @Override
    public float getFlapVolume() {
        return 0.65f;
    }

    @Override
    public float getFlapPitch() {
        return 0.9f;
    }

    @Nullable
    public UUID getRecipientUuid() {
        return this.entityData.get(RECIPIENT).orElse(null);
    }

    public void setRecipientUuid(@Nullable UUID uuid) {
        this.entityData.set(RECIPIENT, Optional.ofNullable(uuid));
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.5f * this.getEyeHeight(), this.getBbWidth() * 0.4f);
    }

    @Override
    protected boolean canSing() {
        if(this.level().isDay()) {
            return false;
        }
        List<Player> list = this.level()
            .getEntitiesOfClass(Player.class, this.getAttackBoundingBox().inflate(16.0, 16.0, 16.0), EntitySelector.NO_SPECTATORS);
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
        return FowlPlaySoundEvents.ENTITY_PIGEON_HURT.get();
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    @Override
    protected Brain.Provider<PigeonEntity> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends PigeonEntity>> getSensors() {
        return ObjectArrayList.of(
            new NearbyLivingEntitySensor<>(),
            new NearbyPlayersSensor<>(),
            new NearbyFoodSensor<>(),
            new NearbyAdultsSensor<>(),
            new InWaterSensor<>(),
            new AttackedSensor<>(),
            new AvoidTargetSensor<>(),
            new PigeonSpecificSensor()
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getCoreTasks() {
        return BirdBrain.coreActivity(
            new FloatToSurfaceOfFluid<>()
                .riseChance(0.5F),
            FlightBehaviours.stopFalling(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
            new TeleportToTarget(),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
                .startCondition(entity -> !BrainUtils.hasMemory(entity, FowlPlayMemoryTypes.TELEPORT_TARGET.get()))
                .stopIf(entity -> BrainUtils.hasMemory(entity, FowlPlayMemoryTypes.TELEPORT_TARGET.get()))
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getAvoidTasks() {
        return BirdBrain.avoidActivity(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getDeliverTasks() {
        return BirdBrain.deliverActivity(
            FlightBehaviours.<PigeonEntity>stopFlying()
                .startCondition(PigeonEntity::shouldStopFlyingToRecipient),
            FlightBehaviours.<PigeonEntity>startFlying()
                .startCondition(PigeonEntity::shouldFlyToRecipient),
            DeliverBundle.run()
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getFollowTasks() {
        return BirdBrain.followActivity(
            new SetOwnerWalkOrTpTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getForageTasks() {
        return BirdBrain.forageActivity(
            new OneRandomBehaviour<>(
                CompositeBehaviours.tryForage(),
                CompositeBehaviours.tryPerch()
            )
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getPerchTasks() {
        return BirdBrain.perchActivity(
            new LeaderlessFlocking(
                5,
                0.03f,
                0.6f,
                0.05f,
                3f
            ),
            CompositeBehaviours.tryPerch()
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getPickupFoodTasks() {
        return BirdBrain.pickupFoodActivity(
            CompositeBehaviours.<PigeonEntity>tryPickUpFood()
                .startCondition(pigeon -> !pigeon.isSitting())
        );
    }

    @Override
    public BrainActivityGroup<? extends PigeonEntity> getRestTasks() {
        return BirdBrain.restActivity(
            CompositeBehaviours.trySetPerchRestTarget(),
            CustomBehaviours.idleIfPerched()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return FowlPlaySchedules.FORAGER.get();
    }

    private static boolean shouldFlyToRecipient(PigeonEntity pigeon) {
        if(!pigeon.isMemoryPresent(FowlPlayMemoryTypes.RECIPIENT.get())) {
            return false;
        }
        UUID recipientUuid = pigeon.getPresentMemory(FowlPlayMemoryTypes.RECIPIENT.get());
        Player recipient = pigeon.level().getPlayerByUUID(recipientUuid);
        if(recipient == null) {
            return false;
        }
        return pigeon.distanceToSqr(recipient) > 64;
    }

    private static boolean shouldStopFlyingToRecipient(PigeonEntity pigeon) {
        if(!pigeon.isMemoryPresent(FowlPlayMemoryTypes.RECIPIENT.get())) {
            return true;
        }
        UUID recipientUuid = pigeon.getPresentMemory(FowlPlayMemoryTypes.RECIPIENT.get());
        Player recipient = pigeon.level().getPlayerByUUID(recipientUuid);
        if(recipient == null) {
            return true;
        }
        return pigeon.distanceToSqr(recipient) < 16;
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();

        if(this.isTamed() && this.getServer() != null) {
            ItemStack stack = this.getItemBySlot(EquipmentSlot.OFFHAND);
            ServerPlayer recipient = this.getServer().getPlayerList().getPlayerByName(stack.getHoverName().getString());

            if(!(stack.getItem() instanceof BundleItem) || !stack.getComponents().has(DataComponents.CUSTOM_NAME) || recipient == null) {
                this.setRecipientUuid(null);
            }
            else {
                this.setRecipientUuid(recipient.getUUID());
            }
        }
    }
}
