package aqario.fowlplay.common.tags;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class FowlPlayBiomeTags {
    public static final TagKey<Biome> SPAWNS_CARDINALS = create("spawns_cardinals");
    public static final TagKey<Biome> SPAWNS_PENGUINS = create("spawns_penguins");
    public static final TagKey<Biome> SPAWNS_PIGEONS = create("spawns_pigeons");
    public static final TagKey<Biome> SPAWNS_ROBINS = create("spawns_robins");
    public static final TagKey<Biome> SPAWNS_SEAGULLS = create("spawns_seagulls");

    private static TagKey<Biome> create(String id) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(FowlPlay.ID, id));
    }
}
