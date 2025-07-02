package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.List;
import java.util.UUID;

public final class FowlPlayTrackedDataHandlerRegistry {
    public static final TrackedDataHandler<ChickenVariant> CHICKEN_VARIANT = register(
        "chicken_variant",
        TrackedDataHandler.of(FowlPlayRegistries.CHICKEN_VARIANT.get())
    );
    public static final TrackedDataHandler<DuckVariant> DUCK_VARIANT = register(
        "duck_variant",
        TrackedDataHandler.of(FowlPlayRegistries.DUCK_VARIANT.get())
    );
    public static final TrackedDataHandler<GullVariant> GULL_VARIANT = register(
        "gull_variant",
        TrackedDataHandler.of(FowlPlayRegistries.GULL_VARIANT.get())
    );
    public static final TrackedDataHandler<PigeonVariant> PIGEON_VARIANT = register(
        "pigeon_variant",
        TrackedDataHandler.of(FowlPlayRegistries.PIGEON_VARIANT.get())
    );
    public static final TrackedDataHandler<SparrowVariant> SPARROW_VARIANT = register(
        "sparrow_variant",
        TrackedDataHandler.of(FowlPlayRegistries.SPARROW_VARIANT.get())
    );
    public static final TrackedDataHandler<List<UUID>> UUID_LIST = register(
        "uuid_list",
        TrackedDataHandler.of(
            (buf, list) -> buf.writeCollection(list, PacketByteBuf::writeUuid),
            (buf) -> buf.readList(PacketByteBuf::readUuid)
        )
    );

    private static <T> TrackedDataHandler<T> register(String id, TrackedDataHandler<T> handler) {
        PlatformHelper.registerTrackedDataHandler(id, handler);
        return handler;
    }

    public static void init() {
    }
}
