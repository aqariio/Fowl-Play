package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public final class FowlPlayBiomeTags {
    public static final TagKey<Biome> SPAWNS_BLUE_JAYS = create("spawns_blue_jays");
    public static final TagKey<Biome> SPAWNS_CARDINALS = create("spawns_cardinals");
    public static final TagKey<Biome> SPAWNS_GULLS = create("spawns_gulls");
    public static final TagKey<Biome> SPAWNS_PENGUINS = create("spawns_penguins");
    public static final TagKey<Biome> SPAWNS_PIGEONS = create("spawns_pigeons");
    public static final TagKey<Biome> SPAWNS_ROBINS = create("spawns_robins");
    public static final TagKey<Biome> SPAWNS_SPARROWS = create("spawns_sparrows");

    private static TagKey<Biome> create(String id) {
        return TagKey.of(RegistryKeys.BIOME, Identifier.of(FowlPlay.ID, id));
    }
}
