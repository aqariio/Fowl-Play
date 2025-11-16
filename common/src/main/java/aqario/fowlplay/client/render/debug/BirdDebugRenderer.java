package aqario.fowlplay.client.render.debug;

import aqario.fowlplay.client.FowlPlayClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.client.render.debug.VillageDebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BirdDebugRenderer implements DebugRenderer.Renderer {
    public static final BirdDebugRenderer INSTANCE = new BirdDebugRenderer();
    private final MinecraftClient client;
    private final Map<UUID, BirdData> birds = Maps.newHashMap();
    @Nullable
    private UUID targetedEntity;

    private BirdDebugRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public void clear() {
        this.targetedEntity = null;
    }

    public void addBird(BirdData birdData) {
        this.birds.put(birdData.uuid(), birdData);
    }

    private boolean isTargeted(BirdData birdData) {
        return Objects.equals(this.targetedEntity, birdData.uuid());
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        if(!FowlPlayClient.DEBUG_BIRD) {
            return;
        }
        this.removeRemovedBirds();
        this.draw(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
        this.updateTargetedEntity();
    }

    private void removeRemovedBirds() {
        this.birds.entrySet().removeIf(entry -> {
            // noinspection ConstantConditions
            Entity entity = this.client.world.getEntityById(entry.getValue().entityId());
            return entity == null || entity.isRemoved();
        });
    }

    private void updateTargetedEntity() {
        DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(entity -> this.targetedEntity = entity.getUuid());
    }

    private boolean isClose(BirdData birdData) {
        PlayerEntity playerEntity = this.client.player;
        // noinspection ConstantConditions
        BlockPos playerPos = BlockPos.ofFloored(playerEntity.getX(), birdData.pos().getY(), playerEntity.getZ());
        BlockPos brainPos = BlockPos.ofFloored(birdData.pos());
        // ignores y
        return playerPos.isWithinDistance(brainPos, 30.0);
    }

    private void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double x, double y, double z) {
        this.birds.values().forEach(birdData -> {
            if(this.isClose(birdData)) {
                drawBirdData(matrices, vertexConsumers, birdData, this.isTargeted(birdData), x, y, z);
            }
        });
    }

    private static void drawBirdData(
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, BirdData birdData, boolean targeted, double cameraX, double cameraY, double cameraZ
    ) {
        int i = 0;
        drawString(matrices, vertexConsumers, birdData.pos(), i, birdData.name(), -1, 0.03F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "trusting: " + Arrays.toString(birdData.trusting().toArray()), -3355444, 0.02F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "flying: " + birdData.flying(), -1, 0.02F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "perched: " + birdData.perched(), -1, 0.02F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "ambient: " + birdData.ambient(), -1, 0.02F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "move control: " + birdData.moveControl(), -1, 0.02F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "navigation: " + birdData.navigation(), -1, 0.02F);
        i++;

        int j = birdData.health() < birdData.maxHealth() ? -23296 : -1;
        drawString(
            matrices,
            vertexConsumers,
            birdData.pos(),
            i,
            "health: " + String.format(Locale.ROOT, "%.1f", birdData.health()) + " / " + String.format(Locale.ROOT, "%.1f", birdData.maxHealth()),
            j,
            0.02F
        );
        i++;

        if(!birdData.inventory().isEmpty()) {
            drawString(matrices, vertexConsumers, birdData.pos(), i, birdData.inventory(), -98404, 0.02F);
        }

        for(String string : birdData.runningTasks()) {
            drawString(matrices, vertexConsumers, birdData.pos(), i, string, -16711681, 0.02F);
            i++;
        }

        for(String string : birdData.possibleActivities()) {
            drawString(matrices, vertexConsumers, birdData.pos(), i, string, -16711936, 0.02F);
            i++;
        }

        if(birdData.schedule() != null) {
            drawString(matrices, vertexConsumers, birdData.pos(), i, birdData.schedule(), -23296, 0.02F);
            i++;
        }

        if(targeted) {
            for(String string : Lists.reverse(birdData.memories())) {
                drawString(matrices, vertexConsumers, birdData.pos(), i, string, -3355444, 0.02F);
                i++;
            }
        }

        drawPath(matrices, vertexConsumers, birdData, cameraX, cameraY, cameraZ);
    }

    private static void drawPath(
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, BirdData birdData, double cameraX, double cameraY, double cameraZ
    ) {
        if(birdData.path() != null) {
            if(birdData.flying()) {
                drawPath(matrices, vertexConsumers, birdData.path(), 0.1F, false, false, cameraX, cameraY, cameraZ);
            }
            else {
                PathfindingDebugRenderer.drawPath(matrices, vertexConsumers, birdData.path(), 0.5F, false, false, cameraX, cameraY, cameraZ);
            }
        }
    }

    public static void drawPath(
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        Path path,
        float nodeSize,
        boolean drawDebugNodes,
        boolean drawLabels,
        double cameraX,
        double cameraY,
        double cameraZ
    ) {
        PathfindingDebugRenderer.drawPathLines(matrices, vertexConsumers.getBuffer(RenderLayer.getDebugLineStrip(6.0)), path, cameraX, cameraY, cameraZ);
        BlockPos blockPos = path.getTarget();
        if(getManhattanDistance(blockPos, cameraX, cameraY, cameraZ) <= 80.0F) {
            DebugRenderer.drawBox(
                matrices,
                vertexConsumers,
                new Box(blockPos.getX() + 0.25F, blockPos.getY() + 0.25F, blockPos.getZ() + 0.25, blockPos.getX() + 0.75F, blockPos.getY() + 0.75F, blockPos.getZ() + 0.75F)
                    .offset(-cameraX, -cameraY, -cameraZ),
                0.0F,
                1.0F,
                0.0F,
                0.5F
            );

            for(int i = 0; i < path.getLength(); i++) {
                PathNode pathNode = path.getNode(i);
                if(getManhattanDistance(pathNode.getBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    float f = i == path.getCurrentNodeIndex() ? 1.0F : 0.0F;
                    float g = i == path.getCurrentNodeIndex() ? 0.0F : 1.0F;
                    DebugRenderer.drawBox(
                        matrices,
                        vertexConsumers,
                        new Box(
                            pathNode.x + 0.5F - nodeSize,
                            pathNode.y + 0.5F - nodeSize,
                            pathNode.z + 0.5F - nodeSize,
                            pathNode.x + 0.5F + nodeSize,
                            pathNode.y + 0.5F + nodeSize,
                            pathNode.z + 0.5F + nodeSize
                        )
                            .offset(-cameraX, -cameraY, -cameraZ),
                        f,
                        0.0F,
                        g,
                        0.5F
                    );
                }
            }
        }

        if(drawDebugNodes) {
            for(PathNode pathNode2 : path.getDebugSecondNodes()) {
                if(getManhattanDistance(pathNode2.getBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    DebugRenderer.drawBox(
                        matrices,
                        vertexConsumers,
                        new Box(
                            pathNode2.x + 0.5F - nodeSize / 2.0F,
                            pathNode2.y + 0.5F - nodeSize / 2.0F,
                            pathNode2.z + 0.5F - nodeSize / 2.0F,
                            pathNode2.x + 0.5F + nodeSize / 2.0F,
                            pathNode2.y + 0.5F + nodeSize / 2.0F,
                            pathNode2.z + 0.5F + nodeSize / 2.0F
                        )
                            .offset(-cameraX, -cameraY, -cameraZ),
                        1.0F,
                        0.8F,
                        0.8F,
                        0.5F
                    );
                }
            }

            for(PathNode pathNode2x : path.getDebugNodes()) {
                if(getManhattanDistance(pathNode2x.getBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    DebugRenderer.drawBox(
                        matrices,
                        vertexConsumers,
                        new Box(
                            pathNode2x.x + 0.5F - nodeSize / 2.0F,
                            pathNode2x.y + 0.5F - nodeSize / 2.0F,
                            pathNode2x.z + 0.5F - nodeSize / 2.0F,
                            pathNode2x.x + 0.5F + nodeSize / 2.0F,
                            pathNode2x.y + 0.5F + nodeSize / 2.0F,
                            pathNode2x.z + 0.5F + nodeSize / 2.0F
                        )
                            .offset(-cameraX, -cameraY, -cameraZ),
                        0.8F,
                        1.0F,
                        1.0F,
                        0.5F
                    );
                }
            }
        }

        if(drawLabels) {
            for(int ix = 0; ix < path.getLength(); ix++) {
                PathNode pathNode = path.getNode(ix);
                if(getManhattanDistance(pathNode.getBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    DebugRenderer.drawString(
                        matrices, vertexConsumers, String.valueOf(pathNode.type), pathNode.x + 0.5, pathNode.y + 0.75, pathNode.z + 0.5, -1, 0.02F, true, 0.0F, true
                    );
                    DebugRenderer.drawString(
                        matrices,
                        vertexConsumers,
                        String.format(Locale.ROOT, "%.2f", pathNode.penalty),
                        pathNode.x + 0.5,
                        pathNode.y + 0.25,
                        pathNode.z + 0.5,
                        -1,
                        0.02F,
                        true,
                        0.0F,
                        true
                    );
                }
            }
        }
    }

    private static float getManhattanDistance(BlockPos pos, double x, double y, double z) {
        return (float) (Math.abs(pos.getX() - x) + Math.abs(pos.getY() - y) + Math.abs(pos.getZ() - z));
    }

    private static void drawString(
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, String string, VillageDebugRenderer.PointOfInterest pointOfInterest, int offsetY, int color
    ) {
        drawString(matrices, vertexConsumers, string, pointOfInterest.pos, offsetY, color);
    }

    private static void drawString(MatrixStack matrices, VertexConsumerProvider vertexConsumers, String string, BlockPos pos, int offsetY, int color) {
        double f = (double) pos.getX() + 0.5;
        double g = (double) pos.getY() + 1.3 + (double) offsetY * 0.2;
        double h = (double) pos.getZ() + 0.5;
        DebugRenderer.drawString(matrices, vertexConsumers, string, f, g, h, color, 0.02F, true, 0.0F, true);
    }

    private static void drawString(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Position pos, int offsetY, String string, int color, float size) {
//        BlockPos blockPos = BlockPos.ofFloored(pos);
        double f = pos.getX() + 0.5;
        double g = pos.getY() + 2.4 + (double) offsetY * 0.25;
        double h = pos.getZ() + 0.5;
        DebugRenderer.drawString(matrices, vertexConsumers, string, f, g, h, color, size, false, 0.5F, true);
    }

    public record BirdData(
        UUID uuid,
        int entityId,
        String name,
        String moveControl,
        String navigation,
        float health,
        float maxHealth,
        Vec3d pos,
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
        public BirdData(PacketByteBuf buf) {
            this(
                buf.readUuid(),
                buf.readInt(),
                buf.readString(),
                buf.readString(),
                buf.readString(),
                buf.readFloat(),
                buf.readFloat(),
                new Vec3d(
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble()
                ),
                buf.readString(),
                buf.readNullable(Path::fromBuffer),
                buf.readList(PacketByteBuf::readString),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readList(PacketByteBuf::readString),
                buf.readList(PacketByteBuf::readString),
                buf.readList(PacketByteBuf::readString),
                buf.readNullable(PacketByteBuf::readString),
                buf.readCollection(HashSet::new, PacketByteBuf::readBlockPos),
                buf.readCollection(HashSet::new, PacketByteBuf::readBlockPos)
            );
        }

        public void write(PacketByteBuf buf) {
            buf.writeUuid(this.uuid);
            buf.writeInt(this.entityId);
            buf.writeString(this.name);
            buf.writeString(this.moveControl);
            buf.writeString(this.navigation);
            buf.writeFloat(this.health);
            buf.writeFloat(this.maxHealth);
            buf.writeDouble(this.pos.getX());
            buf.writeDouble(this.pos.getY());
            buf.writeDouble(this.pos.getZ());
            buf.writeString(this.inventory);
            buf.writeNullable(this.path, (bufx, pathx) -> pathx.toBuffer(bufx));
            buf.writeCollection(trusting, PacketByteBuf::writeString);
            buf.writeBoolean(this.flying);
            buf.writeBoolean(this.ambient);
            buf.writeBoolean(this.perched);
            buf.writeCollection(this.possibleActivities, PacketByteBuf::writeString);
            buf.writeCollection(this.runningTasks, PacketByteBuf::writeString);
            buf.writeCollection(this.memories, PacketByteBuf::writeString);
            buf.writeNullable(this.schedule, PacketByteBuf::writeString);
            buf.writeCollection(this.pois, PacketByteBuf::writeBlockPos);
            buf.writeCollection(this.potentialPois, PacketByteBuf::writeBlockPos);
        }
    }
}