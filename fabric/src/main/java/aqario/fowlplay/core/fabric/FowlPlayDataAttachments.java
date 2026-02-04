package aqario.fowlplay.core.fabric;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayBuiltInRegistries;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.Holder;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class FowlPlayDataAttachments {
    public static final AttachmentType<Holder<ChickenVariant>> CHICKEN_VARIANT = register(
        "chicken_variant",
        builder -> builder
            .initializer(() -> FowlPlayBuiltInRegistries.CHICKEN_VARIANT.getHolderOrThrow(ChickenVariant.WHITE))
            .persistent(FowlPlayBuiltInRegistries.CHICKEN_VARIANT.holderByNameCodec())
            .syncWith(
                ChickenVariant.PACKET_CODEC,
                AttachmentSyncPredicate.all()
            )
    );

    private static <T> AttachmentType<T> register(String id, Consumer<AttachmentRegistry.Builder<T>> builder) {
        return AttachmentRegistry.create(
            FowlPlay.id(id),
            builder
        );
    }

    public static void init() {
    }
}
