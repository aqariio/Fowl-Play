package aqario.fowlplay.mixin.forge;

import aqario.fowlplay.core.platform.CommonRegistry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

@Mixin(IForgeRegistry.class)
public interface IForgeRegistryMixin<V> extends CommonRegistry<V> {
    @Shadow
    @NotNull
    Collection<V> getValues();

    @Shadow
    @Nullable
    V getValue(Identifier key);

    @Shadow
    @Nullable
    Identifier getKey(V value);

    @Shadow
    @NotNull
    Set<Map.Entry<RegistryKey<V>, V>> getEntries();

    @Shadow
    @Nullable
    ITagManager<V> tags();

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

    @Override
    default Optional<V> fowlplay$getRandomEntry(TagKey<V> tag, Random random) {
        ITagManager<V> tagManager = this.tags();
        if(tagManager == null) {
            return Optional.empty();
        }
        return tagManager.getTag(tag).getRandomElement(random);
    }
}
