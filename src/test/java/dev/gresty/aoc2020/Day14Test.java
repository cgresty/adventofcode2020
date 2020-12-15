package dev.gresty.aoc2020;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {

    @Test
    public void test1() {
        long max4 = (1L << 4) - 1;
        Day14.SeaPortComputer spc = new Day14.SeaPortComputer(4);
        spc.setMask("X10X");
        assertEquals(max4 - 2, spc.mask0);
        assertEquals(4, spc.mask1);

        spc.setMem(10L, 0b1010);
        assertEquals(12, spc.memory.get(10L));
    }

    @Test
    public void test2() {
        Day14.SeaPortComputer spc = new Day14.SeaPortComputer(4);
        spc.setAddrMask("X10X");
        assertEquals(2, spc.addrMaskThru);
        assertEquals(6, spc.addrMask1);
        assertEquals(2, spc.addrMaskFloat.size());
        assertEquals(0, spc.addrMaskFloat.get(0));
        assertEquals(3, spc.addrMaskFloat.get(1));

        spc.setMem(10L, 10L);
        assertEquals(10L, spc.memory.get(0b0110L));
        assertEquals(10L, spc.memory.get(0b0111L));
        assertEquals(10L, spc.memory.get(0b1110L));
        assertEquals(10L, spc.memory.get(0b1111L));
    }

    @Test
    public void test2a() {
        Day14.SeaPortComputer spc = new Day14.SeaPortComputer(36);
        spc.setAddrMask("000000000000000000000000000000X1001X");
        spc.setMem(42L, 100L);
        assertEquals(100L, spc.memory.get(26L));
        assertEquals(100L, spc.memory.get(27L));
        assertEquals(100L, spc.memory.get(58L));
        assertEquals(100L, spc.memory.get(59L));


    }

}