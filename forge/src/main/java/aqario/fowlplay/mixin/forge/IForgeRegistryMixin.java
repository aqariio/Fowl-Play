package aqario.fowlplay.mixin.forge;

import aqario.fowlplay.core.platform.CommonRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(IForgeRegistry.class)
public interface IForgeRegistryMixin<V> extends CommonRegistry<V> {
    @Shadow
    RegistryKey<Registry<V>> getRegistryKey();

    @Shadow
    @NotNull
    Collection<V> getValues();

    @Shadow
    @Nullable
    V getValue(Identifier key);

    @Shadow
    @Nullable
    Identifier getKey(V value);

    @Override
    default RegistryKey<? extends Registry<V>> fowlplay$getKey() {
        return this.getRegistryKey();
    }

    @Override
    default V fowlplay$get(Identifier id) {
        return this.getValue(id);
    }

    @Override
    default Identifier fowlplay$getId(V value) {
        return this.getKey(value);
    }

    @Override
    default Optional<V> fowlplay$getRandom(Random random) {
        return Util.getRandomOrEmpty(List.copyOf(this.getValues()), random);
    }
}
