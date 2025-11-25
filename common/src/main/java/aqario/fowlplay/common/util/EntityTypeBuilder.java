package aqario.fowlplay.common.util;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EntityTypeBuilder<T extends Entity> {
    private final EntityType.EntityFactory<T> factory;
    private final MobCategory category;
    private ImmutableSet<Block> immuneTo = ImmutableSet.of();
    private boolean serialize = true;
    private boolean summon = true;
    private boolean fireImmune;
    private boolean canSpawnFarFromPlayer;
    private int clientTrackingRange = 5;
    private int updateInterval = 3;
    private EntityDimensions dimensions = EntityDimensions.scalable(0.6F, 1.8F);
    private float spawnDimensionsScale = 1.0F;
    private EntityAttachments.Builder attachments = EntityAttachments.builder();
    private FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;
    @Nullable
    private Supplier<AttributeSupplier.Builder> attributeBuilder;
    private SpawnPlacementType spawnPlacement;
    private Heightmap.Types heightmap;
    private SpawnPlacements.SpawnPredicate<T> spawnPredicate;

    private EntityTypeBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
        this.factory = factory;
        this.category = category;
        this.canSpawnFarFromPlayer = category == MobCategory.CREATURE || category == MobCategory.MISC;
    }

    public static <T extends Entity> EntityTypeBuilder<T> of(EntityType.EntityFactory<T> factory, MobCategory spawnGroup) {
        return new EntityTypeBuilder<>(factory, spawnGroup);
    }

    public EntityTypeBuilder<T> sized(float width, float height) {
        this.dimensions = EntityDimensions.scalable(width, height);
        return this;
    }

    public EntityTypeBuilder<T> spawnDimensionsScale(float spawnBoxScale) {
        this.spawnDimensionsScale = spawnBoxScale;
        return this;
    }

    public EntityTypeBuilder<T> eyeHeight(float eyeHeight) {
        this.dimensions = this.dimensions.withEyeHeight(eyeHeight);
        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(float... offsetYs) {
        for(float f : offsetYs) {
            this.attachments = this.attachments.attach(EntityAttachment.PASSENGER, 0.0F, f, 0.0F);
        }

        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(Vec3... passengerAttachments) {
        for(Vec3 vec3d : passengerAttachments) {
            this.attachments = this.attachments.attach(EntityAttachment.PASSENGER, vec3d);
        }

        return this;
    }

    public EntityTypeBuilder<T> vehicleAttachment(Vec3 vehicleAttachment) {
        return this.attach(EntityAttachment.VEHICLE, vehicleAttachment);
    }

    public EntityTypeBuilder<T> ridingOffset(float offsetY) {
        return this.attach(EntityAttachment.VEHICLE, 0.0F, -offsetY, 0.0F);
    }

    public EntityTypeBuilder<T> nameTagOffset(float offsetY) {
        return this.attach(EntityAttachment.NAME_TAG, 0.0F, offsetY, 0.0F);
    }

    public EntityTypeBuilder<T> attach(EntityAttachment type, float offsetX, float offsetY, float offsetZ) {
        this.attachments = this.attachments.attach(type, offsetX, offsetY, offsetZ);
        return this;
    }

    public EntityTypeBuilder<T> attach(EntityAttachment type, Vec3 offset) {
        this.attachments = this.attachments.attach(type, offset);
        return this;
    }

    public EntityTypeBuilder<T> noSummon() {
        this.summon = false;
        return this;
    }

    public EntityTypeBuilder<T> noSave() {
        this.serialize = false;
        return this;
    }

    public EntityTypeBuilder<T> fireImmune() {
        this.fireImmune = true;
        return this;
    }

    public EntityTypeBuilder<T> immuneTo(Block... blocks) {
        this.immuneTo = ImmutableSet.copyOf(blocks);
        return this;
    }

    public EntityTypeBuilder<T> canSpawnFarFromPlayer() {
        this.canSpawnFarFromPlayer = true;
        return this;
    }

    public EntityTypeBuilder<T> clientTrackingRange(int maxTrackingRange) {
        this.clientTrackingRange = maxTrackingRange;
        return this;
    }

    public EntityTypeBuilder<T> updateInterval(int ticks) {
        this.updateInterval = ticks;
        return this;
    }

    public EntityTypeBuilder<T> requiredFeatures(FeatureFlag... features) {
        this.requiredFeatures = FeatureFlags.REGISTRY.subset(features);
        return this;
    }

    public EntityTypeBuilder<T> attributes(Supplier<AttributeSupplier.Builder> attributeBuilder) {
        this.attributeBuilder = attributeBuilder;
        return this;
    }

    public EntityTypeBuilder<T> spawnRestriction(SpawnPlacementType location, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.spawnPlacement = location;
        this.heightmap = heightmap;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public EntityType<T> build() {
        return this.build(null);
    }

    @SuppressWarnings("unchecked")
    public EntityType<T> build(String id) {
        if(this.serialize) {
            Util.fetchChoiceType(References.ENTITY_TREE, id);
        }

        EntityType<T> type = new EntityType<>(
            this.factory,
            this.category,
            this.serialize,
            this.summon,
            this.fireImmune,
            this.canSpawnFarFromPlayer,
            this.immuneTo,
            this.dimensions.withAttachments(this.attachments),
            this.spawnDimensionsScale,
            this.clientTrackingRange,
            this.updateInterval,
            this.requiredFeatures
        );

        if(type.getBaseClass().isAssignableFrom(LivingEntity.class)) {
            if(this.attributeBuilder != null) {
                EntityAttributeRegistry.register(() -> (EntityType<? extends LivingEntity>) type, this.attributeBuilder);
            }
        }

        if(type.getBaseClass().isAssignableFrom(Mob.class)) {
            if(this.spawnPredicate != null) {
                SpawnPlacementsRegistry.register(() -> (EntityType<Mob>) type, this.spawnPlacement, this.heightmap, (SpawnPlacements.SpawnPredicate<Mob>) this.spawnPredicate);
            }
        }

        return type;
    }
}
