//~ expect_platform

package aqario.fowlplay.common.registry;

import aqario.fowlplay.fabric.common.registry.CommonRegisterImpl;
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
        return CommonRegisterImpl.create(key, namespace);
    }

    public static <T> CommonRegister<T> create(Registry<T> registry, String namespace) {
        return CommonRegisterImpl.create(registry, namespace);
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