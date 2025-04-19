package aqario.fowlplay.client.render.debug;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.common.network.s2c.DebugBirdCustomPayload;
import com.google.common.collect.Maps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class BirdDebugRenderer implements DebugRenderer.Renderer {
    public static final BirdDebugRenderer INSTANCE = new BirdDebugRenderer();
    private final MinecraftClient client;
    private final Map<UUID, DebugBirdCustomPayload.BirdData> birds = Maps.newHashMap();
    @Nullable
    private UUID targetedEntity;

    protected BirdDebugRenderer() {
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public void clear() {
        this.targetedEntity = null;
    }

    public void addBird(DebugBirdCustomPayload.BirdData birdData) {
        this.birds.put(birdData.uuid(), birdData);
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
            Entity entity = this.client.world.getEntityById(entry.getValue().entityId());
            return entity == null || entity.isRemoved();
        });
    }

    private void updateTargetedEntity() {
        DebugRenderer.getTargetedEntity(this.client.getCameraEntity(), 8).ifPresent(entity -> this.targetedEntity = entity.getUuid());
    }

    private boolean isClose(DebugBirdCustomPayload.BirdData birdData) {
        PlayerEntity playerEntity = this.client.player;
        BlockPos playerPos = BlockPos.ofFloored(playerEntity.getX(), birdData.pos().getY(), playerEntity.getZ());
        BlockPos brainPos = BlockPos.ofFloored(birdData.pos());
        // ignores y
        return playerPos.isWithinDistance(brainPos, 30.0);
    }

    private void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double x, double y, double z) {
        this.birds.values().forEach(brain -> {
            if(this.isClose(brain)) {
                this.drawBirdData(matrices, vertexConsumers, brain, x, y, z);
            }
        });
    }

    private void drawBirdData(
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, DebugBirdCustomPayload.BirdData birdData, double cameraX, double cameraY, double cameraZ
    ) {
        int i = 0;
        drawString(matrices, vertexConsumers, birdData.pos(), i, birdData.name(), -1, 0.03F);
        i++;
        drawString(matrices, vertexConsumers, birdData.pos(), i, "trusting: " + Arrays.toString(birdData.trusting().toArray()), -3355444, 0.02F);
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

        this.drawPath(matrices, vertexConsumers, birdData, cameraX, cameraY, cameraZ);
    }

    private void drawPath(
        MatrixStack matrices, VertexConsumerProvider vertexConsumers, DebugBirdCustomPayload.BirdData birdData, double cameraX, double cameraY, double cameraZ
    ) {
        if(birdData.path() != null) {
            PathfindingDebugRenderer.drawPath(matrices, vertexConsumers, birdData.path(), 0.5F, false, false, cameraX, cameraY, cameraZ);
        }
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
}
