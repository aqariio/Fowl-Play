package aqario.fowlplay.common.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class MemoryList extends ObjectArrayList<Pair<MemoryModuleType<?>, MemoryStatus>> {
    private MemoryList(int size) {
        super(size);
    }

    public static MemoryList create(int size) {
        return new MemoryList(size);
    }

    public MemoryList present(MemoryModuleType<?> memory) {
        return this.add(memory, MemoryStatus.VALUE_PRESENT);
    }

    public MemoryList present(MemoryModuleType<?>... memories) {
        for(MemoryModuleType<?> memory : memories) {
            this.present(memory);
        }

        return this;
    }

    public MemoryList absent(MemoryModuleType<?> memory) {
        return this.add(memory, MemoryStatus.VALUE_ABSENT);
    }

    public MemoryList absent(MemoryModuleType<?>... memories) {
        for(MemoryModuleType<?> memory : memories) {
            this.absent(memory);
        }

        return this;
    }

    public MemoryList registered(MemoryModuleType<?> memory) {
        return this.add(memory, MemoryStatus.REGISTERED);
    }

    public MemoryList registered(MemoryModuleType<?>... memories) {
        for(MemoryModuleType<?> memory : memories) {
            this.registered(memory);
        }

        return this;
    }

    public MemoryList add(MemoryModuleType<?> memory, MemoryStatus state) {
        super.add(Pair.of(memory, state));

        return this;
    }
}
