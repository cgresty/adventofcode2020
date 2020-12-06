package dev.gresty.aoc2020;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dev.gresty.aoc2020.Utils.*;

public class Day05 {

    public static void main(String[] args) {
        test();
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static void test() {
        msg("test: FBFBBFFRLR -> " + convertToSeatId("FBFBBFFRLR"));
    }

    public static int puzzle1() {
        return withLines("day05.txt",
                line -> line.mapToInt(Day05::convertToSeatId)
                        .max().orElse(0));
    }

    public static int puzzle2() {
        var seats = IntStream.range(0, 1024)
                .collect(HashSet<Integer>::new, Set::add, Set::addAll);
        var occupied = withLines("day05.txt",
                line -> line.map(Day05::convertToSeatId)
                        .collect(Collectors.toSet()));
        occupied.forEach(seats::remove);
        int i = 0;
        while (seats.remove(i)) i++;
        i = 1023;
        while (seats.remove(i)) i--;
        return seats.iterator().next();
    }

    public static int convertToSeatId(String partition) {
        return Integer.parseInt(partition
                .replace('F', '0')
                .replace('B', '1')
                .replace('L', '0')
                .replace('R', '1'), 2);
    }
}
