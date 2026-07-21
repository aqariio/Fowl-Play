package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.FPMobCategory;
import aqario.fowlplay.common.registry.AttributeRegistry;
import aqario.fowlplay.common.registry.SpawnPlacementsRegistry;
import aqario.fowlplay.common.worldgen.BiomeModifier;
import com.google.common.collect.ImmutableSet;
import net.minecraft.Util;
import net.minecraft.tags.TagKey;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
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
    private FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;
    @Nullable
    private Supplier<AttributeSupplier.Builder> attributeBuilder;
    private SpawnPlacements.Type spawnPlacement;
    private Heightmap.Types heightmap;
    private SpawnPlacements.SpawnPredicate<T> spawnPredicate;
    private boolean hasSpawn = false;
    private TagKey<Biome> biomeTag;
    private int weight;
    private int minCount;
    private int maxCount;
    private boolean hasSpawnCost = false;
    private double energyBudget;
    private double charge;

    private EntityTypeBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
        this.factory = factory;
        this.category = category;
        this.canSpawnFarFromPlayer = category == FPMobCategory.birds()
            || category == FPMobCategory.ambientBirds()
            || category == MobCategory.CREATURE
            || category == MobCategory.MISC;
    }

    public static <T extends Entity> EntityTypeBuilder<T> of(EntityType.EntityFactory<T> factory, MobCategory spawnGroup) {
        return new EntityTypeBuilder<>(factory, spawnGroup);
    }

    public EntityTypeBuilder<T> sized(float width, float height) {
        this.dimensions = EntityDimensions.scalable(width, height);
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

    public EntityTypeBuilder<T> spawnPlacement(SpawnPlacements.Type location, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        this.spawnPlacement = location;
        this.heightmap = heightmap;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public EntityTypeBuilder<T> spawn(TagKey<Biome> biome, int weight, int minCount, int maxCount) {
        this.hasSpawn = true;
        this.biomeTag = biome;
        this.weight = weight;
        this.minCount = minCount;
        this.maxCount = maxCount;
        return this;
    }

    public EntityTypeBuilder<T> spawnCost(TagKey<Biome> biome, double energyBudget, double charge) {
        this.hasSpawnCost = true;
        this.biomeTag = biome;
        this.energyBudget = energyBudget;
        this.charge = charge;
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
            this.canSpawnFarFromPlayer,
            this.serialize,
            this.summon,
            this.fireImmune,
            this.immuneTo,
            this.dimensions,
            this.clientTrackingRange,
            this.updateInterval,
            this.requiredFeatures
        );

        if(this.hasSpawn) {
            BiomeModifier.add(
                context -> context.is(this.biomeTag),
                (context, modifier) -> modifier.addSpawn(
                    this.category,
                    new MobSpawnSettings.SpawnerData(
                        type,
                        this.weight,
                        this.minCount,
                        this.maxCount
                    )
                )
            );
        }

        if(this.hasSpawnCost) {
            BiomeModifier.add(
                context -> context.is(this.biomeTag),
                (context, modifier) -> modifier.setSpawnCost(
                    type,
                    new MobSpawnSettings.MobSpawnCost(
                        this.energyBudget,
                        this.charge
                    )
                )
            );
        }

        if(type.getBaseClass().isAssignableFrom(LivingEntity.class)) {
            if(this.attributeBuilder != null) {
                AttributeRegistry.register(
                    () -> (EntityType<? extends LivingEntity>) type,
                    this.attributeBuilder.get()
                );
            }
        }

        if(type.getBaseClass().isAssignableFrom(Mob.class)) {
            if(this.spawnPredicate != null) {
                SpawnPlacementsRegistry.register(
                    () -> (EntityType<Mob>) type,
                    this.spawnPlacement,
                    this.heightmap,
                    (SpawnPlacements.SpawnPredicate<Mob>) this.spawnPredicate
                );
            }
        }

        return type;
    }
}
