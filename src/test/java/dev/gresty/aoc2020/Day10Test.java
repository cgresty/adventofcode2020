package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static dev.gresty.aoc2020.Day10.*;
import static dev.gresty.aoc2020.Utils.time;
import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    public void testIsValid() {
        assertTrue(isValid(3, 0b111));
        assertTrue(isValid(4, 0b1001));
        assertFalse(isValid(4, 0b1000));
        assertFalse(isValid(4, 1));
        assertTrue(isValid(10, 0b1001001001));
    }

    @Test
    public void testIsValid4Exhaustive() {
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 1 || i == 8) {
                assertFalse(isValid(4, i));
            } else {
                assertTrue(isValid(4, i), "Iteration " + i + " failed");
            }
        }
    }

    @Test
    public void testPermute() {
        assertEquals(1, permute(0));
        assertEquals(2, permute(1));
        assertEquals(4, permute(2));
        assertEquals(7, permute(3));
        assertEquals(13, permute(4));
    }

    @Test
    public void testPermute2() {
        assertEquals(permute(1), permute2(1));
        assertEquals(permute(2), permute2(2));
        assertEquals(permute(3), permute2(3));
        assertEquals(permute(4), permute2(4));
        assertEquals(permute(5), permute2(5));
        assertEquals(permute(6), permute2(6));
        assertEquals(permute(7), permute2(7));
        assertEquals(permute(10), permute2(10));
        assertEquals(permute(20), permute2(20));
    }

    @Test
    public void enumerate() {
        for (int i = 1; i < 10; i++) {
            int x = i;
            time(() -> "" + x + " - " + permute(x));
        }
    }

    @Test
    public void testCountArrangements() {
        var a = INPUT.lines().mapToInt(Integer::parseInt)
                .sorted().toArray();
        assertEquals(19208L, countArrangements(a));
    }

    @Test
    public void testCountArrangements72() {
        var a = IntStream.range(1, 72).toArray();
        assertEquals(3814116544533214284L, countArrangements(a));
    }

    static final String INPUT = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3""";

}