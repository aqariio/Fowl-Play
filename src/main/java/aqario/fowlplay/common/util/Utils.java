package aqario.fowlplay.common.util;

import net.minecraft.util.RandomSource;

public class Utils {
    @SafeVarargs
    public static <T> T getRandomOf(RandomSource random, T... selections) {
        return selections[random.nextInt(selections.length)];
    }
}
