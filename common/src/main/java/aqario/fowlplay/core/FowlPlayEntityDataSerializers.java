package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.*;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.List;
import java.util.UUID;

public final class FowlPlayEntityDataSerializers {
    public static final EntityDataSerializer<ChickenVariant> CHICKEN_VARIANT = register(
        "chicken_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayBuiltInRegistries.CHICKEN_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayBuiltInRegistries.CHICKEN_VARIANT.get(), ChickenVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<DuckVariant> DUCK_VARIANT = register(
        "duck_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayBuiltInRegistries.DUCK_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayBuiltInRegistries.DUCK_VARIANT.get(), DuckVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<GooseVariant> GOOSE_VARIANT = register(
        "goose_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayBuiltInRegistries.GOOSE_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayBuiltInRegistries.GOOSE_VARIANT.get(), GooseVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<GullVariant> GULL_VARIANT = register(
        "gull_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayBuiltInRegistries.GULL_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayBuiltInRegistries.GULL_VARIANT.get(), GullVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<PigeonVariant> PIGEON_VARIANT = register(
        "pigeon_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayBuiltInRegistries.PIGEON_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayBuiltInRegistries.PIGEON_VARIANT.get(), PigeonVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<SparrowVariant> SPARROW_VARIANT = register(
        "sparrow_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> PlatformHelper.writeRegistry(FowlPlayBuiltInRegistries.SPARROW_VARIANT.get(), variant, buf),
            (buf) -> PlatformHelper.readRegistry(FowlPlayBuiltInRegistries.SPARROW_VARIANT.get(), SparrowVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<List<UUID>> UUID_LIST = register(
        "uuid_list",
        EntityDataSerializer.simple(
            (buf, list) -> buf.writeCollection(list, FriendlyByteBuf::writeUUID),
            (buf) -> buf.readList(FriendlyByteBuf::readUUID)
        )
    );

    private static <T> EntityDataSerializer<T> register(String id, EntityDataSerializer<T> handler) {
        PlatformHelper.registerTrackedDataHandler(id, handler);
        return handler;
    }

    public static void init() {
    }
}
