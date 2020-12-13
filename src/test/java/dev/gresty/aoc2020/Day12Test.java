package dev.gresty.aoc2020;

import dev.gresty.aoc2020.Day12.Ferry;
import dev.gresty.aoc2020.Day12.FerryMover;
import dev.gresty.aoc2020.Day12.Instruction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day12Test {

    @Test
    public void test1() {
        Ferry f = new Ferry();
        FerryMover m = FerryMover.forPuzzle1(f);
        INPUT.lines()
                .map(Instruction::decode)
                .forEach(m::move);
        assertEquals(25, f.loc.manhattan());
    }

    @Test
    public void test2() {
        Ferry f = new Ferry();
        FerryMover m = FerryMover.forPuzzle2(f);
        INPUT.lines()
                .map(Instruction::decode)
                .forEach(m::move);
        assertEquals(286, f.loc.manhattan());
    }

    static final String INPUT = """
            F10
            N3
            F7
            R90
            F11""";
}