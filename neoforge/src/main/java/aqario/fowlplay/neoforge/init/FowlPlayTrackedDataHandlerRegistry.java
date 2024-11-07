package aqario.fowlplay.neoforge.init;

import aqario.fowlplay.common.FowlPlay;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.UUID;

public final class FowlPlayTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<List<UUID>> UUID_LIST = register("uuid_list", TrackedDataHandler.create(Uuids.PACKET_CODEC.collect(PacketCodecs.toList())));

    private static <T> TrackedDataHandler<T> register(String id, TrackedDataHandler<T> handler) {
        Registry.register(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Identifier.of(FowlPlay.ID, id), handler);
        return handler;
    }

    public static void init() {
    }
}
