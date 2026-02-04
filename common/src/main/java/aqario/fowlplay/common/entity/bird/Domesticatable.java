package aqario.fowlplay.common.entity.bird;

import net.minecraft.nbt.CompoundTag;

public interface Domesticatable {
    String CLIPPED_KEY = "clipped";
    String DOMESTIC_KEY = "domestic";

    boolean isDomestic();

    void setDomestic(boolean domestic);

    boolean hasClippedWings();

    void setClippedWings(boolean clipped);

    default void writeClipped(CompoundTag nbt) {
        nbt.putBoolean(CLIPPED_KEY, this.hasClippedWings());
    }

    default void readClipped(CompoundTag nbt) {
        this.setClippedWings(nbt.getBoolean(CLIPPED_KEY));
    }

    default void writeDomestic(CompoundTag nbt) {
        nbt.putBoolean(DOMESTIC_KEY, this.isDomestic());
    }

    default void readDomestic(CompoundTag nbt) {
        this.setDomestic(nbt.getBoolean(DOMESTIC_KEY));
    }
}
