package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.core.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.List;
import java.util.UUID;

public final class FowlPlayEntityDataSerializers {
    public static final EntityDataSerializer<Holder<ChickenVariant>> CHICKEN_VARIANT = register(
        "chicken_variant",
        EntityDataSerializer.forValueType(ChickenVariant.PACKET_CODEC)
    );
    public static final EntityDataSerializer<Holder<DuckVariant>> DUCK_VARIANT = register(
        "duck_variant",
        EntityDataSerializer.forValueType(DuckVariant.PACKET_CODEC)
    );
    public static final EntityDataSerializer<Holder<GooseVariant>> GOOSE_VARIANT = register(
        "goose_variant",
        EntityDataSerializer.forValueType(GooseVariant.PACKET_CODEC)
    );
    public static final EntityDataSerializer<Holder<GullVariant>> GULL_VARIANT = register(
        "gull_variant",
        EntityDataSerializer.forValueType(GullVariant.PACKET_CODEC)
    );
    public static final EntityDataSerializer<Holder<PigeonVariant>> PIGEON_VARIANT = register(
        "pigeon_variant",
        EntityDataSerializer.forValueType(PigeonVariant.PACKET_CODEC)
    );
    public static final EntityDataSerializer<Holder<SparrowVariant>> SPARROW_VARIANT = register(
        "sparrow_variant",
        EntityDataSerializer.forValueType(SparrowVariant.PACKET_CODEC)
    );
    public static final EntityDataSerializer<List<UUID>> UUID_LIST = register(
        "uuid_list",
        EntityDataSerializer.forValueType(UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs.list()))
    );

    private static <T> EntityDataSerializer<T> register(String id, EntityDataSerializer<T> handler) {
        PlatformHelper.registerTrackedDataHandler(id, handler);
        return handler;
    }

    public static void init() {
    }
}
