package dev.gresty.aoc2020;

import java.util.HashMap;
import java.util.Map;

import static dev.gresty.aoc2020.Utils.time;

public class Day10 {

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        var sorted = Utils.withLines("day10.txt",
                s -> s.mapToInt(Integer::parseInt)
                        .sorted().toArray());
        var diffs = new int[4];
        for (int i = 1; i < sorted.length; i++) {
            var d = sorted[i] - sorted[i - 1];
            diffs[d] = diffs[d] + 1;
        }
        diffs[sorted[0]] = diffs[sorted[0]] + 1;
        diffs[3] = diffs[3] + 1;
        return diffs[1] * diffs[3];
    }

    public static long puzzle2() {
        var a = Utils.withLines("day10.txt",
                s -> s.mapToInt(Integer::parseInt)
                        .sorted().toArray());
        return countArrangements(a);
    }

    public static long countArrangements(int[] a) {
        var arrangements = 1L;
        var runOfOnes = 0;
        for (int i = 0; i < a.length; i++) {
            var before = i == 0 ? a[0] : a[i] - a[i - 1];
            var after = i == a.length - 1 ? 3 : a[i + 1] - a[i];
            if (before == 1 && after == 1) {
                runOfOnes++;
            } else if (before == 1 && after == 3) {
                arrangements *= permute2(runOfOnes);
                runOfOnes = 0;
            }
        }
        return arrangements;
    }

    public static long permute(int runLength) {
        long max = (long) Math.pow(2, runLength);
        var count = 0;
        for (long i = 0; i < max; i++) {
            if (isValid(runLength, i)) count++;
        }
        return count;
    }

    public static boolean isValid(int length, long value) {
        var runOfZeroes = 0;
        for (int i = 0; i < length; i++) {
            if (value % 2 == 0) runOfZeroes++;
            else runOfZeroes = 0;
            if (runOfZeroes == 3) return false;
            value = value >> 1;
        }
        return true;
    }

    static Map<Integer, Long> memo = new HashMap<>();
    static {
        memo.put(-1, 1L);
        memo.put(0, 1L);
        memo.put(1, 2L);
        memo.put(2, 4L);
    }
    public static long permute2(int runLength) {
        Long result = memo.get(runLength);
        if (result == null) {
            result = 2 * permute2(runLength - 1) - permute2(runLength - 4);
            memo.put(runLength, result);
        }
        return result;
    }
}
