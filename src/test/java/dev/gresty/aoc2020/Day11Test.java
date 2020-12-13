package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import static dev.gresty.aoc2020.Day11.*;
import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    static final String INPUT = """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL""";

    @Test
    public void testFindSteadyState() {
        char[][] seats = loadSeats(INPUT.lines());
        assertEquals(37, findStaticState(seats, Day11::nextSeatStateByAdjacency));
    }

    @Test
    public void testNextRound1() {
        char[][] seats = loadSeats(INPUT.lines());
        char[][][] seatBuffers = createBuffers(seats);
        nextRound(seatBuffers, Day11::nextSeatStateByAdjacency);
        dump(seatBuffers[0]);
    }

    @Test
    public void testNextRound2() {
        char[][] seats = loadSeats(INPUT.lines());
        char[][][] seatBuffers = createBuffers(seats);
        nextRound(seatBuffers, Day11::nextSeatStateByAdjacency);
        nextRound(seatBuffers, Day11::nextSeatStateByAdjacency);
        dump(seatBuffers[0]);
    }

    @Test
    public void testOccupiedAdjacent() {
        char[][] seats = loadSeats(INPUT.lines());
        char[][][] seatBuffers = createBuffers(seats);
        nextRound(seatBuffers, Day11::nextSeatStateByAdjacency);
        assertEquals(2, occupiedAdjacent(seatBuffers[0], 0, 0));
    }

    static void dump(char[][] seats) {
        for (char[] row : seats) {
            for (char seat : row) {
                System.out.print(seat);
            }
            System.out.println();
        }

    }

}