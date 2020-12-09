package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class Day07Test {

    public static final String TEST_INPUT = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.""";

    public static final String TEST_INPUT_2 = """
            shiny gold bags contain 2 dark red bags.
            dark red bags contain 2 dark orange bags.
            dark orange bags contain 2 dark yellow bags.
            dark yellow bags contain 2 dark green bags.
            dark green bags contain 2 dark blue bags.
            dark blue bags contain 2 dark violet bags.
            dark violet bags contain no other bags.""";

    @Test
    public void testBagReading() {
        Day07.Bags bags = new Day07.Bags();
        TEST_INPUT.lines().forEach(bags::add);
        assertEquals(9, bags.allColours.size());
        assertEquals(9, bags.containedBy.size());
        assertEquals(7, bags.contains.size());
        assertEquals(2, bags.contains.get("shiny gold").size());
    }

    @Test
    public void testHoldersOfGold() {
        Day07.Bags bags = new Day07.Bags();
        TEST_INPUT.lines().forEach(bags::add);
        AtomicInteger count = new AtomicInteger();
        Day07.next(bags, "shiny gold", new HashSet<>(), c -> count.incrementAndGet());
        assertEquals(4, count.get());
    }

    @Test
    public void testHeldByGold() {
        Day07.Bags bags = new Day07.Bags();
        TEST_INPUT.lines().forEach(bags::add);
        long count = Day07.next2(bags, "shiny gold", new HashMap<>(), 1);
        assertEquals(32, count);
    }

    @Test
    public void testHeldByGold2() {
        Day07.Bags bags = new Day07.Bags();
        TEST_INPUT_2.lines().forEach(bags::add);
        long count = Day07.next2(bags, "shiny gold", new HashMap<>(), 1);
        assertEquals(126, count);
    }

}