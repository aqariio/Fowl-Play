package aqario.fowlplay.common.entity.bird.waterfowl;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.brain.BirdBrain;
import aqario.fowlplay.common.entity.ai.brain.behaviour.*;
import aqario.fowlplay.common.entity.ai.brain.sensor.*;
import aqario.fowlplay.common.entity.ai.navigation.AmphibiousNavigation;
import aqario.fowlplay.common.entity.bird.*;
import aqario.fowlplay.common.entity.bird.VariantHolder;
import aqario.fowlplay.common.entity.variant.GooseVariant;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.common.util.CylindricalRadius;
import aqario.fowlplay.core.*;
import aqario.fowlplay.core.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.core.tags.FowlPlayItemTags;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

public class GooseEntity extends TrustingBirdEntity implements BirdBrain<GooseEntity>, VariantHolder<GooseVariant>, Domesticatable, Flocking {
    private static final EntityDataAccessor<Holder<GooseVariant>> VARIANT = SynchedEntityData.defineId(
        GooseEntity.class,
        FowlPlayEntityDataSerializers.GOOSE_VARIANT
    );
    private static final EntityDataAccessor<Boolean> CLIPPED = SynchedEntityData.defineId(
        GooseEntity.class,
        EntityDataSerializers.BOOLEAN
    );
    private static final EntityDataAccessor<Boolean> DOMESTIC = SynchedEntityData.defineId(
        GooseEntity.class,
        EntityDataSerializers.BOOLEAN
    );
    private static final String AGGRESSIVE_KEY = "aggressive";
    private boolean aggressive;

    public GooseEntity(EntityType<? extends GooseEntity> entityType, Level world) {
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        GooseEntity child = FowlPlayEntityTypes.GOOSE.get().create(level);
        if(child != null && otherParent instanceof GooseEntity parent2) {
            Holder<GooseVariant> variant = BirdUtils.getRandomOf(child.getRandom(), this, parent2).getVariant();
            child.setVariant(variant);
            child.setDomestic(variant.value().domesticatable());
        }
        return child;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return this.getFood().test(stack);
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.45F : 1.0F;
    }

    @Override
    public boolean canStartFlying() {
        return !this.isBaby() && !this.hasClippedWings() && super.canStartFlying();
    }

    @Override
    public boolean shouldStopFlying() {
        return this.isBaby() || this.hasClippedWings() || super.shouldStopFlying();
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
    public Registry<GooseVariant> variantRegistry() {
        return FowlPlayBuiltInRegistries.GOOSE_VARIANT;
    }

    @Override
    public ResourceKey<Registry<GooseVariant>> variantRegistryKey() {
        return FowlPlayRegistries.GOOSE_VARIANT;
    }

    @Override
    public ResourceKey<GooseVariant> defaultVariant() {
        return GooseVariant.CANADA;
    }

    @Override
    public Holder<GooseVariant> getVariant() {
        return this.entityData.get(VARIANT);
    }

    @Override
    public void setVariant(Holder<GooseVariant> variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.writeClipped(nbt);
        this.writeDomestic(nbt);
        this.writeVariant(nbt);
        if(this.aggressive) {
            nbt.putBoolean(AGGRESSIVE_KEY, true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.readClipped(nbt);
        this.readDomestic(nbt);
        this.readVariant(nbt);
        if(nbt.contains(AGGRESSIVE_KEY, Tag.TAG_ANY_NUMERIC)) {
            this.aggressive = nbt.getBoolean(AGGRESSIVE_KEY);
        }
    }

    @Override
    public boolean isAggressive() {
        return this.aggressive && !this.isBaby();
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
        if(this.isDomestic()) {
            return false;
        }
        if(this.hasLowHealth()) {
            return false;
        }
        return BirdUtils.wasHurtBy(this, target);
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().is(FowlPlayEntityTypeTags.GOOSE_AVOIDS) && !this.isAggressive();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if(item.is(Items.SHEARS) && this.isDomestic() && !this.isBaby() && !this.hasClippedWings()) {
            if(!this.level().isClientSide()) {
                this.setClippedWings(true);
                this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, this.getSoundSource(), 1.0f, 1.0f);
                item.hurtAndBreak(1, player, getSlotForHand(hand));
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void updateAnimationStates() {
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
        if(this.getVariant().is(GooseVariant.GREYLAG)) {
            return FowlPlaySoundEvents.ENTITY_GREYLAG_GOOSE_CALL.get();
        }
        if(this.getVariant().is(GooseVariant.SWAN)) {
            return FowlPlaySoundEvents.ENTITY_SWAN_GOOSE_CALL.get();
        }
        return FowlPlaySoundEvents.ENTITY_CANADA_GOOSE_CALL.get();
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().gooseCallVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if(this.getVariant().is(GooseVariant.GREYLAG)) {
            return FowlPlaySoundEvents.ENTITY_GREYLAG_GOOSE_HURT.get();
        }
        if(this.getVariant().is(GooseVariant.SWAN)) {
            return FowlPlaySoundEvents.ENTITY_SWAN_GOOSE_HURT.get();
        }
        return FowlPlaySoundEvents.ENTITY_CANADA_GOOSE_HURT.get();
    }

    @Override
    public CylindricalRadius getWalkRange() {
        return this.isDomestic()
            ? new CylindricalRadius(64, 24)
            : new CylindricalRadius(32, 8);
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
    public BrainActivityGroup<? extends GooseEntity> coreActivity() {
        return BirdBrain.core(
            FlightBehaviours.stopFalling(),
            new SetAttackTarget<>(),
            new LookAtTarget<>()
                .runForBetween(45, 90),
            new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> avoidActivity() {
        return BirdBrain.avoid(
            CustomBehaviours.setAvoidEntityWalkTarget()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> fightActivity() {
        return BirdBrain.fight(
            new InvalidateAttackTarget<>(),
            new SetWalkTargetToAttackTarget<>()
                .speedMod((entity, target) -> BirdUtils.FAST_SPEED),
            new AnimatableMeleeAttack<>(0)
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> forageActivity() {
        return BirdBrain.forage(
            new BreedWithPartner<>(),
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
    public BrainActivityGroup<? extends GooseEntity> idleActivity() {
        return BirdBrain.idle(
            new BreedWithPartner<>(),
            new FollowParent<>(),
            SetEntityLookTarget.create(BirdUtils::isPlayerHoldingFood),
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
    public BrainActivityGroup<? extends GooseEntity> pickUpActivity() {
        return BirdBrain.pickUp(
            CompositeBehaviours.tryPickUpFood()
        );
    }

    @Override
    public BrainActivityGroup<? extends GooseEntity> restActivity() {
        return BirdBrain.rest(
            CompositeBehaviours.trySetWaterRestTarget(),
            CustomBehaviours.idleIfInWater()
        );
    }

    @Nullable
    @Override
    public SmartBrainSchedule getSchedule() {
        return this.isDomestic()
            ? FowlPlaySchedules.WATERFOWL_DOMESTIC.get()
            : FowlPlaySchedules.WATERFOWL.get();
    }

    @Override
    protected void customServerAiStep() {
        this.tickBrain(this);
        super.customServerAiStep();
    }
}
