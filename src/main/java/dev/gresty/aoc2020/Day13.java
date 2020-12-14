package dev.gresty.aoc2020;

import lombok.Data;

import java.util.Arrays;
import java.util.Scanner;

import static dev.gresty.aoc2020.Utils.*;

public class Day13 {

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    static int puzzle1() {
        Scanner scanner = scanner("day13.txt");
        PuzzleData data = new PuzzleData(scanner.nextLine(), scanner.nextLine());
        Bus earliest = earliestPickup(data.arrivalTime, data.busLines);
        return earliest.line * earliest.waitTime;
    }

    static long puzzle2() {
        Scanner scanner = scanner("day13.txt");
        scanner.nextLine();
        int[] buses = decodeBuses(scanner.nextLine());
        return solve(buses);
    }

    static int[] decodeBuses(String input) {
        return Arrays.stream(input.split(","))
                .map(s -> s.equals("x") ? "0" : s)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    static long solve(int[] buses) {
        long t = 0;
        long step = 1;
        int lastBus = buses.length - 1;

        for (int b = lastBus; b >= 0; b--) {
            if (buses[b] != 0) {
                msg("Time " + t + " - solving for bus " + b + ": " + buses[b] + " with step " + step + ", looking for gap " + (lastBus - b));
                t = findNextT(buses[b], lastBus - b, t, step);
                step *= buses[b];
            }
        }
        return t - lastBus;
    }

    static long findNextT(int nextBus, int gap, long tFrom, long step) {
        long t = tFrom;
        long offset = 0;
        while (nextBus + offset < gap) offset += nextBus;
        while (t % nextBus + offset != gap) { t += step; }
        return t;
    }

    static Bus earliestPickup(int arrivalTime, int[] busLines) {
        Bus earliest = new Bus(0, Integer.MAX_VALUE);
        for (int busLine : busLines) {
            int waitTime = waitTimeForBus(arrivalTime, busLine);
            if (waitTime < earliest.waitTime) {
                earliest = new Bus(busLine, waitTime);
            }
        }
        return earliest;
    }

    static int waitTimeForBus(int arrivalTime, int busLine) {
        return busLine - arrivalTime % busLine;
    }

    @Data
    static class Bus {
        final int line;
        final int waitTime;
    }

    @Data
    static class PuzzleData {
        final int arrivalTime;
        final int[] busLines;

        PuzzleData (String arrivalTime, String busLineCSV) {
            this.arrivalTime = Integer.parseInt(arrivalTime);
            this.busLines = Arrays.stream(busLineCSV.split(","))
                    .filter(s -> !s.equals("x"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

    }

    @Data
    static class PuzzleData2 {
        final int[] busLines;

        PuzzleData2 (String busLineCSV) {
            this.busLines = Arrays.stream(busLineCSV.split(","))
                    .map(s -> s.equals("x") ? "0" : s)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

    }
}
