package dev.gresty.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;
import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Day11 {
    static final char FLOOR = '.';
    static final char EMPTY = 'L';
    static final char OCCUPIED = '#';

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        char[][] seats = withLines("day11.txt", Day11::loadSeats);
        return findStaticState(seats, Day11::nextSeatStateByAdjacency);
    }

    public static int puzzle2() {
        char[][] seats = withLines("day11.txt", Day11::loadSeats);
        return findStaticState(seats, Day11::nextSeatStateByVisibility);
    }

    static char[][] loadSeats(Stream<String> input) {
        return input.map(String::toCharArray).toArray(char[][]::new);
    }

    static int findStaticState(char[][] seats, NextSeatState nextSeatState) {
        char[][][] seatBuffers = createBuffers(seats);
        while (nextRound(seatBuffers, nextSeatState) > 0);
        return countOccupied(seatBuffers[0]);
    }

    static char[][][] createBuffers(char[][] source) {
        char[][][] buffers = new char[2][][];
        buffers[0] = source;
        buffers[1] = new char[source.length][];
        for(int r = 0; r < source.length; r++) {
            buffers[1][r] = new char[source[r].length];
        }
        return buffers;
    }

    static int nextRound(char[][][] seats, NextSeatState nextSeatState) {
        var changes = 0;
        var now = seats[0];
        var next = seats[1];
        for (int row = 0; row < now.length; row++) {
            for (int col = 0; col < now[row].length; col++) {
                if (now[row][col] == FLOOR) {
                    next[row][col] = FLOOR;
                } else {
                    next[row][col] = nextSeatState.calculate(now, row, col);
                    if (now[row][col] != next[row][col]) changes++;
                }
            }
        }
        seats[0] = next;
        seats[1] = now;
        return changes;
    }

    static char nextSeatStateByAdjacency(char[][] seats, int row, int col) {
        return switch (occupiedAdjacent(seats, row, col)) {
            case 0 -> OCCUPIED;
            case 4, 5, 6, 7, 8 -> EMPTY;
            default -> seats[row][col];
        };
    }

    static char nextSeatStateByVisibility(char[][] seats, int row, int col) {
        return switch (occupiedVisible(seats, row, col)) {
            case 0 -> OCCUPIED;
            case 5, 6, 7, 8 -> EMPTY;
            default -> seats[row][col];
        };
    }

    static int occupiedVisible(char[][] seats, int row, int col) {
        return (int) allVisibleFromSeat(seats, Pair.of(row, col)).stream()
                .mapToLong(loc -> seats[loc.x][loc.y])
                .filter(s -> s == OCCUPIED)
                .count();
    }

    static Map<IntPair, List<IntPair>> seatVisibility = new HashMap<>();
    static List<IntPair> allVisibleFromSeat(char[][] seats, IntPair from) {
        return seatVisibility.computeIfAbsent(from, loc -> {
            var visible = new ArrayList<IntPair>();
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(1, 0)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(1, 1)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(0, 1)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(-1, 1)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(-1, 0)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(-1, -1)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(0, -1)));
            addIfNotNull(visible, nextVisibleInDirection(seats, loc, Pair.of(1, -1)));
            return visible;
        });
    }

    static <T> void addIfNotNull(List<T> list, T item) {
        if (item != null) list.add(item);
    }

    static IntPair nextVisibleInDirection(char[][] seats, IntPair from, IntPair d) {
        IntPair next = from.add(d);
        if (validLocation(seats, next)) {
            if (seats[next.x][next.y] == FLOOR) {
                return nextVisibleInDirection(seats, next, d);
            } else {
                return next;
            }
        } else {
            return null;
        }
    }

    static boolean validLocation(char[][] seat, IntPair loc) {
        return (loc.x >= 0 && loc.y >= 0 && loc.x < seat.length && loc.y < seat[loc.x].length);
    }

    static int countOccupied(char[][] seats) {
        var count = 0;
        for (char[] row : seats) {
            for (char seat : row) {
                if (seat == OCCUPIED) count++;
            }
        }
        return count;
    }


    static int occupiedAdjacent(char[][] seats, int row, int col) {
        int a = 0;
        int rmin = max(row - 1, 0);
        int rmax = min(row + 1, seats.length - 1);
        int cmin = max(col - 1, 0);
        int cmax = min(col + 1, seats[0].length - 1);

        for (int r = rmin; r <= rmax; r++) {
            for (int c = cmin; c <= cmax; c++) {
                if (!(r == row && c == col) && seats[r][c] == OCCUPIED) a++;
            }
        }
        return a;
    }

    @FunctionalInterface
    public interface NextSeatState {
        char calculate(char[][] seats, int row, int col);
    }
}
