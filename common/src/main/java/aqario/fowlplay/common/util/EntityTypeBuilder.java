package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.CustomMobCategory;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EntityTypeBuilder<T extends Entity> {
    private final EntityType.Builder<T> builder;
    @Nullable
    private Supplier<AttributeSupplier.Builder> attributeBuilder;
    private SpawnPlacementType spawnPlacement;
    private Heightmap.Types heightmap;
    private SpawnPlacements.SpawnPredicate<T> spawnPredicate;

    private EntityTypeBuilder(EntityType.Builder<T> builder, MobCategory mobCategory) {
        this.builder = builder;
        if(mobCategory == CustomMobCategory.BIRDS.mobCategory) {
            this.canSpawnFarFromPlayer();
        }
    }

    public static <T extends Entity> EntityTypeBuilder<T> of(EntityType.EntityFactory<T> factory, MobCategory mobCategory) {
        return new EntityTypeBuilder<>(EntityType.Builder.of(factory, mobCategory), mobCategory);
    }

    public EntityTypeBuilder<T> dimensions(float width, float height) {
        this.builder.sized(width, height);
        return this;
    }

    public EntityTypeBuilder<T> spawnDimensionsScale(float spawnBoxScale) {
        this.builder.spawnDimensionsScale(spawnBoxScale);
        return this;
    }

    public EntityTypeBuilder<T> eyeHeight(float eyeHeight) {
        this.builder.eyeHeight(eyeHeight);
        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(float... offsetYs) {
        this.builder.passengerAttachments(offsetYs);
        return this;
    }

    public EntityTypeBuilder<T> passengerAttachments(Vec3... passengerAttachments) {
        this.builder.passengerAttachments(passengerAttachments);
        return this;
    }

    public EntityTypeBuilder<T> vehicleAttachment(Vec3 vehicleAttachment) {
        this.builder.vehicleAttachment(vehicleAttachment);
        return this;
    }

    public EntityTypeBuilder<T> ridingOffset(float offsetY) {
        this.builder.ridingOffset(offsetY);
        return this;
    }

    public EntityTypeBuilder<T> nameTagOffset(float offsetY) {
        this.builder.nameTagOffset(offsetY);
        return this;
    }

    public EntityTypeBuilder<T> attach(EntityAttachment type, float offsetX, float offsetY, float offsetZ) {
        this.builder.attach(type, offsetX, offsetY, offsetZ);
        return this;
    }

    public EntityTypeBuilder<T> attach(EntityAttachment type, Vec3 offset) {
        this.builder.attach(type, offset);
        return this;
    }

    public EntityTypeBuilder<T> noSummon() {
        this.builder.noSummon();
        return this;
    }

    public EntityTypeBuilder<T> noSave() {
        this.builder.noSave();
        return this;
    }

    public EntityTypeBuilder<T> fireImmune() {
        this.builder.fireImmune();
        return this;
    }

    public EntityTypeBuilder<T> immuneTo(Block... blocks) {
        this.builder.immuneTo(blocks);
        return this;
    }

    public EntityTypeBuilder<T> canSpawnFarFromPlayer() {
        this.builder.canSpawnFarFromPlayer();
        return this;
    }

    public EntityTypeBuilder<T> clientTrackingRange(int maxTrackingRange) {
        this.builder.clientTrackingRange(maxTrackingRange);
        return this;
    }

    public EntityTypeBuilder<T> updateInterval(int ticks) {
        this.builder.updateInterval(ticks);
        return this;
    }

    public EntityTypeBuilder<T> requiredFeatures(FeatureFlag... features) {
        this.builder.requiredFeatures(features);
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

    public Result<T> build() {
        return new Result<>(
            this.builder,
            this.attributeBuilder,
            this.spawnPlacement,
            this.heightmap,
            this.spawnPredicate
        );
    }

    public record Result<T extends Entity>(
        EntityType.Builder<T> builder,
        @Nullable Supplier<AttributeSupplier.Builder> attributeBuilder,
        SpawnPlacementType spawnPlacement,
        Heightmap.Types heightmap,
        SpawnPlacements.SpawnPredicate<T> spawnPredicate
    ) {
    }
}
