package aqario.fowlplay.client.render.debug;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.common.network.clientbound.GenericDebugPayload;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class GenericDebugRenderer implements FowlPlayDebugRenderers.LerpedDebugRenderer {
    public static final GenericDebugRenderer INSTANCE = new GenericDebugRenderer();
    private final Minecraft client;
    private final Map<UUID, GenericDebugPayload.Data> mobs = Maps.newHashMap();
    @Nullable
    private UUID targetedEntity;

    private GenericDebugRenderer() {
        this.client = Minecraft.getInstance();
    }

    @Override
    public void clear() {
        this.targetedEntity = null;
    }

    public void addData(GenericDebugPayload.Data data) {
        this.mobs.put(data.uuid(), data);
    }

    private boolean isTargeted(GenericDebugPayload.Data data) {
        return Objects.equals(this.targetedEntity, data.uuid());
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ, double partialTick) {
        if(!FowlPlayClient.DEBUG_GENERIC) {
            return;
        }
        this.removeRemovedData();
        this.draw(matrices, vertexConsumers, cameraX, cameraY, cameraZ, partialTick);
        this.updateTargetedEntity();
    }

    private void removeRemovedData() {
        this.mobs.entrySet().removeIf(entry -> {
            // noinspection ConstantConditions
            Entity entity = this.client.level.getEntity(entry.getValue().entityId());
            return entity == null || entity.isRemoved();
        });
    }

    private void updateTargetedEntity() {
        DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 30).ifPresent(entity -> this.targetedEntity = entity.getUUID());
    }

    private boolean isClose(GenericDebugPayload.Data data) {
        Player playerEntity = this.client.player;
        // noinspection ConstantConditions
        BlockPos playerPos = BlockPos.containing(playerEntity.getX(), data.pos().y(), playerEntity.getZ());
        BlockPos birdPos = BlockPos.containing(data.pos());
        // ignores y
        return playerPos.closerThan(birdPos, 30.0);
    }

    private void draw(PoseStack matrices, MultiBufferSource vertexConsumers, double x, double y, double z, double partialTick) {
        this.mobs.values().forEach(data -> {
            if(this.isClose(data)) {
                drawData(matrices, vertexConsumers, data, this.isTargeted(data), x, y, z, partialTick);
            }
        });
    }

    private static void drawData(
        PoseStack matrices, MultiBufferSource vertexConsumers, GenericDebugPayload.Data data, boolean targeted, double cameraX, double cameraY, double cameraZ, double partialTick
    ) {
        int i = 0;
        for(Map.Entry<String, String> entry : data.data().entrySet()) {
            drawString(matrices, vertexConsumers, data.pos(), i, entry.getKey() + ": " + entry.getValue(), targeted ? -1 : 0xaaaaaa, 0.02F, partialTick);
            i++;
        }
    }

    private static void drawString(PoseStack matrices, MultiBufferSource vertexConsumers, Position pos, int offsetY, String string, int color, float size, double partialTick) {
        double f = pos.x() + 0.5;
        double g = pos.y() + 1.4 + (double) offsetY * 0.25;
        double h = pos.z() + 0.5;
        DebugRenderer.renderFloatingText(matrices, vertexConsumers, string, f, g, h, color, size, false, 0.5F, false);
    }
}
