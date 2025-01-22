package aqario.fowlplay.common.entity;

import aqario.fowlplay.common.config.FowlPlayConfig;
import aqario.fowlplay.common.entity.ai.control.BirdFloatMoveControl;
import aqario.fowlplay.common.entity.ai.pathing.BirdNavigation;
import aqario.fowlplay.common.sound.FowlPlaySoundEvents;
import aqario.fowlplay.common.tags.FowlPlayBiomeTags;
import aqario.fowlplay.common.tags.FowlPlayEntityTypeTags;
import aqario.fowlplay.common.tags.FowlPlayItemTags;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class DuckEntity extends TrustingBirdEntity implements VariantHolder<DuckEntity.Variant>, Aquatic, Flocking {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(DuckEntity.class, TrackedDataHandlerRegistry.STRING);
    public final AnimationState idleState = new AnimationState();
    public final AnimationState glideState = new AnimationState();
    public final AnimationState flapState = new AnimationState();
    public final AnimationState floatState = new AnimationState();

    public DuckEntity(EntityType<? extends DuckEntity> entityType, World world) {
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
        return 0.7f;
    }

    @Override
    protected MoveControl getLandMoveControl() {
        return new BirdFloatMoveControl(this);
    }

    @Override
    protected EntityNavigation getLandNavigation() {
        return new AmphibiousSwimNavigation(this, this.getWorld());
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends BirdEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBiome(pos).isIn(FowlPlayBiomeTags.SPAWNS_DUCKS);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setVariant(Util.getRandom(Variant.VARIANTS, world.getRandom()));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected BirdNavigation getFlightNavigation() {
        BirdNavigation birdNavigation = new BirdNavigation(this, this.getWorld());
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanEnterOpenDoors(true);
        birdNavigation.setCanSwim(true);
        return birdNavigation;
    }

    @Override
    public int getFlapFrequency() {
        return 0;
    }

    public static DefaultAttributeContainer.Builder createDuckAttributes() {
        return FlyingBirdEntity.createFlyingBirdAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.2f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, Variant.MALLARD.toString());
    }

    @Override
    public Variant getVariant() {
        return Variant.valueOf(this.dataTracker.get(VARIANT));
    }

    @Override
    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("variant", this.getVariant().toString());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("variant")) {
            this.setVariant(Variant.valueOf(nbt.getString("variant")));
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.getWorld().isClient) {
            return false;
        }
        if (bl && source.getAttacker() instanceof LivingEntity entity) {
            DuckBrain.onAttacked(this, entity);
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
        return Ingredient.fromTag(FowlPlayItemTags.DUCK_FOOD);
    }

    @Override
    public boolean canHunt(LivingEntity target) {
        return target.getType().isIn(FowlPlayEntityTypeTags.DUCK_HUNT_TARGETS) ||
            (target.getType().isIn(FowlPlayEntityTypeTags.DUCK_BABY_HUNT_TARGETS) && target.isBaby());
    }

    @Override
    public boolean shouldAvoid(LivingEntity entity) {
        return entity.getType().isIn(FowlPlayEntityTypeTags.DUCK_AVOIDS);
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_PARROT_EAT;
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient()) {
            this.idleState.setRunning(!this.isFlying() && !this.isInsideWaterOrBubbleColumn(), this.age);
            this.flapState.setRunning(this.isFlying(), this.age);
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
        return FowlPlaySoundEvents.ENTITY_DUCK_CALL;
    }

    @Override
    protected float getCallVolume() {
        return FowlPlayConfig.getInstance().duckCallVolume;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FowlPlaySoundEvents.ENTITY_DUCK_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected Brain.Profile<DuckEntity> createBrainProfile() {
        return DuckBrain.createProfile();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return DuckBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<DuckEntity> getBrain() {
        return (Brain<DuckEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("duckBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("duckActivityUpdate");
        DuckBrain.reset(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public boolean isFloating() {
        double maxWaterHeight = 0.35; // how much of the hitbox the water should cover
        BlockPos blockPos = BlockPos.ofFloored(this.getX(), this.getY() + maxWaterHeight, this.getZ());
        double waterHeight = this.getBlockPos().getY() + this.getWorld().getFluidState(blockPos).getHeight(this.getWorld(), blockPos);
        return this.isSubmergedInWater() || waterHeight > this.getY() + maxWaterHeight;
    }

    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void setLeader() {
    }

    public enum Variant {
        MALLARD("mallard");

        public static final List<Variant> VARIANTS = List.of(Arrays.stream(values())
            .toArray(Variant[]::new));

        private final String id;

        Variant(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
