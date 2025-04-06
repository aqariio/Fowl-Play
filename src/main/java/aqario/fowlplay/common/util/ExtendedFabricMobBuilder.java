package aqario.fowlplay.common.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

import java.util.function.Supplier;

// reimplementation so I can stop the null checks from failing after reading my mixin enums
public class ExtendedFabricMobBuilder<T extends MobEntity> extends FabricEntityTypeBuilder.Living<T> {
    private SpawnRestriction.Location restrictionLocation;
    private Heightmap.Type restrictionHeightmap;
    private SpawnRestriction.SpawnPredicate<T> spawnPredicate;

    protected ExtendedFabricMobBuilder(SpawnGroup spawnGroup, EntityType.EntityFactory<T> factory) {
        super(spawnGroup, factory);
    }

    public static <T extends MobEntity> ExtendedFabricMobBuilder<T> createExtended() {
        return new ExtendedFabricMobBuilder<>(SpawnGroup.MISC, (entityType, world) -> null);
    }

    public ExtendedFabricMobBuilder<T> spawnGroup(SpawnGroup group) {
        super.spawnGroup(group);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <N extends T> ExtendedFabricMobBuilder<N> entityFactory(EntityType.EntityFactory<N> factory) {
        super.entityFactory(factory);
        return (ExtendedFabricMobBuilder<N>) this;
    }

    public ExtendedFabricMobBuilder<T> disableSummon() {
        super.disableSummon();
        return this;
    }

    public ExtendedFabricMobBuilder<T> disableSaving() {
        super.disableSaving();
        return this;
    }

    public ExtendedFabricMobBuilder<T> fireImmune() {
        super.fireImmune();
        return this;
    }

    public ExtendedFabricMobBuilder<T> spawnableFarFromPlayer() {
        super.spawnableFarFromPlayer();
        return this;
    }

    public ExtendedFabricMobBuilder<T> dimensions(EntityDimensions dimensions) {
        super.dimensions(dimensions);
        return this;
    }

    public ExtendedFabricMobBuilder<T> trackRangeChunks(int range) {
        super.trackRangeChunks(range);
        return this;
    }

    public ExtendedFabricMobBuilder<T> trackRangeBlocks(int range) {
        super.trackRangeBlocks(range);
        return this;
    }

    public ExtendedFabricMobBuilder<T> trackedUpdateRate(int rate) {
        super.trackedUpdateRate(rate);
        return this;
    }

    public ExtendedFabricMobBuilder<T> forceTrackedVelocityUpdates(boolean forceTrackedVelocityUpdates) {
        super.forceTrackedVelocityUpdates(forceTrackedVelocityUpdates);
        return this;
    }

    public ExtendedFabricMobBuilder<T> specificSpawnBlocks(Block... blocks) {
        super.specificSpawnBlocks(blocks);
        return this;
    }

    public ExtendedFabricMobBuilder<T> defaultAttributes(Supplier<DefaultAttributeContainer.Builder> defaultAttributeBuilder) {
        super.defaultAttributes(defaultAttributeBuilder);
        return this;
    }

    public ExtendedFabricMobBuilder<T> spawnRestriction(SpawnRestriction.Location location, Heightmap.Type heightmap, SpawnRestriction.SpawnPredicate<T> spawnPredicate) {
        this.restrictionLocation = location;
        this.restrictionHeightmap = heightmap;
        this.spawnPredicate = spawnPredicate;
        return this;
    }

    public EntityType<T> build() {
        EntityType<T> type = super.build();
        if (this.spawnPredicate != null) {
            SpawnRestriction.register(type, this.restrictionLocation, this.restrictionHeightmap, this.spawnPredicate);
        }

        return type;
    }
}
