package aqario.fowlplay.mixin.fabric;

import aqario.fowlplay.core.platform.CommonRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends CommonRegistry<T> {
    @Shadow
    RegistryKey<? extends Registry<T>> getKey();

    @Shadow
    Optional<RegistryEntry.Reference<T>> getRandom(Random random);

    @Shadow
    @Nullable
    T get(@Nullable Identifier id);

    @Shadow
    @Nullable
    Identifier getId(T value);

    @Override
    default Registry<T> fowlplay$getRegistry() {
        return (Registry<T>) this;
    }

    @Override
    default RegistryKey<? extends Registry<T>> fowlplay$getKey() {
        return this.getKey();
    }

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
}
