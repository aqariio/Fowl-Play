package aqario.fowlplay.core.fabric;

import aqario.fowlplay.common.entity.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

import java.util.function.UnaryOperator;

@SuppressWarnings("UnstableApiUsage")
public class FowlPlayDataAttachments {
    public static final AttachmentType<ChickenVariant> CHICKEN_VARIANT = register(
        "chicken_variant",
        builder -> builder
            .initializer(ChickenVariant.WHITE)
            .persistent(ChickenVariant.CODEC)
    );

    private static <T> AttachmentType<T> register(String id, UnaryOperator<AttachmentRegistry.Builder<T>> builder) {
        return builder.andThen(b -> b.buildAndRegister(FowlPlay.id(id)))
            .apply(AttachmentRegistry.builder());
    }

    public static void init() {
    }
}
