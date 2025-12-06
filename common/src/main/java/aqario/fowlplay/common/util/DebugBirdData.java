package aqario.fowlplay.common.util;

import aqario.fowlplay.common.entity.BirdEntity;
import aqario.fowlplay.common.entity.FlyingBirdEntity;
import aqario.fowlplay.common.entity.TrustingBirdEntity;
import com.google.common.collect.Lists;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public record DebugBirdData(
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
    public static final StreamCodec<FriendlyByteBuf, DebugBirdData> STREAM_CODEC = StreamCodec.of(
        (buf, birdData) -> birdData.write(buf), DebugBirdData::new
    );

    public DebugBirdData(FriendlyByteBuf buf) {
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

    @SuppressWarnings("deprecation")
    public static DebugBirdData takeBirdData(ServerLevel level, BirdEntity bird) {
        Brain<?> brain = bird.getBrain();
        String name = DebugEntityNameGenerator.getEntityName(bird);
        String inventory = "";
        Path path = null;
        boolean flying = bird instanceof FlyingBirdEntity flyingBird && flyingBird.isFlying();
        if(bird instanceof InventoryCarrier inventoryOwner) {
            inventory = inventoryOwner.getInventory().isEmpty() ? "" : inventoryOwner.getInventory().toString();
        }
        if(bird.isMemoryPresent(MemoryModuleType.PATH)) {
            path = bird.getPresentMemory(MemoryModuleType.PATH);
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

        return new DebugBirdData(
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
