package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Day08Test {

    static String TEST_INPUT = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6""";

    @Test
    public void testProgram1() {
        Day08.Handheld hh = new Day08.Handheld();
        hh.load(TEST_INPUT.lines().collect(Collectors.toList()));
        hh.runToRepeat();
        assertEquals(5, hh.acc);
    }
}