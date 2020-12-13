package dev.gresty.aoc2020;

import lombok.Data;

import static java.lang.Math.abs;

@Data
public class IntPair {
    final int x;
    final int y;

    public IntPair add(IntPair other) {
        return Pair.of(x + other.x, y + other.y);
    }
    public IntPair scale(int scalar) { return Pair.of(x * scalar, y * scalar); }
    public int manhattan() { return abs(x) + abs(y); }
}
