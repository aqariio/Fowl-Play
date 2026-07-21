package aqario.fowlplay.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;

import java.util.Optional;

public interface CommonRegistry<T> {
    T fowlplay$get(ResourceLocation id);

    ResourceLocation fowlplay$getKey(T value);

    Optional<T> fowlplay$getRandom(RandomSource random);

    Optional<T> fowlplay$getRandomElement(TagKey<T> tag, RandomSource random);

    Optional<Holder.Reference<T>> fowlplay$getHolder(T value);
}
