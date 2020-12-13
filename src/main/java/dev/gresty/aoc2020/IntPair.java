package dev.gresty.aoc2020;

import lombok.Data;

@Data
public class IntPair {
    final int a;
    final int b;

    public IntPair add(IntPair other) {
        return Pair.of(a + other.a, b + other.b);
    }
}
