//~ expect_platform

package aqario.fowlplay.common.worldgen;

import aqario.fowlplay.fabric.common.worldgen.BiomeModifierImpl;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BiomeModifier {
    private static final Object2ObjectOpenHashMap<Predicate<Context>, BiConsumer<Context, Modifier>> MODIFICATIONS = new Object2ObjectOpenHashMap<>();

    public static void add(Predicate<Context> predicate, BiConsumer<Context, Modifier> modifier) {
        MODIFICATIONS.put(predicate, modifier);
    }

    public static void applyModifications(Context context, Modifier changes) {
        MODIFICATIONS.forEach((predicate, modifier) -> {
            if(predicate.test(context)) {
                modifier.accept(context, changes);
            }
        });
    }

    public static void register() {
        BiomeModifierImpl.register();
    }

    public interface Context {
        ResourceKey<Biome> key();

        boolean is(TagKey<Biome> tag);

        boolean is(ResourceKey<Biome> biome);
    }

    public abstract static class Modifier {
        public abstract void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData data);

        public abstract void removeSpawn(EntityType<?> type);

        public abstract void setSpawnCost(EntityType<?> type, MobSpawnSettings.MobSpawnCost cost);
    }
}
