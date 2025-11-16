package aqario.fowlplay.core.platform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;

import java.util.Optional;

public interface CommonRegistry<T> {
    T fowlplay$get(ResourceLocation id);

    ResourceLocation fowlplay$getId(T value);

    Optional<T> fowlplay$getRandom(RandomSource random);

    Optional<T> fowlplay$getRandomEntry(TagKey<T> tag, RandomSource random);
}
