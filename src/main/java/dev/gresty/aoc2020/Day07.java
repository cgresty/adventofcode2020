package dev.gresty.aoc2020;

import lombok.Data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static dev.gresty.aoc2020.Utils.*;

public class Day07 {


    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        var bags = new Bags();
        withLines2("day07.txt", lines -> lines.forEach(bags::add));
        AtomicInteger count = new AtomicInteger();
        next(bags, "shiny gold", new HashSet<>(), c -> count.incrementAndGet());
        return count.get();
    }

    public static long puzzle2() {
        var bags = new Bags();
        withLines2("day07.txt", lines -> lines.forEach(bags::add));
        return next2(bags, "shiny gold", new HashMap<>(), 1);
    }

    public static void next(Bags bags, String colour, Set<String> alreadyFound, Consumer<String> onFound) {
        if (bags.contains(colour) == null) return;
        for (String nextColour : bags.contains(colour)) {
            if (alreadyFound.contains(nextColour)) continue;
            onFound.accept(nextColour);
            alreadyFound.add(nextColour);
            next(bags, nextColour, alreadyFound, onFound);
        }
    }

    public static long next2(Bags bags, String colour, Map<String, Long> alreadyFound, long multiplier) {
        long bagContainedCount = 0;
        if (bags.containedBy(colour) != null) {
            for (CountedBag nextBag : bags.containedBy(colour)) {
                bagContainedCount += nextBag.count;
                Long alreadyFoundCount = alreadyFound.get(nextBag.colour);
                if (alreadyFoundCount != null) {
                    bagContainedCount += nextBag.count * alreadyFoundCount;
                } else {
                    long bagsInNextBag = next2(bags, nextBag.colour, alreadyFound, multiplier * nextBag.count);
                    bagContainedCount += nextBag.count * bagsInNextBag;
                }
            }
        }
        alreadyFound.put(colour, bagContainedCount);
        return bagContainedCount;
    }

    public static class Bags {
        public final Map<String, List<CountedBag>> containedBy = new HashMap<>();
        public final Map<String, List<String>> contains = new HashMap<>();
        public final Set<String> allColours = new HashSet<>();

        void add(String input) {
            var tokens = input.split(" ");
            var container = tokens[0] + " " + tokens[1];
            allColours.add(container);
            if (tokens.length % 4 == 0) {
                for (int i = 4; i < tokens.length; i+= 4) {
                    var n = Integer.parseInt(tokens[i]);
                    var colour = tokens[i + 1] + " " + tokens[i + 2];
                    var countedBag = new CountedBag(n, colour);
                    allColours.add(colour);
                    containedBy.compute(container, Day07::listValue).add(countedBag);
                    contains.compute(colour, Day07::listValue).add(container);
                }
            } else {
                containedBy.compute(container, Day07::listValue);
            }
        }

        List<String> contains(String bagOfColour) {
            return contains.get(bagOfColour);
        }

        List<CountedBag> containedBy(String bagOfColour) {
            return containedBy.get(bagOfColour);
        }
    }

    @Data
    public static class CountedBag {
        final int count;
        final String colour;
    }

    public static <T> List<T> listValue(String key, List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }
}
