//? if forge {
/*package aqario.fowlplay.forge.common.registry;

import aqario.fowlplay.common.registry.CommonRegister;
import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CommonRegisterImpl<T> extends CommonRegister<T> {
    private final DeferredRegister<T> registry;

    private CommonRegisterImpl(DeferredRegister<T> registry, String namespace) {
        super(namespace);
        this.registry = registry;
    }

    public static <T> CommonRegister<T> create(ResourceKey<? extends Registry<T>> key, String namespace) {
        return new CommonRegisterImpl<>(DeferredRegister.create(key, namespace), namespace);
    }

    public static <T> CommonRegister<T> create(Registry<T> registry, String namespace) {
        return new CommonRegisterImpl<>(DeferredRegister.create(registry.key(), namespace), namespace);
    }

    @Override
    public <E extends T> Supplier<E> register(String name, Supplier<E> entry) {
        return this.registry.register(name, entry);
    }

    @Override
    public ResourceKey<? extends Registry<T>> key() {
        return this.registry.getRegistryKey();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Registry<T> registry() {
        return ((Registry<Registry<T>>) BuiltInRegistries.REGISTRY).get((ResourceKey<Registry<T>>) this.key());
    }

    @Override
    public void register() {
        super.register();
        this.registry.register(FowlPlayForge.eventBus());
    }
}
*///?}