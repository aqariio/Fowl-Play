package aqario.fowlplay.common.network.clientbound;

import aqario.fowlplay.client.render.debug.GenericDebugRenderer;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.UUID;

public record GenericDebugPayload(Data data) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, GenericDebugPayload> STREAM_CODEC = CustomPacketPayload.codec(
        GenericDebugPayload::write, GenericDebugPayload::new
    );
    public static final Type<GenericDebugPayload> TYPE = new Type<>(
        FowlPlay.id("debug/generic")
    );

    private GenericDebugPayload(FriendlyByteBuf buf) {
        this(new Data(buf));
    }

    private void write(FriendlyByteBuf buf) {
        this.data.write(buf);
    }

    public static void onReceive(GenericDebugPayload payload) {
        GenericDebugRenderer.INSTANCE.addData(payload.data());
    }

    @Override
    public Type<GenericDebugPayload> type() {
        return TYPE;
    }

    public record Data(
        UUID uuid,
        int entityId,
        Vec3 pos,
        Map<String, String> data
    ) {
        public Data(FriendlyByteBuf buf) {
            this(
                buf.readUUID(),
                buf.readInt(),
                buf.readVec3(),
                buf.readMap(FriendlyByteBuf::readUtf, b -> b.readUtf())
            );
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.uuid);
            buf.writeInt(this.entityId);
            buf.writeVec3(this.pos);
            buf.writeMap(this.data, FriendlyByteBuf::writeUtf, (b, v) -> b.writeUtf(v));
        }
    }
}
