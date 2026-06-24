package aqario.fowlplay.loaders.neoforge.common.registry;

import aqario.fowlplay.common.registry.RegistryBuilder;
import aqario.fowlplay.loaders.neoforge.core.FowlPlayNeoForge;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;

public class RegistryBuilderImpl<T> extends RegistryBuilder<T> {
    private RegistryBuilderImpl(ResourceKey<Registry<T>> registryKey) {
        super(registryKey);
    }

    public static <T> RegistryBuilder<T> create(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilderImpl<>(registryKey);
    }

    @Override
    public Registry<T> buildAndRegister() {
        var builder = new net.neoforged.neoforge.registries.RegistryBuilder<>(registryKey);
        if(sync) {
            builder.sync(true);
        }
        Registry<T> registry = builder.create();
        FowlPlayNeoForge.eventBus().<NewRegistryEvent>addListener(event -> event.register(registry));
        return registry;
    }
}
