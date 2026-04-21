package aqario.fowlplay.core;

import aqario.fowlplay.common.entity.ai.brain.RememberedPositions;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import aqario.fowlplay.common.registry.CommonRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class FPMemoryTypes {
    public static final CommonRegister<MemoryModuleType<?>> REGISTRAR = CommonRegister.create(
        BuiltInRegistries.MEMORY_MODULE_TYPE,
        FowlPlay.ID
    );

    public static final Supplier<MemoryModuleType<List<? extends AgeableMob>>> NEAREST_VISIBLE_ADULTS = register("nearest_visible_adults");
    public static final Supplier<MemoryModuleType<Unit>> SEES_FOOD = register("sees_food");
    public static final Supplier<MemoryModuleType<Unit>> CANNOT_PICKUP_FOOD = register("cannot_pickup_food");
    public static final Supplier<MemoryModuleType<Unit>> IS_AVOIDING = register("is_avoiding");
    public static final Supplier<MemoryModuleType<TeleportTarget>> TELEPORT_TARGET = register("teleport_target");
    public static final Supplier<MemoryModuleType<Unit>> IS_FOLLOWING = register("is_following");
    public static final Supplier<MemoryModuleType<UUID>> RECIPIENT = register("recipient");
    public static final Supplier<MemoryModuleType<RememberedPositions>> REMEMBERED_POSITIONS = register("remembered_positions");
    public static final Supplier<MemoryModuleType<LivingEntity>> NEAREST_HUNTABLE = register("nearest_huntable");

    private static <U> Supplier<MemoryModuleType<U>> register(String id) {
        return REGISTRAR.register(id, () -> new MemoryModuleType<>(Optional.empty()));
    }
}
