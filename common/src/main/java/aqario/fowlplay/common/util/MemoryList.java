package aqario.fowlplay.common.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;

public class MemoryList extends ObjectArrayList<Pair<MemoryModuleType<?>, MemoryModuleState>> {
    private MemoryList(int size) {
        super(size);
    }

    public static MemoryList create(int size) {
        return new MemoryList(size);
    }

    public MemoryList present(MemoryModuleType<?> memory) {
        return this.add(memory, MemoryModuleState.VALUE_PRESENT);
    }

    public MemoryList present(MemoryModuleType<?>... memories) {
        for(MemoryModuleType<?> memory : memories) {
            this.present(memory);
        }

        return this;
    }

    public MemoryList absent(MemoryModuleType<?> memory) {
        return this.add(memory, MemoryModuleState.VALUE_ABSENT);
    }

    public MemoryList absent(MemoryModuleType<?>... memories) {
        for(MemoryModuleType<?> memory : memories) {
            this.absent(memory);
        }

        return this;
    }

    public MemoryList registered(MemoryModuleType<?> memory) {
        return this.add(memory, MemoryModuleState.REGISTERED);
    }

    public MemoryList registered(MemoryModuleType<?>... memories) {
        for(MemoryModuleType<?> memory : memories) {
            this.registered(memory);
        }

        return this;
    }

    public MemoryList add(MemoryModuleType<?> memory, MemoryModuleState state) {
        super.add(Pair.of(memory, state));

        return this;
    }
}
