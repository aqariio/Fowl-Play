package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CommonRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends CommonRegistry<T> {
    @Shadow
    Optional<RegistryEntry.Reference<T>> getRandom(Random random);

    @Shadow
    @Nullable
    T get(@Nullable Identifier id);

    @Shadow
    @Nullable
    Identifier getId(T value);

    @Shadow
    Optional<RegistryEntryList.Named<T>> getEntryList(TagKey<T> tag);

    @Override
    default T fowlplay$get(Identifier id) {
        return this.get(id);
    }

    @Override
    default Identifier fowlplay$getId(T value) {
        return this.getId(value);
    }

    @Override
    default Optional<T> fowlplay$getRandom(Random random) {
        return this.getRandom(random).map(RegistryEntry.Reference::value);
    }

    @Override
    default Optional<T> fowlplay$getRandomEntry(TagKey<T> tag, Random random) {
        return this.getEntryList(tag).flatMap(list -> list.getRandom(random).map(RegistryEntry::value));
    }
}
