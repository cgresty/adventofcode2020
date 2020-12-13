package dev.gresty.aoc2020;

import lombok.Data;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;

public class Day09 {


    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + findFirstInvalid(input(), 25));
        time(() -> "Puzzle 2: " + puzzle2(input(), findFirstInvalid(input(), 25)));
    }

    static long[] input() {
        return withLines("day09.txt", s -> s.mapToLong(Long::parseLong).toArray());
    }

    public static long findFirstInvalid(long[] input, int preamble) {
        Slice slice = new Slice(input, 0, preamble);

        for (int i = preamble; i < input.length; i++) {
            if (!isValid(input[i], slice, preamble)) {
                return input[i];
            }
            slice = slice.slice(1, preamble);
        }
        return -1;
    }

    static boolean isValid(long x, Slice s, int preamble) {
        for (int i = 0; i < preamble - 1; i++) {
            for (int j = i + 1; j < preamble; j++) {
                if (s.get(i) + s.get(j) == x) {
                    return true;
                }
            }
        }
        return false;
    }

    public static long puzzle2(long[] input, long target) {
        var slice = new Slice(input, 0, 1);
        while (slice.sum() != target) {
            if (slice.sum() < target) {
                slice = slice.slice(0, slice.length + 1);
            } else {
                slice = slice.slice(1, slice.length - 1);
            }
        }
        LongPair minMax = slice.minMax();
        return minMax.x + minMax.y;
    }


    @Data
    static class Slice {
        final long[] source;
        final int start;
        final int length;
        final long sum;

        Slice(long[] source, int start, int length) {
            this.source = source;
            this.start = start;
            this.length = length;
            long sum = 0;
            for (int i = start; i < start + length; i++) {
                sum += source[i];
            }
            this.sum = sum;
        }

        private Slice(long[] source, int start, int length, long sum) {
            this.source = source;
            this.start = start;
            this.length = length;
            this.sum = sum;
        }

        Slice slice(int start, int length) {
            long diff = 0;
            for (int i = 0; i < start; i++) {
                diff -= get(i);
            }
            for (int i = this.length; i < length; i++) {
                diff += get(i);
            }
            return new Slice(source, this.start + start, length, sum + diff);
        }

        long get(int index) {
            return source[start + index];
        }

        long sum() {
            return sum;
        }

        LongPair minMax() {
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            for (int i = 0; i < length; i++) {
                min = Math.min(min, get(i));
                max = Math.max(max, get(i));
            }
            return Pair.of(min, max);
        }
    }
}
