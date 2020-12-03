package dev.gresty.aoc2020;

import java.util.List;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;

public class Day03 {

    public static void main(String[] args) {
        time(() -> "The toboggan hits " + puzzle1() + " trees.");
        time(() -> "Product of trees hit on all slopes is " + puzzle2());
    }


    static long puzzle1() {
        var map = withLines("day03.txt", s -> s.toArray(String[]::new));
        return treesHitOnSlope(3, 1, map);
    }

    static long puzzle2() {
        var map = withLines("day03.txt", s -> s.toArray(String[]::new));
        var slopes = List.of(Pair.of(1, 1),
                Pair.of(3, 1),
                Pair.of(5, 1),
                Pair.of(7, 1),
                Pair.of(1, 2));
        return slopes.stream()
                .mapToLong(p -> treesHitOnSlope(p.a, p.b, map))
                .reduce(1L, (a, b) -> a * b);
    }

    static long treesHitOnSlope(int dx, int dy, String[] map) {
        final var repeat = map[0].length();
        int x = 0, y = 0, trees = 0;
        do {
            if (map[y].charAt(x) == '#') trees++;
            y += dy;
            if (y < map.length) {
                x = (x + dx) % repeat;
            }

        } while (y < map.length);
        return trees;
    }

}
