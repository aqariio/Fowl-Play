package aqario.fowlplay.core.platform;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

public interface CommonRegistry<T> {
    Registry<T> fowlplay$getRegistry();

    RegistryKey<? extends Registry<T>> fowlplay$getKey();

    T fowlplay$get(Identifier id);

    Identifier fowlplay$getId(T value);

    Optional<T> fowlplay$getRandom(Random random);
}
