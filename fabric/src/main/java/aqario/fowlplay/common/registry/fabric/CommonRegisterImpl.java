package aqario.fowlplay.common.registry.fabric;

import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class CommonRegisterImpl<T> extends CommonRegister<T> {
    private final Registry<T> registry;

    protected CommonRegisterImpl(Registry<T> registry, String namespace) {
        super(namespace);
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public static <T> CommonRegister<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        Registry<?> registry = BuiltInRegistries.REGISTRY.get(key.location());
        if(registry == null) {
            throw new IllegalArgumentException("Unknown registry: " + key.location());
        }

        return new CommonRegisterImpl<>((Registry<T>) registry, namespace);
    }

    public static <T> CommonRegister<T> create(Registry<T> registry, String namespace) {
        return new CommonRegisterImpl<>(registry, namespace);
    }

    @Override
    public <E extends T> Supplier<E> register(String name, Supplier<E> entry) {
        E value = Registry.register(this.registry, ResourceLocation.fromNamespaceAndPath(this.namespace, name), entry.get());
        return () -> value;
    }

    @Override
    public ResourceKey<? extends Registry<T>> key() {
        return this.registry.key();
    }

    @Override
    public Registry<T> registry() {
        return this.registry;
    }

    @Override
    protected void platformRegister() {
        // NO-OP
    }
}
