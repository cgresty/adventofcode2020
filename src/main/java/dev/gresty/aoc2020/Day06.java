package dev.gresty.aoc2020;

import java.util.*;
import java.util.stream.Stream;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;

public class Day06 {

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        var groupedAnswers = withLines("day06.txt", Day06::groupAnswersByAnyone);
        return groupedAnswers.stream().mapToInt(BitSet::cardinality).sum();
    }

    public static int puzzle2() {
        var groupedAnswers = withLines("day06.txt", Day06::groupAnswersByEveryone);
        return groupedAnswers.stream().mapToInt(BitSet::cardinality).sum();
    }

    static List<BitSet> groupAnswersByAnyone(Stream<String> lines) {
        return lines.collect(ArrayList::new,
                (l, s) -> {
                    var last = l.size() - 1;
                    if (s.isBlank()) {
                        l.add(new BitSet(26));
                    } else if (l.isEmpty()) {
                        l.add(toBitSet(s));
                    } else {
                        l.get(last).or(toBitSet(s));
                    }
                },
                List::addAll);
    }

    static List<BitSet> groupAnswersByEveryone(Stream<String> lines) {
        return lines.collect(ArrayList::new,
                (l, s) -> {
                    var last = l.size() - 1;
                    if (s.isBlank()) {
                        var b = new BitSet(26);
                        b.set(0, 26);
                        l.add(b);
                    } else if (l.isEmpty()) {
                        l.add(toBitSet(s));
                    } else {
                        l.get(last).and(toBitSet(s));
                    }
                },
                List::addAll);
    }

    static BitSet toBitSet(String line) {
        BitSet b = new BitSet(26);
        line.chars().forEach(c -> b.set(c - 'a'));
        return b;
    }
}
