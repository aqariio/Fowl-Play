package aqario.fowlplay.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public abstract class CommonRegister<T> {
    protected final String namespace;
    protected boolean registered = false;

    public CommonRegister(String namespace) {
        this.namespace = namespace;
    }

    @ExpectPlatform
    public static <T> CommonRegister<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> CommonRegister<T> create(Registry<T> registry, String namespace) {
        throw new AssertionError();
    }

    public abstract <E extends T> Supplier<E> register(String name, Supplier<E> entry);

    public abstract ResourceKey<? extends Registry<T>> key();

    public abstract Registry<T> registry();

    public void register() {
        if(this.registered) {
            throw new IllegalArgumentException("Already registered!");
        }
        this.registered = true;
    }
}
