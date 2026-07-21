package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.variant.*;
import aqario.fowlplay.common.registry.DataSerializerRegister;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.List;
import java.util.UUID;

public final class FPEntityDataSerializers {
    public static final DataSerializerRegister REGISTRAR = DataSerializerRegister.create(FowlPlay.ID);

    public static final EntityDataSerializer<ChickenVariant> CHICKEN_VARIANT = register(
        "chicken_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> DataSerializerRegister.writeRegistry(FPBuiltInRegistries.CHICKEN_VARIANT.get(), variant, buf),
            (buf) -> DataSerializerRegister.readRegistry(FPBuiltInRegistries.CHICKEN_VARIANT.get(), ChickenVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<DuckVariant> DUCK_VARIANT = register(
        "duck_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> DataSerializerRegister.writeRegistry(FPBuiltInRegistries.DUCK_VARIANT.get(), variant, buf),
            (buf) -> DataSerializerRegister.readRegistry(FPBuiltInRegistries.DUCK_VARIANT.get(), DuckVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<GooseVariant> GOOSE_VARIANT = register(
        "goose_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> DataSerializerRegister.writeRegistry(FPBuiltInRegistries.GOOSE_VARIANT.get(), variant, buf),
            (buf) -> DataSerializerRegister.readRegistry(FPBuiltInRegistries.GOOSE_VARIANT.get(), GooseVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<GullVariant> GULL_VARIANT = register(
        "gull_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> DataSerializerRegister.writeRegistry(FPBuiltInRegistries.GULL_VARIANT.get(), variant, buf),
            (buf) -> DataSerializerRegister.readRegistry(FPBuiltInRegistries.GULL_VARIANT.get(), GullVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<PigeonVariant> PIGEON_VARIANT = register(
        "pigeon_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> DataSerializerRegister.writeRegistry(FPBuiltInRegistries.PIGEON_VARIANT.get(), variant, buf),
            (buf) -> DataSerializerRegister.readRegistry(FPBuiltInRegistries.PIGEON_VARIANT.get(), PigeonVariant.class, buf)
        )
    );
    public static final EntityDataSerializer<SparrowVariant> SPARROW_VARIANT = register(
        "sparrow_variant",
        EntityDataSerializer.simple(
            (buf, variant) -> DataSerializerRegister.writeRegistry(FPBuiltInRegistries.SPARROW_VARIANT.get(), variant, buf),
            (buf) -> DataSerializerRegister.readRegistry(FPBuiltInRegistries.SPARROW_VARIANT.get(), SparrowVariant.class, buf)
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
        REGISTRAR.register(id, handler);
        return handler;
    }
}