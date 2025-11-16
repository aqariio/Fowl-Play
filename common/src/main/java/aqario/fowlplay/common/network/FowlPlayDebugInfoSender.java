package aqario.fowlplay.common.network;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlay;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.NameGenerator;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FowlPlayDebugInfoSender {
    @SuppressWarnings("deprecation")
    public static void sendBirdDebugData(BirdEntity bird) {
        if(!FowlPlay.isDebugUtilsLoaded()
            || bird.getWorld().isClient()
            || !FowlPlayClient.DEBUG_BIRD
        ) {
            return;
        }

        Brain<?> brain = bird.getBrain();
        String name = NameGenerator.name(bird);
        String inventory = "";
        Path path = null;
        boolean flying = bird instanceof FlyingBirdEntity flyingBird && flyingBird.isFlying();
        if(bird instanceof InventoryOwner inventoryOwner) {
            inventory = inventoryOwner.getInventory().isEmpty() ? "" : inventoryOwner.getInventory().toString();
        }
        if(BrainUtils.hasMemory(brain, MemoryModuleType.PATH)) {
            path = BrainUtils.getMemory(brain, MemoryModuleType.PATH);
        }
        List<String> trusting = new ArrayList<>();
        if(bird instanceof TrustingBirdEntity trustingBird) {
            trustingBird.getTrustedUuids().forEach(uuid -> {
                PlayerEntity player = bird.getWorld().getPlayerByUuid(uuid);
                if(player != null) {
                    trusting.add(player.getName().getString());
                }
                else {
                    trusting.add(uuid.toString());
                }
            });
        }

        List<String> activities = brain.getPossibleActivities().stream().map(Activity::getId).toList();
        List<String> behaviors = brain.getRunningTasks().stream().map(Task::getName).toList();
        List<String> memories = getMemoryDescriptions(bird, bird.getWorld().getTime());
        String schedule = Optional.ofNullable(Registries.SCHEDULE.getId(brain.getSchedule())).map(Identifier::getPath).orElse(null);
        Set<BlockPos> pois = Set.of();
        Set<BlockPos> potentialPois = Set.of();

        BirdDebugRenderer.BirdData data = new BirdDebugRenderer.BirdData(
            bird.getUuid(),
            bird.getId(),
            name,
            bird.getMoveControl().getClass().getSimpleName(),
            bird.getNavigation().getClass().getSimpleName(),
            bird.getHealth(),
            bird.getMaxHealth(),
            bird.getPos(),
            inventory,
            path,
            trusting,
            flying,
            bird.isAmbient(),
            Birds.isPerched(bird),
            activities,
            behaviors,
            memories,
            schedule,
            pois,
            potentialPois
        );
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        data.write(buf);

        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(FowlPlayClient.DEBUG_BIRD_ID, buf);
        sendToAll((ServerWorld) bird.getWorld(), packet);
    }

    @SuppressWarnings("deprecation")
    private static List<String> getMemoryDescriptions(LivingEntity entity, long gameTime) {
        Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> map = entity.getBrain().getMemories();
        List<String> list = Lists.newArrayList();
        for(Map.Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> entry : map.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            Optional<? extends Memory<?>> optional = entry.getValue();
            String value;
            if(optional.isPresent()) {
                Memory<?> expirableValue = optional.get();
                Object object = expirableValue.getValue();
                if(memoryModuleType == MemoryModuleType.HEARD_BELL_TIME) {
                    long l = gameTime - (Long) object;
                    value = l + " ticks ago";
                }
                else if(expirableValue.isTimed()) {
                    String desc = getMemoryValueDescription((ServerWorld) entity.getWorld(), object);
                    value = desc + " (ttl: " + expirableValue.getExpiry() + ")";
                }
                else {
                    value = getMemoryValueDescription((ServerWorld) entity.getWorld(), object);
                }
            }
            else {
                value = "-";
            }
            String memory = Registries.MEMORY_MODULE_TYPE.getId(memoryModuleType).getPath();
            list.add(memory + ": " + value);
        }
        list.sort(String::compareTo);
        return list;
    }

    private static String getMemoryValueDescription(ServerWorld world, @Nullable Object object) {
        if(object == null) {
            return "-";
        }
        if(object instanceof UUID uuid) {
            return getMemoryValueDescription(world, world.getEntity(uuid));
        }
        if(object instanceof LivingEntity entity) {
            return NameGenerator.name(entity);
        }
        if(object instanceof Nameable nameable) {
            return nameable.getName().getString();
        }
        if(object instanceof WalkTarget walkTarget) {
            return getMemoryValueDescription(world, walkTarget.getLookTarget());
        }
        if(object instanceof EntityLookTarget entityLookTarget) {
            return getMemoryValueDescription(world, entityLookTarget.getEntity());
        }
        if(object instanceof GlobalPos globalPos) {
            return getMemoryValueDescription(world, globalPos.getPos());
        }
        if(object instanceof BlockPosLookTarget blockPosLookTarget) {
            return getMemoryValueDescription(world, blockPosLookTarget.getBlockPos());
        }
        if(object instanceof DamageSource damageSource) {
            Entity entity = damageSource.getAttacker();
            return entity == null ? object.toString() : getMemoryValueDescription(world, entity);
        }
        if(object instanceof Collection<?> iterable) {
            List<String> list = Lists.newArrayList();
            iterable.forEach(o -> list.add(getMemoryValueDescription(world, o)));
            return list.toString();
        }
        if(object instanceof LivingTargetCache cache) {
            List<String> list = Lists.newArrayList();
            cache.stream(entity -> true).forEach(o -> list.add(getMemoryValueDescription(world, o)));
            return list.toString();
        }
        return object.toString();
    }

    private static void sendToAll(ServerWorld world, Packet<?> packet) {
        world.getPlayers().forEach(player -> {
            if(FowlPlayClient.DEBUG_BIRD) {
                player.networkHandler.sendPacket(packet);
            }
        });
    }
}