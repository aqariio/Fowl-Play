package aqario.fowlplay.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public abstract class RegistryBuilder<T> {
    protected final ResourceKey<Registry<T>> registryKey;
    protected boolean sync = false;

    protected RegistryBuilder(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    @ExpectPlatform
    public static <T> RegistryBuilder<T> create(ResourceKey<Registry<T>> registryKey) {
        throw new AssertionError();
    }

    public RegistryBuilder<T> sync() {
        this.sync = true;
        return this;
    }

    public abstract Supplier<CommonRegistry<T>> buildAndRegister();
}
