package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.BirdEntity;
import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import net.minecraft.block.Block;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Util;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EntityTypeBuilder<T extends Entity> {
    private final EntityType.EntityFactory<T> factory;
    private final SpawnGroup spawnGroup;
    private ImmutableSet<Block> canSpawnInside = ImmutableSet.of();
    private boolean saveable = true;
    private boolean summonable = true;
    private boolean fireImmune;
    private boolean spawnableFarFromPlayer;
    private int maxTrackDistance = 5;
    private int trackTickInterval = 3;
    private EntityDimensions dimensions = EntityDimensions.changing(0.6F, 1.8F);
    private FeatureSet requiredFeatures;
    @Nullable
    private Supplier<DefaultAttributeContainer.Builder> attributeBuilder;
    private SpawnRestriction.Location location;
    private Heightmap.Type heightmap;
    private SpawnRestriction.SpawnPredicate<T> spawnPredicate;

    private EntityTypeBuilder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
        this.requiredFeatures = FeatureFlags.VANILLA_FEATURES;
        this.factory = factory;
        this.spawnGroup = spawnGroup;
        this.spawnableFarFromPlayer = spawnGroup == SpawnGroup.CREATURE || spawnGroup == SpawnGroup.MISC;
    }

    public static <T extends BirdEntity> EntityTypeBuilder<T> create(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
        return new EntityTypeBuilder<>(factory, spawnGroup);
    }

    public static <T extends BirdEntity> EntityTypeBuilder<T> create(SpawnGroup spawnGroup) {
        return new EntityTypeBuilder<>((type, world) -> null, spawnGroup);
    }

    public EntityTypeBuilder<T> dimensions(float width, float height) {
        this.dimensions = EntityDimensions.changing(width, height);
        return this;
    }

    public EntityTypeBuilder<T> disableSummon() {
        this.summonable = false;
        return this;
    }

    public EntityTypeBuilder<T> disableSaving() {
        this.saveable = false;
        return this;
    }

    public EntityTypeBuilder<T> makeFireImmune() {
        this.fireImmune = true;
        return this;
    }

    public EntityTypeBuilder<T> allowSpawningInside(Block... blocks) {
        this.canSpawnInside = ImmutableSet.copyOf(blocks);
        return this;
    }

    public EntityTypeBuilder<T> spawnableFarFromPlayer() {
        this.spawnableFarFromPlayer = true;
        return this;
    }

    public EntityTypeBuilder<T> maxTrackingRange(int maxTrackingRange) {
        this.maxTrackDistance = maxTrackingRange;
        return this;
    }

    public EntityTypeBuilder<T> trackingTickInterval(int trackingTickInterval) {
        this.trackTickInterval = trackingTickInterval;
        return this;
    }

    public EntityTypeBuilder<T> requires(FeatureFlag... features) {
        this.requiredFeatures = FeatureFlags.FEATURE_MANAGER.featureSetOf(features);
        return this;
    }

    public EntityTypeBuilder<T> attributes(Supplier<DefaultAttributeContainer.Builder> attributeBuilder) {
        this.attributeBuilder = attributeBuilder;
        return this;
    }

    public EntityTypeBuilder<T> spawnRestriction(SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        this.location = location;
        this.heightmap = heightmap;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public EntityType<T> build() {
        return this.build(null);
    }

    @SuppressWarnings("unchecked")
    public EntityType<T> build(String id) {
        if(this.saveable) {
            Util.getChoiceType(TypeReferences.ENTITY_TREE, id);
        }

        EntityType<T> type = new EntityType<>(
            this.factory,
            this.spawnGroup,
            this.spawnableFarFromPlayer,
            this.saveable,
            this.summonable,
            this.fireImmune,
            this.canSpawnInside,
            this.dimensions,
            this.maxTrackDistance,
            this.trackTickInterval,
            this.requiredFeatures
        );

        if(type.getBaseClass().isAssignableFrom(LivingEntity.class)) {
            if(this.attributeBuilder != null) {
                EntityAttributeRegistry.register(() -> (EntityType<? extends LivingEntity>) type, this.attributeBuilder);
            }
        }

        if(type.getBaseClass().isAssignableFrom(MobEntity.class)) {
            if(this.spawnPredicate != null) {
                SpawnPlacementsRegistry.register(() -> (EntityType<MobEntity>) type, this.location, this.heightmap, (SpawnRestriction.SpawnPredicate<MobEntity>) this.spawnPredicate);
            }
        }

        return type;
    }
}
