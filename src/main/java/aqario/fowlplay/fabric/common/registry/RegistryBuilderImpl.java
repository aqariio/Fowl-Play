//? if fabric {
package aqario.fowlplay.fabric.common.registry;

import aqario.fowlplay.common.registry.CommonRegistry;
import aqario.fowlplay.common.registry.RegistryBuilder;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

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
        FabricRegistryBuilder<T, MappedRegistry<T>> builder = FabricRegistryBuilder.createSimple(registryKey);
        if(sync) {
            builder.attribute(RegistryAttribute.SYNCED);
        }
        Registry<T> registry = builder.buildAndRegister();
        return () -> (CommonRegistry<T>) registry;
    }
}
//?}