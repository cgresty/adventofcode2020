package dev.gresty.aoc2020;

import static dev.gresty.aoc2020.Utils.*;

public class Day01 {

    public static void main(String[] args) {
        int[] expenses = withLines("day01.txt",
                s -> s.mapToInt(Integer::parseInt).toArray());

        time(() -> "Puzzle 1: " + puzzle1(expenses));
        time(() -> "Puzzle 2: " + puzzle2(expenses));
    }

    static int puzzle1(int[] expenses) {
        for (int i = 0; i < expenses.length - 1; i++) {
            for (int j = i + 1; j < expenses.length; j++) {
                if (expenses[i] + expenses[j] == 2020) {
                    return expenses[i] * expenses[j];
                }
            }
        }
        return -1;
    }

    static int puzzle2(int[] expenses) {
        for (int i = 0; i < expenses.length - 2; i++) {
            for (int j = i + 1; j < expenses.length - 1; j++) {
                for (int k = j + 1; k < expenses.length; k++) {
                    if (expenses[i] + expenses[j] + expenses[k] == 2020) {
                        return expenses[i] * expenses[j] * expenses[k];
                    }
                }
            }
        }
        return -1;
    }
}
