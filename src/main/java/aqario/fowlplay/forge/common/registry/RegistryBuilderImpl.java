//? if forge {
/*package aqario.fowlplay.forge.common.registry;

import aqario.fowlplay.common.registry.CommonRegistry;
import aqario.fowlplay.common.registry.RegistryBuilder;
import aqario.fowlplay.forge.core.FowlPlayForge;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class RegistryBuilderImpl<T> extends RegistryBuilder<T> {
    private RegistryBuilderImpl(ResourceKey<Registry<T>> registryKey) {
        super(registryKey);
    }

    public static <T> RegistryBuilder<T> create(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilderImpl<>(registryKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Supplier<CommonRegistry<T>> buildAndRegister() {
        var builder = net.minecraftforge.registries.RegistryBuilder.of(this.registryKey.location());
        if(!sync) {
            builder.disableSync();
        }
        AtomicReference<Supplier<IForgeRegistry<T>>> registry = new AtomicReference<>();
        FowlPlayForge.eventBus().<NewRegistryEvent>addListener(event ->
            registry.set(event.create((net.minecraftforge.registries.RegistryBuilder<T>) builder))
        );
        return () -> ((Supplier<CommonRegistry<T>>) (Supplier<?>) registry.get()).get();
    }
}
*///?}