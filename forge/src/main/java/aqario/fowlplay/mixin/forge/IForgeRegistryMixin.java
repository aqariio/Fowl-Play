package aqario.fowlplay.mixin.forge;

import aqario.fowlplay.common.registry.CommonRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;
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
    @NotNull
    Collection<V> getValues();

    @Shadow
    @Nullable
    V getValue(ResourceLocation key);

    @Shadow
    @Nullable
    ResourceLocation getKey(V value);

    @Shadow
    @Nullable
    ITagManager<V> tags();

    @Shadow
    @NotNull Optional<Holder.Reference<V>> getDelegate(V value);

    @Override
    default V fowlplay$get(ResourceLocation id) {
        return this.getValue(id);
    }

    @Override
    default ResourceLocation fowlplay$getKey(V value) {
        return this.getKey(value);
    }

    @Override
    default Optional<V> fowlplay$getRandom(RandomSource random) {
        return Util.getRandomSafe(List.copyOf(this.getValues()), random);
    }

    @Override
    default Optional<V> fowlplay$getRandomElement(TagKey<V> tag, RandomSource random) {
        ITagManager<V> tagManager = this.tags();
        if(tagManager == null) {
            return Optional.empty();
        }
        return tagManager.getTag(tag).getRandomElement(random);
    }

    @Override
    default Optional<Holder.Reference<V>> fowlplay$getHolder(V value) {
        return this.getDelegate(value);
    }
}
