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
        TrackedDataHandler.of(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayRegistries.CHICKEN_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayRegistries.CHICKEN_VARIANT.get(), ChickenVariant.class, buf)
        )
    );
    public static final TrackedDataHandler<DuckVariant> DUCK_VARIANT = register(
        "duck_variant",
        TrackedDataHandler.of(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayRegistries.DUCK_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayRegistries.DUCK_VARIANT.get(), DuckVariant.class, buf)
        )
    );
    public static final TrackedDataHandler<GooseVariant> GOOSE_VARIANT = register(
        "goose_variant",
        TrackedDataHandler.of(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayRegistries.GOOSE_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayRegistries.GOOSE_VARIANT.get(), GooseVariant.class, buf)
        )
    );
    public static final TrackedDataHandler<GullVariant> GULL_VARIANT = register(
        "gull_variant",
        TrackedDataHandler.of(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayRegistries.GULL_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayRegistries.GULL_VARIANT.get(), GullVariant.class, buf)
        )
    );
    public static final TrackedDataHandler<PigeonVariant> PIGEON_VARIANT = register(
        "pigeon_variant",
        TrackedDataHandler.of(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayRegistries.PIGEON_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayRegistries.PIGEON_VARIANT.get(), PigeonVariant.class, buf)
        )
    );
    public static final TrackedDataHandler<SparrowVariant> SPARROW_VARIANT = register(
        "sparrow_variant",
        TrackedDataHandler.of(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayRegistries.SPARROW_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayRegistries.SPARROW_VARIANT.get(), SparrowVariant.class, buf)
        )
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
