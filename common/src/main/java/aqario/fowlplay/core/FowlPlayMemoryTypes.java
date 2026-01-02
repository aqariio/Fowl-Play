package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.RememberedPositions;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.core.platform.PlatformHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class FowlPlayMemoryTypes {
    public static final Supplier<MemoryModuleType<List<? extends AgeableMob>>> NEAREST_VISIBLE_ADULTS = register("nearest_visible_adults");
    public static final Supplier<MemoryModuleType<Unit>> SEES_FOOD = register("sees_food", Unit.CODEC);
    public static final Supplier<MemoryModuleType<Boolean>> CANNOT_PICKUP_FOOD = register("cannot_pickup_food", Codec.BOOL);
    public static final Supplier<MemoryModuleType<Unit>> IS_AVOIDING = register("is_avoiding", Unit.CODEC);
    public static final Supplier<MemoryModuleType<TeleportTarget>> TELEPORT_TARGET = register("teleport_target");
    public static final Supplier<MemoryModuleType<UUID>> RECIPIENT = register("recipient", UUIDUtil.CODEC);
    public static final Supplier<MemoryModuleType<RememberedPositions>> REMEMBERED_POSITIONS = register("remembered_positions", RememberedPositions.CODEC);

    private static <U> Supplier<MemoryModuleType<U>> register(String id, Codec<U> codec) {
        return PlatformHelper.registerMemoryModuleType(id, () -> new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> Supplier<MemoryModuleType<U>> register(String id) {
        return PlatformHelper.registerMemoryModuleType(id, () -> new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {
    }
}
