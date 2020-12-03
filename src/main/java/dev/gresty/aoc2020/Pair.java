package dev.gresty.aoc2020;

import lombok.Data;

@Data
public class Pair<T> {
    final T a;
    final T b;

    static final <T> Pair<T> of(T a, T b) {
        return new Pair(a, b);
    }

    static final IntPair of(int a, int b) {
        return new IntPair(a, b);
    }

}
