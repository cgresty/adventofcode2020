package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import static dev.gresty.aoc2020.Day13.*;
import static org.junit.jupiter.api.Assertions.*;

class Day13Test {

    @Test
    public void test1() {
        Day13.PuzzleData data = new Day13.PuzzleData(ARRIVAL, BUSES);
        assertEquals(new Day13.Bus(59, 5), earliestPickup(data.arrivalTime, data.busLines));
    }

    @Test
    public void test2() {
        assertEquals(3417L, solve(decodeBuses("17,x,13,19")));
        assertEquals(754018L, solve(decodeBuses("67,7,59,61")));
        assertEquals(779210L, solve(decodeBuses("67,x,7,59,61")));
        assertEquals(1261476L, solve(decodeBuses("67,7,x,59,61")));
        assertEquals(1202161486L, solve(decodeBuses("1789,37,47,1889")));
    }

    static final String ARRIVAL = "939";
    static final String BUSES = "7,13,x,x,59,x,31,19";
}