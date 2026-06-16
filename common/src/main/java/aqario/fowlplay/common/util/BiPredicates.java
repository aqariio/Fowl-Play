package aqario.fowlplay.common.util;

import java.util.function.BiPredicate;

public class BiPredicates {
    public static <T, U> BiPredicate<T, U> not(BiPredicate<T, U> biPredicate) {
        return biPredicate.negate();
    }
}
