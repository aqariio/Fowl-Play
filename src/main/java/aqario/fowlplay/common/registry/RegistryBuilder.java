//~ expect_platform

package aqario.fowlplay.common.registry;

import aqario.fowlplay.fabric.common.registry.RegistryBuilderImpl;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public abstract class RegistryBuilder<T> {
    protected final ResourceKey<Registry<T>> registryKey;
    protected boolean sync = false;

    protected RegistryBuilder(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    public static <T> RegistryBuilder<T> create(ResourceKey<Registry<T>> registryKey) {
        return RegistryBuilderImpl.create(registryKey);
    }

    public RegistryBuilder<T> sync() {
        this.sync = true;
        return this;
    }

    public abstract Supplier<CommonRegistry<T>> buildAndRegister();
}
