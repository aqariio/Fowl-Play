package aqario.fowlplay.common.util;

import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public final class RegistryBuilder<T, R extends MutableRegistry<T>> {
    private final RegistryKey<Registry<T>> registryKey;
    private boolean sync = false;
    private Identifier defaultId = null;

    private RegistryBuilder(RegistryKey<Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    private RegistryBuilder(RegistryKey<Registry<T>> registryKey, Identifier defaultId) {
        this.registryKey = registryKey;
        this.defaultId = defaultId;
    }

    public static <T, R extends SimpleRegistry<T>> RegistryBuilder<T, R> create(RegistryKey<Registry<T>> registryKey) {
        return new RegistryBuilder<>(registryKey);
    }

    public static <T, R extends SimpleDefaultedRegistry<T>> RegistryBuilder<T, R> createDefaulted(RegistryKey<Registry<T>> registryKey, String defaultId) {
        return new RegistryBuilder<>(registryKey, Identifier.of(FowlPlay.ID, defaultId));
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
        Identifier defaultId
    ) {
    }
}
