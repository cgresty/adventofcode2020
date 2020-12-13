package dev.gresty.aoc2020;

import lombok.Data;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dev.gresty.aoc2020.Utils.*;

public class Day12 {

    static final IntPair N = Pair.of(0, 1);
    static final IntPair S = Pair.of(0, -1);
    static final IntPair E = Pair.of(1, 0);
    static final IntPair W = Pair.of(-1, 0);

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    static int puzzle1() {
        Ferry f = new Ferry();
        FerryMover m = FerryMover.forPuzzle1(f);
        withLines2("day12.txt", s -> s.map(Instruction::decode).forEach(m::move));
        return f.loc.manhattan();
    }

    static int puzzle2() {
        Ferry f = new Ferry();
        FerryMover m = FerryMover.forPuzzle2(f);
        withLines2("day12.txt", s -> s.map(Instruction::decode).forEach(m::move));
        return f.loc.manhattan();
    }

    static class Ferry {
        IntPair loc = Pair.of(0, 0);
        IntPair dir = E;
        IntPair wp = Pair.of(10, 1);

        void turn(int a) {
            dir = rotate(dir, a);
        }

        void forward(int d) {
            move(dir, d);
        }

        void move(IntPair dir, int dist) {
            loc = loc.add(dir.scale(dist));
        }

        void moveWP(IntPair dir, int dist) {
            wp = wp.add(dir.scale(dist));
        }

        void rotateWP(int a) {
            wp = rotate(wp, a);
        }

        void forwardToWP(int d) {
            move(wp, d);
        }

        private static IntPair rotate(IntPair start, int angle) {
            return switch ((angle % 360 + 360) % 360) { // positive modulo
                case 90 -> Pair.of(start.y, -start.x);
                case 180 -> Pair.of(-start.x, -start.y);
                case 270 -> Pair.of(-start.y, start.x);
                default -> start;
            };
        }
    }

    @Data
    static class Instruction {
        final char op;
        final int arg;

        static Instruction decode(String input) {
            return new Instruction(input.charAt(0), Integer.parseInt(input.substring(1)));
        }
    }

    @Data
    static class FerryMover {
        final Consumer<Integer> forward;
        final Consumer<Integer> turn;
        final BiConsumer<IntPair, Integer> move;

        void move(Instruction i) {
            switch (i.op) {
                case 'F' -> forward.accept(i.arg);
                case 'L' -> turn.accept(-1 * i.arg);
                case 'R' -> turn.accept(i.arg);
                case 'N' -> move.accept(N, i.arg);
                case 'S' -> move.accept(S, i.arg);
                case 'E' -> move.accept(E, i.arg);
                case 'W' -> move.accept(W, i.arg);
            }
        }

        static FerryMover forPuzzle1(Ferry ferry) {
            return new FerryMover(ferry::forward, ferry::turn, ferry::move);
        }

        static FerryMover forPuzzle2(Ferry ferry) {
            return new FerryMover(ferry::forwardToWP, ferry::rotateWP, ferry::moveWP);
        }
    }
}
