package aqario.fowlplay.common.util;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class RegistryBuilder<T, R extends WritableRegistry<T>> {
    private final ResourceKey<Registry<T>> registryKey;
    private boolean sync = false;
    private ResourceLocation defaultId = null;

    private RegistryBuilder(ResourceKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    private RegistryBuilder(ResourceKey<Registry<T>> registryKey, ResourceLocation defaultId) {
        this.registryKey = registryKey;
        this.defaultId = defaultId;
    }

    public static <T, R extends MappedRegistry<T>> RegistryBuilder<T, R> create(ResourceKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey);
    }

    public static <T, R extends DefaultedMappedRegistry<T>> RegistryBuilder<T, R> createDefaulted(ResourceKey<Registry<T>> registryKey, String defaultId) {
        return new RegistryBuilder<>(registryKey, FowlPlay.id(defaultId));
    }

    public RegistryBuilder<T, R> sync() {
        this.sync = true;
        return this;
    }

    public R buildAndRegister() {
        return PlatformHelper.registerRegistry(
            this.registryKey,
            new Properties(
                this.sync,
                this.defaultId
            )
        );
    }

    public record Properties(
        boolean sync,
        ResourceLocation defaultId
    ) {
    }
}
