//? if neoforge {
/*package aqario.fowlplay.loaders.neoforge.core;

import aqario.fowlplay.common.entity.variant.ChickenVariant;
import aqario.fowlplay.core.FPBuiltInRegistries;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FPDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
        NeoForgeRegistries.ATTACHMENT_TYPES,
        FowlPlay.ID
    );

    public static final Supplier<AttachmentType<Holder<ChickenVariant>>> CHICKEN_VARIANT = register(
        "chicken_variant",
        AttachmentType.builder(
                () -> FPBuiltInRegistries.CHICKEN_VARIANT.getHolderOrThrow(ChickenVariant.WHITE).getDelegate()
            )
            .serialize(FPBuiltInRegistries.CHICKEN_VARIANT.holderByNameCodec())
            .sync(ChickenVariant.PACKET_CODEC)
    );

    private static <T> Supplier<AttachmentType<T>> register(String id, AttachmentType.Builder<T> builder) {
        return ATTACHMENT_TYPES.register(
            id,
            builder::build
        );
    }
}
*///?}
