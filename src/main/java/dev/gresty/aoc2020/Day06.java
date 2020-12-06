package dev.gresty.aoc2020;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;

public class Day06 {

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        var groupedAnswers = withLines("day06.txt",
                stream -> Day06.groupAnswers(stream, Day06::accumulateForAnyone));
        return groupedAnswers.stream().mapToInt(BitSet::cardinality).sum();
    }

    public static int puzzle2() {
        var groupedAnswers = withLines("day06.txt",
                stream -> Day06.groupAnswers(stream, Day06::accumulateForEveryone));
        return groupedAnswers.stream().mapToInt(BitSet::cardinality).sum();
    }

    static List<BitSet> groupAnswers(Stream<String> lines, BiConsumer<List<BitSet>, ? super String> accumulator) {
        return lines.collect(ArrayList::new, accumulator, List::addAll);
    }

    public static void accumulateForAnyone(List<BitSet> list, String line) {
        var last = list.size() - 1;
        if (line.isBlank()) {
            list.add(new BitSet(26));
        } else if (list.isEmpty()) {
            list.add(toBitSet(line));
        } else {
            list.get(last).or(toBitSet(line));
        }
    }

    public static void accumulateForEveryone(List<BitSet> list, String line) {
        var last = list.size() - 1;
        if (line.isBlank()) {
            var b = new BitSet(26);
            b.set(0, 26);
            list.add(b);
        } else if (list.isEmpty()) {
            list.add(toBitSet(line));
        } else {
            list.get(last).and(toBitSet(line));
        }
    }


    static BitSet toBitSet(String line) {
        BitSet b = new BitSet(26);
        line.chars().forEach(c -> b.set(c - 'a'));
        return b;
    }
}
