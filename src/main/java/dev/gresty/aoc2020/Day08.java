package dev.gresty.aoc2020;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;

public class Day08 {

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        Day08.Handheld hh = new Day08.Handheld();
        hh.load(withLines("day08.txt", s -> s.collect(Collectors.toList())));
        hh.runToRepeat();
        return hh.acc;
    }

    public static int puzzle2() {
        Day08.Handheld hh = new Day08.Handheld();
        var program = withLines("day08.txt", s -> s.collect(Collectors.toList()));
        for (int i = 0; i < program.size(); i++) {
            var line = program.get(i);
            String newline = null;
            if (line.startsWith("nop")) {
                newline = line.replace("nop", "jmp");
            } else if (line.startsWith("jmp")) {
                newline = line.replace("jmp", "nop");
            }
            if (newline != null) {
                program.set(i, newline);
                hh.load(program);
                var finished = hh.runToRepeat();
                program.set(i, line);
                if (finished) return hh.acc;
            }
        }
        return -1;
    }

    public static class Handheld {
        Instruction[] program;
        int acc = 0;
        int pc = 0;


        public void load(List<String> program) {
            this.program = program.stream()
                    .map(Instruction::new)
                    .toArray(Instruction[]::new);
            acc = 0;
            pc = 0;
        }

        public boolean runToRepeat() {
            while (pc < program.length && !program[pc].executed){
                pc += program[pc].execute();
            }
            return pc == program.length;
        }

        @Data
        public class Instruction {
            final String op;
            final int arg;
            boolean executed;

            public Instruction (String input) {
                op = input.substring(0, 3);
                arg = Integer.parseInt(input.substring(4));
            }

            int execute() {
                executed = true;
                switch(op) {
                    case "acc" -> { acc += arg; return 1; }
                    case "jmp" -> { return arg; }
                    default -> { return 1; }
                }
            }
        }

    }

}
