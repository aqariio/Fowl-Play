package aqario.fowlplay.common.entity.data;

import aqario.fowlplay.common.entity.DuckVariant;
import aqario.fowlplay.common.entity.GullVariant;
import aqario.fowlplay.common.entity.PigeonVariant;
import aqario.fowlplay.common.entity.SparrowVariant;
import aqario.fowlplay.common.registry.FowlPlayRegistries;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;

import java.util.List;
import java.util.UUID;

public final class FowlPlayTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<DuckVariant> DUCK_VARIANT = register(
        TrackedDataHandler.of(FowlPlayRegistries.DUCK_VARIANT)
    );
    public static final TrackedDataHandler<GullVariant> GULL_VARIANT = register(
        TrackedDataHandler.of(FowlPlayRegistries.GULL_VARIANT)
    );
    public static final TrackedDataHandler<PigeonVariant> PIGEON_VARIANT = register(
        TrackedDataHandler.of(FowlPlayRegistries.PIGEON_VARIANT)
    );
    public static final TrackedDataHandler<SparrowVariant> SPARROW_VARIANT = register(
        TrackedDataHandler.of(FowlPlayRegistries.SPARROW_VARIANT)
    );
    public static final TrackedDataHandler<List<UUID>> UUID_LIST = register(TrackedDataHandler.of(
        (buf, list) -> buf.writeCollection(list, PacketByteBuf::writeUuid),
        (buf) -> buf.readList(PacketByteBuf::readUuid)
    ));

    private static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> handler) {
        TrackedDataHandlerRegistry.register(handler);
        return handler;
    }

    public static void init() {
    }
}
