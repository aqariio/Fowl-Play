package aqario.fowlplay.common.util;

import aqario.fowlplay.core.platform.CommonRegistry;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public final class RegistryBuilder<T> {
    private final ResourceKey<Registry<T>> registryKey;
    private boolean sync = false;

    private RegistryBuilder(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    public static <T> RegistryBuilder<T> create(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey);
    }

    public RegistryBuilder<T> sync() {
        this.sync = true;
        return this;
    }

    public Supplier<CommonRegistry<T>> buildAndRegister() {
        return PlatformHelper.registerRegistry(this.registryKey, this.sync);
    }
}
