package aqario.fowlplay.core.platform;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

public interface CommonRegistry<T> {
    T fowlplay$get(Identifier id);

    Identifier fowlplay$getId(T value);

    Optional<T> fowlplay$getRandom(Random random);
}
