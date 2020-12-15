package dev.gresty.aoc2020;

import lombok.Data;

import java.util.*;

import static dev.gresty.aoc2020.Utils.*;
import static dev.gresty.aoc2020.Utils.time;

public class Day14 {
    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    static long puzzle1() {
        SeaPortComputer spc = new SeaPortComputer(36);
        Interpreter inter = new Interpreter(spc);
        withLines2("day14.txt", s -> s.forEach(inter::apply));
        return spc.sumValues();
    }

    static long puzzle2() {
        SeaPortComputer spc = new SeaPortComputer(36);
        Interpreter2 inter = new Interpreter2(spc);
        withLines2("day14.txt", s -> s.forEach(inter::apply));
        return spc.sumValues();
    }

    @Data
    static class Interpreter {
        final SeaPortComputer spc;

        void apply(String instr) {
            String[] atoms = instr.split(" ");
            if (atoms[0].equals("mask")) {
                spc.setMask(atoms[2]);
            } else if (atoms[0].startsWith("mem")) {
                long addr = Long.parseLong(atoms[0].substring(4, atoms[0].length() - 1));
                long value = Long.parseLong(atoms[2]);
                spc.setMem(addr, value);
            }
        }
    }

    @Data
    static class Interpreter2 {
        final SeaPortComputer spc;

        void apply(String instr) {
            String[] atoms = instr.split(" ");
            if (atoms[0].equals("mask")) {
                spc.setAddrMask(atoms[2]);
            } else if (atoms[0].startsWith("mem")) {
                long addr = Long.parseLong(atoms[0].substring(4, atoms[0].length() - 1));
                long value = Long.parseLong(atoms[2]);
                spc.setMem(addr, value);
            }
        }
    }

    @Data
    static class SeaPortComputer {
        final int wordlen;
        final long mask;

        Map<Long, Long> memory = new HashMap<>();
        long mask0; // All 1, except 0 where setting 0
        long mask1; // All 0, except 1 where setting 1;
        long addrMaskThru; // 1 for passthrough, 0 for masked
        long addrMask1; // 1 for passthrough or 1, 0 for 0
        List<Integer> addrMaskFloat = new ArrayList<>();

        SeaPortComputer(int wordlen) {
            this.wordlen = wordlen;
            mask = (1L << wordlen) - 1;
            mask0 = mask;
            mask1 = 0;
            addrMaskThru = mask;
            addrMask1 = mask;
        }

        void setMask(String mask) {
            mask0 = 0L;
            mask1 = 0L;
            for (int i = 0; i < wordlen; i++) {
                mask0 = mask0 << 1;
                mask1 = mask1 << 1;
                if (mask.charAt(i) == 'X') {
                    mask0 += 1;
                } else if (mask.charAt(i) == '1') {
                    mask0 += 1;
                    mask1 += 1;
                }
            }
        }

        void setAddrMask(String mask) {
            addrMaskThru = 0L;
            addrMask1 = 0L;
            addrMaskFloat.clear();
            for (int i = 0; i < wordlen; i++) {
                addrMaskThru = addrMaskThru << 1;
                addrMask1 = addrMask1 << 1;
                if (mask.charAt(i) == 'X') {
                    addrMaskFloat.add(wordlen - i - 1);
                } else if (mask.charAt(i) == '0') {
                    addrMaskThru += 1;
                } else if (mask.charAt(i) == '1') {
                    addrMask1 += 1;
                }
            }
            Collections.reverse(addrMaskFloat);
        }

        void setMem(long addr, long value) {
            long maskedValue = ((value & mask) & mask0) | mask1;
            var maxFloat = Long.max(0, (1 << addrMaskFloat.size()) - 1);
            for (int i = 0; i <= maxFloat; i++) {
                long maskedAddr = ((addr & addrMaskThru) | addrMask1) | getNthFloatMask(i);
                memory.put(maskedAddr, maskedValue);
            }
        }

        long getNthFloatMask(long n) {
            long floatMask = 0;
            for (Integer integer : addrMaskFloat) {
                floatMask |= (n % 2) << integer;
                n = n >>> 1;
            }
            return floatMask;
        }

        long sumValues() {
            return memory.values().stream().mapToLong(Long::longValue).sum();
        }
    }
}
