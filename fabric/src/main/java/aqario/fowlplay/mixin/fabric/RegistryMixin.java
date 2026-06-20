package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.common.registry.CommonRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends CommonRegistry<T> {
    @Shadow
    Optional<Holder.Reference<T>> getRandom(RandomSource random);

    @Shadow
    @Nullable
    T get(@Nullable ResourceLocation id);

    @Shadow
    @Nullable
    ResourceLocation getKey(T value);

    @Shadow
    Optional<HolderSet.Named<T>> getTag(TagKey<T> tag);

    @Shadow
    Optional<Holder.Reference<T>> getHolder(ResourceKey<T> key);

    @Shadow
    Optional<ResourceKey<T>> getResourceKey(T value);

    @Override
    default T fowlplay$get(ResourceLocation id) {
        return this.get(id);
    }

    @Override
    default ResourceLocation fowlplay$getKey(T value) {
        return this.getKey(value);
    }

    @Override
    default Optional<T> fowlplay$getRandom(RandomSource random) {
        return this.getRandom(random).map(Holder.Reference::value);
    }

    @Override
    default Optional<T> fowlplay$getRandomElement(TagKey<T> tag, RandomSource random) {
        return this.getTag(tag).flatMap(list -> list.getRandomElement(random).map(Holder::value));
    }

    @Override
    default Optional<Holder.Reference<T>> fowlplay$getHolder(T value) {
        return this.getResourceKey(value).flatMap(this::getHolder);
    }
}
