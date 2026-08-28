package aqario.fowlplay.common.entity.bird;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import java.util.Optional;
import java.util.function.Consumer;

public interface VariantHolder<T> {
    String VARIANT_KEY = "variant";

    Registry<T> variantRegistry();

    ResourceKey<Registry<T>> variantRegistryKey();

    ResourceKey<T> defaultVariant();

    Holder<T> getVariant();

    void setVariant(Holder<T> variant);

    String getVariantName();

    default void defineVariant(SynchedEntityData.Builder builder, EntityDataAccessor<Holder<T>> accessor) {
        builder.define(accessor, this.variantRegistry().getHolderOrThrow(this.defaultVariant()));
    }

    default void writeVariant(CompoundTag nbt) {
        nbt.putString(VARIANT_KEY, this.getVariantKey().location().toString());
    }

    default void readVariant(CompoundTag nbt) {
        Optional.ofNullable(ResourceLocation.tryParse(nbt.getString(VARIANT_KEY)))
            .map(variant -> ResourceKey.create(this.variantRegistryKey(), variant))
            .flatMap(this::toHolder)
            .ifPresent(this::setVariant);
    }

    default void withRandomVariant(RandomSource random, Consumer<Holder.Reference<T>> consumer) {
        this.getRandomVariant(random).ifPresent(consumer);
    }

    default Optional<Holder.Reference<T>> getRandomVariant(RandomSource random) {
        return this.variantRegistry().getRandom(random);
    }

    default Optional<Holder.Reference<T>> toHolder(ResourceKey<T> variant) {
        return this.variantRegistry().getHolder(variant);
    }

    default ResourceKey<T> getVariantKey() {
        return this.getVariant().unwrapKey()
            .orElse(this.defaultVariant());
    }
}
