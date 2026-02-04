package aqario.fowlplay.common.network.clientbound;

import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.core.FowlPlay;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record BirdDebugPayload(BirdData birdData) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, BirdDebugPayload> STREAM_CODEC = CustomPacketPayload.codec(
        BirdDebugPayload::write, BirdDebugPayload::new
    );
    public static final CustomPacketPayload.Type<BirdDebugPayload> TYPE = new CustomPacketPayload.Type<>(
        FowlPlay.id("debug/bird")
    );

    private BirdDebugPayload(FriendlyByteBuf buf) {
        this(new BirdData(buf));
    }

    private void write(FriendlyByteBuf buf) {
        this.birdData.write(buf);
    }

    public static void onReceive(BirdDebugPayload payload) {
        BirdDebugRenderer.INSTANCE.addBird(payload.birdData());
    }

    @Override
    public CustomPacketPayload.Type<BirdDebugPayload> type() {
        return TYPE;
    }

    public record BirdData(
        UUID uuid,
        int entityId,
        String name,
        String moveControl,
        String navigation,
        float health,
        float maxHealth,
        Vec3 pos,
        String inventory,
        @Nullable Path path,
        List<String> trusting,
        boolean flying,
        boolean ambient,
        boolean perched,
        List<String> possibleActivities,
        List<String> runningTasks,
        List<String> memories,
        @Nullable String schedule,
        Set<BlockPos> pois,
        Set<BlockPos> potentialPois
    ) {
        public BirdData(FriendlyByteBuf buf) {
            this(
                buf.readUUID(),
                buf.readInt(),
                buf.readUtf(),
                buf.readUtf(),
                buf.readUtf(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readVec3(),
                buf.readUtf(),
                buf.readNullable(Path::createFromStream),
                buf.readList(FriendlyByteBuf::readUtf),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readList(FriendlyByteBuf::readUtf),
                buf.readList(FriendlyByteBuf::readUtf),
                buf.readList(FriendlyByteBuf::readUtf),
                buf.readNullable(FriendlyByteBuf::readUtf),
                buf.readCollection(HashSet::new, BlockPos.STREAM_CODEC),
                buf.readCollection(HashSet::new, BlockPos.STREAM_CODEC)
            );
        }

        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.uuid);
            buf.writeInt(this.entityId);
            buf.writeUtf(this.name);
            buf.writeUtf(this.moveControl);
            buf.writeUtf(this.navigation);
            buf.writeFloat(this.health);
            buf.writeFloat(this.maxHealth);
            buf.writeVec3(this.pos);
            buf.writeUtf(this.inventory);
            buf.writeNullable(this.path, (bufx, path) -> path.writeToStream(bufx));
            buf.writeCollection(this.trusting, FriendlyByteBuf::writeUtf);
            buf.writeBoolean(this.flying);
            buf.writeBoolean(this.ambient);
            buf.writeBoolean(this.perched);
            buf.writeCollection(this.possibleActivities, FriendlyByteBuf::writeUtf);
            buf.writeCollection(this.runningTasks, FriendlyByteBuf::writeUtf);
            buf.writeCollection(this.memories, FriendlyByteBuf::writeUtf);
            buf.writeNullable(this.schedule, FriendlyByteBuf::writeUtf);
            buf.writeCollection(this.pois, BlockPos.STREAM_CODEC);
            buf.writeCollection(this.potentialPois, BlockPos.STREAM_CODEC);
        }
    }
}
