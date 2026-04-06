package aqario.fowlplay.common.registry.neoforge;

import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Supplier;

public class CommonRegisterImpl<T> extends CommonRegister<T> {
    private final DeferredRegister<T> registry;

    protected CommonRegisterImpl(DeferredRegister<T> registry, String namespace) {
        super(namespace);
        this.registry = registry;
    }

    public static <T> CommonRegister<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        return new CommonRegisterImpl<>(DeferredRegister.create(key, namespace), namespace);
    }

    public static <T> CommonRegister<T> create(Registry<T> registry, String namespace) {
        return new CommonRegisterImpl<>(DeferredRegister.create(registry, namespace), namespace);
    }

    @Override
    public <E extends T> Supplier<E> register(String name, Supplier<E> entry) {
        return this.registry.register(name, entry);
    }

    @Override
    public ResourceKey<? extends Registry<T>> key() {
        return this.registry.getRegistryKey();
    }

    @Override
    public Registry<T> registry() {
        return this.registry.getRegistry().get();
    }

    @Override
    protected void platformRegister() {
        this.registry.register(Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()));
    }
}
