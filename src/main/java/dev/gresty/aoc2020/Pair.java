package dev.gresty.aoc2020;

import lombok.Data;

@Data
public class Pair<T> {
    final T x;
    final T y;

    static <T> Pair<T> of(T x, T y) {
        return new Pair<>(x, y);
    }

    static IntPair of(int x, int y) {
        return new IntPair(x, y);
    }

    static LongPair of(long x, long y) {
        return new LongPair(x, y);
    }

    static <T> Pair<T> of(T[] x) {
        return Pair.of(x[0], x[1]);
    }

}
