package aqario.fowlplay.common.registry;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public abstract class CommonRegister<T> {
    protected final String namespace;
    protected boolean registered = false;

    protected CommonRegister(String namespace) {
        this.namespace = namespace;
    }

    public static <T> CommonRegister<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        return FowlPlay.PLATFORM.commonRegister$create(key, namespace);
    }

    public static <T> CommonRegister<T> create(Registry<T> registry, String namespace) {
        return FowlPlay.PLATFORM.commonRegister$create(registry, namespace);
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
