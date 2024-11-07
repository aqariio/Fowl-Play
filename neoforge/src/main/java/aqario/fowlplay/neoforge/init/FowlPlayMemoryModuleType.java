package aqario.fowlplay.neoforge.init;

import aqario.fowlplay.common.FowlPlay;
import aqario.fowlplay.common.entity.ai.brain.TeleportTarget;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.List;
import java.util.Optional;

public final class FowlPlayMemoryModuleType {
    public static final MemoryModuleType<List<PassiveEntity>> NEAREST_VISIBLE_ADULTS = register("nearest_visible_adults");
    public static final MemoryModuleType<Boolean> SEES_FOOD = register("sees_food", Codec.BOOL);
    public static final MemoryModuleType<Boolean> CANNOT_PICKUP_FOOD = register("cannot_eat_food", Codec.BOOL);
    public static final MemoryModuleType<Unit> IS_FLYING = register("is_flying", Unit.CODEC);
    public static final MemoryModuleType<TeleportTarget> TELEPORT_TARGET = register("teleport_target");

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(FowlPlay.ID, id), new MemoryModuleType<>(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(FowlPlay.ID, id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {
    }
}
