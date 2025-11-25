package aqario.fowlplay.common.network;

import aqario.fowlplay.client.FowlPlayClient;
import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import aqario.fowlplay.common.network.s2c.BirdDebugPayload;
import aqario.fowlplay.common.util.Birds;
import aqario.fowlplay.core.FowlPlay;
import com.google.common.collect.Lists;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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

        BirdDebugPayload.BirdData data = new BirdDebugPayload.BirdData(
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
            Birds.isPerched(bird),
            activities,
            behaviors,
            memories,
            schedule,
            pois,
            potentialPois
        );
        BirdDebugPayload payload = new BirdDebugPayload(data);
        sendToAll((ServerLevel) bird.level(), payload);
    }

    @SuppressWarnings("deprecation")
    private static List<String> getMemoryDescriptions(LivingEntity entity, long gameTime) {
        Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> map = entity.getBrain().getMemories();
        List<String> list = Lists.newArrayList();
        for(Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> entry : map.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            Optional<? extends ExpirableValue<?>> optional = entry.getValue();
            String value;
            if(optional.isPresent()) {
                ExpirableValue<?> expirableValue = optional.get();
                Object object = expirableValue.getValue();
                if(memoryModuleType == MemoryModuleType.HEARD_BELL_TIME) {
                    long l = gameTime - (Long) object;
                    value = l + " ticks ago";
                }
                else if(expirableValue.canExpire()) {
                    String desc = getMemoryValueDescription((ServerLevel) entity.level(), object);
                    value = desc + " (ttl: " + expirableValue.getTimeToLive() + ")";
                }
                else {
                    value = getMemoryValueDescription((ServerLevel) entity.level(), object);
                }
            }
            else {
                value = "-";
            }
            String memory = BuiltInRegistries.MEMORY_MODULE_TYPE.getKey(memoryModuleType).getPath();
            list.add(memory + ": " + value);
        }
        list.sort(String::compareTo);
        return list;
    }

    private static String getMemoryValueDescription(ServerLevel world, @Nullable Object object) {
        switch(object) {
            case null -> {
                return "-";
            }
            case UUID uuid -> {
                return getMemoryValueDescription(world, world.getEntity(uuid));
            }
            case LivingEntity entity -> {
                return DebugEntityNameGenerator.getEntityName(entity);
            }
            case Nameable nameable -> {
                return nameable.getName().getString();
            }
            case WalkTarget walkTarget -> {
                return getMemoryValueDescription(world, walkTarget.getTarget());
            }
            case EntityTracker entityLookTarget -> {
                return getMemoryValueDescription(world, entityLookTarget.getEntity());
            }
            case GlobalPos globalPos -> {
                return getMemoryValueDescription(world, globalPos.pos());
            }
            case BlockPosTracker blockPosLookTarget -> {
                return getMemoryValueDescription(world, blockPosLookTarget.currentBlockPosition());
            }
            case DamageSource damageSource -> {
                Entity entity = damageSource.getEntity();
                return entity == null ? object.toString() : getMemoryValueDescription(world, entity);
            }
            case Collection<?> iterable -> {
                List<String> list = Lists.newArrayList();
                iterable.forEach(o -> list.add(getMemoryValueDescription(world, o)));
                return list.toString();
            }
            case NearestVisibleLivingEntities cache -> {
                List<String> list = Lists.newArrayList();
                cache.nearbyEntities.forEach(o -> list.add(getMemoryValueDescription(world, o)));
                return list.toString();
            }
            default -> {
                return object.toString();
            }
        }
    }

    private static void sendToAll(ServerLevel world, CustomPacketPayload payload) {
        NetworkManager.sendToPlayers(world.players(), payload);
    }
}
