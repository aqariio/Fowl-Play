package aqario.fowlplay.common.network;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.client.render.debug.BirdDebugRenderer;
import aqario.fowlplay.client.render.debug.GenericDebugRenderer;
import aqario.fowlplay.common.entity.bird.BirdEntity;
import aqario.fowlplay.common.entity.bird.FlyingBirdEntity;
import aqario.fowlplay.common.entity.bird.TrustingBirdEntity;
import aqario.fowlplay.common.util.BirdUtils;
import aqario.fowlplay.core.FowlPlay;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.DebugEntityNameGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.pathfinder.Path;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FowlPlayDebugPackets {
    @SafeVarargs
    public static <T> void sendGenericData(LivingEntity entity, Pair<String, T>... data) {
        if(!FowlPlay.isDebugUtilsLoaded()
            || entity.level().isClientSide()
            || !FowlPlayClient.DEBUG_GENERIC
        ) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        for(Pair<String, T> pair : data) {
            map.put(pair.getFirst(), pair.getSecond().toString());
        }

        GenericDebugRenderer.Data payloadData = new GenericDebugRenderer.Data(
            entity.getUUID(),
            entity.getId(),
            entity.position(),
            map
        );
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        payloadData.write(buf);

//        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(FowlPlayClient.DEBUG_GENERIC_ID, buf);
        sendToAll((ServerLevel) entity.level(), FowlPlayClient.DEBUG_GENERIC_ID, buf);
    }

    @SuppressWarnings("deprecation")
    public static void sendBirdData(BirdEntity bird) {
        if(!FowlPlay.isDebugUtilsLoaded()
            || bird.level().isClientSide()
            || !FowlPlayClient.DEBUG_BIRD
        ) {
            return;
        }

        Brain<?> brain = bird.getBrain();
        String name = DebugEntityNameGenerator.getEntityName(bird);
        String inventory = "";
        Path path = null;
        boolean flying = bird instanceof FlyingBirdEntity flyingBird && flyingBird.isFlying();
        if(bird instanceof InventoryCarrier inventoryOwner) {
            inventory = inventoryOwner.getInventory().isEmpty() ? "" : inventoryOwner.getInventory().toString();
        }
        if(BrainUtils.hasMemory(brain, MemoryModuleType.PATH)) {
            path = BrainUtils.getMemory(brain, MemoryModuleType.PATH);
        }
        List<String> trusting = new ArrayList<>();
        if(bird instanceof TrustingBirdEntity trustingBird) {
            trustingBird.getTrustedUuids().forEach(uuid -> {
                Player player = bird.level().getPlayerByUUID(uuid);
                if(player != null) {
                    trusting.add(player.getName().getString());
                }
                else {
                    trusting.add(uuid.toString());
                }
            });
        }

        List<String> activities = brain.getActiveActivities().stream().map(Activity::getName).toList();
        List<String> behaviors = brain.getRunningBehaviors().stream().map(BehaviorControl::debugString).toList();
        List<String> memories = getMemoryDescriptions(bird, bird.level().getGameTime());
        String schedule = Optional.ofNullable(BuiltInRegistries.SCHEDULE.getKey(brain.getSchedule())).map(ResourceLocation::getPath).orElse(null);
        Set<BlockPos> pois = Set.of();
        Set<BlockPos> potentialPois = Set.of();

        BirdDebugRenderer.BirdData data = new BirdDebugRenderer.BirdData(
            bird.getUUID(),
            bird.getId(),
            name,
            bird.getMoveControl().getClass().getSimpleName(),
            bird.getNavigation().getClass().getSimpleName(),
            bird.getHealth(),
            bird.getMaxHealth(),
            bird.position(),
            inventory,
            path,
            trusting,
            flying,
            bird.isAmbient(),
            BirdUtils.isPerched(bird),
            activities,
            behaviors,
            memories,
            schedule,
            pois,
            potentialPois
        );
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        data.write(buf);

//        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(FowlPlayClient.DEBUG_BIRD_ID, buf);
        sendToAll((ServerLevel) bird.level(), FowlPlayClient.DEBUG_BIRD_ID, buf);
    }

    @SuppressWarnings("deprecation")
    private static List<String> getMemoryDescriptions(LivingEntity entity, long gameTime) {
        Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> map = entity.getBrain().getMemories();
        List<String> list = Lists.newArrayList();

        for(Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> entry : map.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            Optional<? extends ExpirableValue<?>> optional = entry.getValue();
            String string;
            if(optional.isPresent()) {
                ExpirableValue<?> expirableValue = optional.get();
                Object object = expirableValue.getValue();
                if(memoryModuleType == MemoryModuleType.HEARD_BELL_TIME) {
                    long l = gameTime - (Long) object;
                    string = l + " ticks ago";
                }
                else if(expirableValue.canExpire()) {
                    string = getShortDescription((ServerLevel) entity.level(), object) + " (ttl: " + expirableValue.getTimeToLive() + ")";
                }
                else {
                    string = getShortDescription((ServerLevel) entity.level(), object);
                }
            }
            else {
                string = "-";
            }

            list.add(BuiltInRegistries.MEMORY_MODULE_TYPE.getKey(memoryModuleType).getPath() + ": " + string);
        }

        list.sort(String::compareTo);
        return list;
    }

    private static String getShortDescription(ServerLevel level, @Nullable Object object) {
        if(object == null) {
            return "-";
        }
        if(object instanceof UUID uuid) {
            return getShortDescription(level, level.getEntity(uuid));
        }
        if(object instanceof LivingEntity entity) {
            return DebugEntityNameGenerator.getEntityName(entity);
        }
        if(object instanceof Nameable nameable) {
            return nameable.getName().getString();
        }
        if(object instanceof WalkTarget walkTarget) {
            return getShortDescription(level, walkTarget.getTarget());
        }
        if(object instanceof EntityTracker entityLookTarget) {
            return getShortDescription(level, entityLookTarget.getEntity());
        }
        if(object instanceof GlobalPos pos) {
            return getShortDescription(level, pos.pos());
        }
        if(object instanceof BlockPosTracker blockPosLookTarget) {
            return getShortDescription(level, blockPosLookTarget.currentBlockPosition());
        }
        if(object instanceof DamageSource damageSource) {
            Entity entity = damageSource.getEntity();
            return entity == null ? object.toString() : getShortDescription(level, entity);
        }
        if(object instanceof NearestVisibleLivingEntities cache) {
            List<String> list = Lists.newArrayList();

            for(Object o : cache.nearbyEntities) {
                list.add(getShortDescription(level, o));
            }

            return list.toString();
        }
        if(!(object instanceof Collection<?> iterable)) {
            return object.toString();
        }
        List<String> list = Lists.newArrayList();

        for(Object object2 : iterable) {
            list.add(getShortDescription(level, object2));
        }

        return list.toString();
    }

    private static void sendToAll(ServerLevel world, ResourceLocation id, FriendlyByteBuf packet) {
        NetworkManager.sendToPlayers(world.players(), id, packet);
//        world.players().forEach(player -> {
//            if(FowlPlayClient.DEBUG_BIRD) {
//                player.connection.send(packet);
//            }
//        });
    }
}