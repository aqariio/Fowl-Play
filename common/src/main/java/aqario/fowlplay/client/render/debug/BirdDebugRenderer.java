package aqario.fowlplay.client.render.debug;

import aqario.fowlplay.client.FowlPlayClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.debug.BrainDebugRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.debug.PathfindingRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BirdDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
    public static final BirdDebugRenderer INSTANCE = new BirdDebugRenderer();
    private final Minecraft client;
    private final Map<UUID, BirdData> birds = Maps.newHashMap();
    @Nullable
    private UUID targetedEntity;

    private BirdDebugRenderer() {
        this.client = Minecraft.getInstance();
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
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ) {
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
            Entity entity = this.client.level.getEntity(entry.getValue().entityId());
            return entity == null || entity.isRemoved();
        });
    }

    private void updateTargetedEntity() {
        DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(entity -> this.targetedEntity = entity.getUUID());
    }

    private boolean isClose(BirdData birdData) {
        Player playerEntity = this.client.player;
        // noinspection ConstantConditions
        BlockPos playerPos = BlockPos.containing(playerEntity.getX(), birdData.pos().y(), playerEntity.getZ());
        BlockPos brainPos = BlockPos.containing(birdData.pos());
        // ignores y
        return playerPos.closerThan(brainPos, 30.0);
    }

    private void draw(PoseStack matrices, MultiBufferSource vertexConsumers, double x, double y, double z) {
        this.birds.values().forEach(birdData -> {
            if(this.isClose(birdData)) {
                drawBirdData(matrices, vertexConsumers, birdData, this.isTargeted(birdData), x, y, z);
            }
        });
    }

    private static void drawBirdData(
        PoseStack matrices, MultiBufferSource vertexConsumers, BirdData birdData, boolean targeted, double cameraX, double cameraY, double cameraZ
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
        PoseStack matrices, MultiBufferSource vertexConsumers, BirdData birdData, double cameraX, double cameraY, double cameraZ
    ) {
        if(birdData.path() != null) {
            if(birdData.flying()) {
                drawPath(matrices, vertexConsumers, birdData.path(), 0.1F, false, false, cameraX, cameraY, cameraZ);
            }
            else {
                PathfindingRenderer.renderPath(matrices, vertexConsumers, birdData.path(), 0.5F, false, false, cameraX, cameraY, cameraZ);
            }
        }
    }

    public static void drawPath(
        PoseStack matrices,
        MultiBufferSource vertexConsumers,
        Path path,
        float nodeSize,
        boolean drawDebugNodes,
        boolean drawLabels,
        double cameraX,
        double cameraY,
        double cameraZ
    ) {
        PathfindingRenderer.renderPathLine(matrices, vertexConsumers.getBuffer(RenderType.debugLineStrip(6.0)), path, cameraX, cameraY, cameraZ);
        BlockPos blockPos = path.getTarget();
        if(getManhattanDistance(blockPos, cameraX, cameraY, cameraZ) <= 80.0F) {
            DebugRenderer.renderFilledBox(
                matrices,
                vertexConsumers,
                new AABB(blockPos.getX() + 0.25F, blockPos.getY() + 0.25F, blockPos.getZ() + 0.25, blockPos.getX() + 0.75F, blockPos.getY() + 0.75F, blockPos.getZ() + 0.75F)
                    .move(-cameraX, -cameraY, -cameraZ),
                0.0F,
                1.0F,
                0.0F,
                0.5F
            );

            for(int i = 0; i < path.getNodeCount(); i++) {
                Node pathNode = path.getNode(i);
                if(getManhattanDistance(pathNode.asBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    float f = i == path.getNextNodeIndex() ? 1.0F : 0.0F;
                    float g = i == path.getNextNodeIndex() ? 0.0F : 1.0F;
                    DebugRenderer.renderFilledBox(
                        matrices,
                        vertexConsumers,
                        new AABB(
                            pathNode.x + 0.5F - nodeSize,
                            pathNode.y + 0.5F - nodeSize,
                            pathNode.z + 0.5F - nodeSize,
                            pathNode.x + 0.5F + nodeSize,
                            pathNode.y + 0.5F + nodeSize,
                            pathNode.z + 0.5F + nodeSize
                        )
                            .move(-cameraX, -cameraY, -cameraZ),
                        f,
                        0.0F,
                        g,
                        0.5F
                    );
                }
            }
        }

        if(drawDebugNodes) {
            for(Node pathNode2 : path.getClosedSet()) {
                if(getManhattanDistance(pathNode2.asBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    DebugRenderer.renderFilledBox(
                        matrices,
                        vertexConsumers,
                        new AABB(
                            pathNode2.x + 0.5F - nodeSize / 2.0F,
                            pathNode2.y + 0.5F - nodeSize / 2.0F,
                            pathNode2.z + 0.5F - nodeSize / 2.0F,
                            pathNode2.x + 0.5F + nodeSize / 2.0F,
                            pathNode2.y + 0.5F + nodeSize / 2.0F,
                            pathNode2.z + 0.5F + nodeSize / 2.0F
                        )
                            .move(-cameraX, -cameraY, -cameraZ),
                        1.0F,
                        0.8F,
                        0.8F,
                        0.5F
                    );
                }
            }

            for(Node pathNode2x : path.getOpenSet()) {
                if(getManhattanDistance(pathNode2x.asBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    DebugRenderer.renderFilledBox(
                        matrices,
                        vertexConsumers,
                        new AABB(
                            pathNode2x.x + 0.5F - nodeSize / 2.0F,
                            pathNode2x.y + 0.5F - nodeSize / 2.0F,
                            pathNode2x.z + 0.5F - nodeSize / 2.0F,
                            pathNode2x.x + 0.5F + nodeSize / 2.0F,
                            pathNode2x.y + 0.5F + nodeSize / 2.0F,
                            pathNode2x.z + 0.5F + nodeSize / 2.0F
                        )
                            .move(-cameraX, -cameraY, -cameraZ),
                        0.8F,
                        1.0F,
                        1.0F,
                        0.5F
                    );
                }
            }
        }

        if(drawLabels) {
            for(int ix = 0; ix < path.getNodeCount(); ix++) {
                Node pathNode = path.getNode(ix);
                if(getManhattanDistance(pathNode.asBlockPos(), cameraX, cameraY, cameraZ) <= 80.0F) {
                    DebugRenderer.renderFloatingText(
                        matrices, vertexConsumers, String.valueOf(pathNode.type), pathNode.x + 0.5, pathNode.y + 0.75, pathNode.z + 0.5, -1, 0.02F, true, 0.0F, true
                    );
                    DebugRenderer.renderFloatingText(
                        matrices,
                        vertexConsumers,
                        String.format(Locale.ROOT, "%.2f", pathNode.costMalus),
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
        PoseStack matrices, MultiBufferSource vertexConsumers, String string, BrainDebugRenderer.PoiInfo pointOfInterest, int offsetY, int color
    ) {
        drawString(matrices, vertexConsumers, string, pointOfInterest.pos, offsetY, color);
    }

    private static void drawString(PoseStack matrices, MultiBufferSource vertexConsumers, String string, BlockPos pos, int offsetY, int color) {
        double f = (double) pos.getX() + 0.5;
        double g = (double) pos.getY() + 1.3 + (double) offsetY * 0.2;
        double h = (double) pos.getZ() + 0.5;
        DebugRenderer.renderFloatingText(matrices, vertexConsumers, string, f, g, h, color, 0.02F, true, 0.0F, true);
    }

    private static void drawString(PoseStack matrices, MultiBufferSource vertexConsumers, Position pos, int offsetY, String string, int color, float size) {
//        BlockPos blockPos = BlockPos.ofFloored(pos);
        double f = pos.x() + 0.5;
        double g = pos.y() + 2.4 + (double) offsetY * 0.25;
        double h = pos.z() + 0.5;
        DebugRenderer.renderFloatingText(matrices, vertexConsumers, string, f, g, h, color, size, false, 0.5F, true);
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
                new Vec3(
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble()
                ),
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
                buf.readCollection(HashSet::new, FriendlyByteBuf::readBlockPos),
                buf.readCollection(HashSet::new, FriendlyByteBuf::readBlockPos)
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
            buf.writeDouble(this.pos.x());
            buf.writeDouble(this.pos.y());
            buf.writeDouble(this.pos.z());
            buf.writeUtf(this.inventory);
            buf.writeNullable(this.path, (bufx, pathx) -> pathx.writeToStream(bufx));
            buf.writeCollection(trusting, FriendlyByteBuf::writeUtf);
            buf.writeBoolean(this.flying);
            buf.writeBoolean(this.ambient);
            buf.writeBoolean(this.perched);
            buf.writeCollection(this.possibleActivities, FriendlyByteBuf::writeUtf);
            buf.writeCollection(this.runningTasks, FriendlyByteBuf::writeUtf);
            buf.writeCollection(this.memories, FriendlyByteBuf::writeUtf);
            buf.writeNullable(this.schedule, FriendlyByteBuf::writeUtf);
            buf.writeCollection(this.pois, FriendlyByteBuf::writeBlockPos);
            buf.writeCollection(this.potentialPois, FriendlyByteBuf::writeBlockPos);
        }
    }
}