package aqario.fowlplay.core.tags;

import aqario.fowlplay.core.FowlPlay;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public final class FowlPlayBiomeTags {
    public static final TagKey<Biome> SPAWNS_BLUE_JAYS = create("spawns_blue_jays");
    public static final TagKey<Biome> SPAWNS_CARDINALS = create("spawns_cardinals");
    public static final TagKey<Biome> SPAWNS_CHICKADEES = create("spawns_chickadees");
    public static final TagKey<Biome> SPAWNS_CROWS = create("spawns_crows");
    public static final TagKey<Biome> SPAWNS_DUCKS = create("spawns_ducks");
    public static final TagKey<Biome> SPAWNS_GEESE = create("spawns_geese");
    public static final TagKey<Biome> SPAWNS_GULLS = create("spawns_gulls");
    public static final TagKey<Biome> SPAWNS_HAWKS = create("spawns_hawks");
    public static final TagKey<Biome> SPAWNS_PENGUINS = create("spawns_penguins");
    public static final TagKey<Biome> SPAWNS_PIGEONS = create("spawns_pigeons");
    public static final TagKey<Biome> SPAWNS_RAVENS = create("spawns_ravens");
    public static final TagKey<Biome> SPAWNS_ROBINS = create("spawns_robins");
    public static final TagKey<Biome> SPAWNS_SPARROWS = create("spawns_sparrows");

    private static TagKey<Biome> create(String id) {
        return TagKey.of(RegistryKeys.BIOME, FowlPlay.id(id));
    }
}
