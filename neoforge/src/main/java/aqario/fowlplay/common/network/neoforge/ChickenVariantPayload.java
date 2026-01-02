package aqario.fowlplay.common.network.neoforge;

import aqario.fowlplay.common.entity.bird.ChickenVariant;
import aqario.fowlplay.core.FowlPlay;
import aqario.fowlplay.core.FowlPlayRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ChickenVariantPayload(int entityId, Holder<ChickenVariant> variant) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, ChickenVariantPayload> CODEC = CustomPacketPayload.codec(
        ChickenVariantPayload::write,
        ChickenVariantPayload::new
    );
    public static final CustomPacketPayload.Type<ChickenVariantPayload> ID = new CustomPacketPayload.Type<>(
        FowlPlay.id("chicken_variant")
    );

    private ChickenVariantPayload(RegistryFriendlyByteBuf buf) {
        this(
            buf.readInt(),
            ByteBufCodecs.holderRegistry(FowlPlayRegistries.CHICKEN_VARIANT).decode(buf)
        );
    }

    private void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        ByteBufCodecs.holderRegistry(FowlPlayRegistries.CHICKEN_VARIANT).encode(buf, this.variant);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
