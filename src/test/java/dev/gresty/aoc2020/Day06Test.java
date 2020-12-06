package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import static dev.gresty.aoc2020.Day06.*;
import static org.junit.jupiter.api.Assertions.*;

class Day06Test {

    @Test
    public void test1() {
        var grouped = groupAnswers(TEST_INPUT_1.lines(), Day06::accumulateForAnyone);
        assertEquals(1, grouped.size());
        assertEquals(6, grouped.get(0).cardinality());
    }

    @Test
    public void test2() {
        var grouped = groupAnswers(TEST_INPUT_2.lines(), Day06::accumulateForAnyone);
        assertEquals(5, grouped.size());
        assertEquals(3, grouped.get(0).cardinality());
        assertEquals(3, grouped.get(1).cardinality());
        assertEquals(3, grouped.get(2).cardinality());
        assertEquals(1, grouped.get(3).cardinality());
        assertEquals(1, grouped.get(4).cardinality());
    }

    @Test
    public void test3() {
        var grouped = groupAnswers(TEST_INPUT_2.lines(), Day06::accumulateForEveryone);
        assertEquals(5, grouped.size());
        assertEquals(3, grouped.get(0).cardinality());
        assertEquals(0, grouped.get(1).cardinality());
        assertEquals(1, grouped.get(2).cardinality());
        assertEquals(1, grouped.get(3).cardinality());
        assertEquals(1, grouped.get(4).cardinality());
    }

    static final String TEST_INPUT_1 = """
            abcx
            abcy
            abcz""";

    static final String TEST_INPUT_2 = """
            abc
                        
            a
            b
            c
                        
            ab
            ac
                        
            a
            a
            a
            a
                        
            b""";
}