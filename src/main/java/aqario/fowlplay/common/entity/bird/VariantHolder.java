package aqario.fowlplay.common.entity.bird;

import aqario.fowlplay.common.registry.CommonRegistry;
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

    CommonRegistry<T> variantRegistry();

    ResourceKey<Registry<T>> variantRegistryKey();

    T defaultVariant();

    T getVariant();

    void setVariant(T variant);

    default void defineVariant(SynchedEntityData entityData, EntityDataAccessor<T> accessor) {
        entityData.define(accessor, this.defaultVariant());
    }

    default void writeVariant(CompoundTag nbt) {
        nbt.putString(VARIANT_KEY, this.variantRegistry().fowlplay$getKey(this.getVariant()).toString());
    }

    default void readVariant(CompoundTag nbt) {
        T variant = this.variantRegistry()
            .fowlplay$get(ResourceLocation.tryParse(nbt.getString(VARIANT_KEY)));
        if(variant != null) {
            this.setVariant(variant);
        }
    }

    default void withRandomVariant(RandomSource random, Consumer<T> consumer) {
        this.getRandomVariant(random).ifPresent(consumer);
    }

    default Optional<T> getRandomVariant(RandomSource random) {
        return this.variantRegistry().fowlplay$getRandom(random);
    }

    default Optional<Holder.Reference<T>> toHolder(T variant) {
        return this.variantRegistry().fowlplay$getHolder(variant);
    }
}
