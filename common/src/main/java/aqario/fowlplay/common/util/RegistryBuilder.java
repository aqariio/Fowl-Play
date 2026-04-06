package aqario.fowlplay.common.util;

import aqario.fowlplay.core.platform.Register;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

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

    public Registry<T> buildAndRegister() {
        return Register.registry(this.registryKey, this.sync);
    }
}
