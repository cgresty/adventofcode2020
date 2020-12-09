package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Day09Test {

    @Test
    public void testFindFirstInvalid() {
        var first = Day09.findFirstInvalid(INPUT.lines().mapToLong(Long::parseLong).toArray(), 5);
        assertEquals(127, first);
    }

    @Test
    public void testPuzzle2() {
        var result = Day09.puzzle2(INPUT.lines().mapToLong(Long::parseLong).toArray(), 127);
        assertEquals(62, result);
    }


    static final String INPUT = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576""";

}