package aqario.fowlplay.common.util;

import net.minecraft.util.RandomSource;

import java.util.function.BiPredicate;

public class Utils {
    public static <T, U> BiPredicate<T, U> not(BiPredicate<T, U> biPredicate) {
        return biPredicate.negate();
    }

    @SafeVarargs
    public static <T> T getRandomOf(RandomSource random, T... selections) {
        return selections[random.nextInt(selections.length)];
    }
}
