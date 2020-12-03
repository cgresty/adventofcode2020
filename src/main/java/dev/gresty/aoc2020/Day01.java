package dev.gresty.aoc2020;

import java.util.Set;
import java.util.stream.Collectors;

import static dev.gresty.aoc2020.Utils.*;

public class Day01 {

    public static void main(String[] args) {
        var expensesSet = withLines("day01.txt",
                s -> s.map(Integer::parseInt).collect(Collectors.toSet()));

        time(() -> "Puzzle 1a: " + puzzle1a(expensesSet));
        time(() -> "Puzzle 2a: " + puzzle2a(expensesSet));

        var expenses = withLines("day01.txt",
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

    static int puzzle1a(Set<Integer> expenses) {
        for (Integer expense : expenses) {
            if (expenses.contains(2020 - expense)) {
                return expense * (2020 - expense);
            }
        }
        return -1;
    }

    static int puzzle2a(Set<Integer> expenses) {
        for (Integer expense1 : expenses) {
            for (Integer expense2 : expenses) {
                if (expenses.contains(2020 - expense1 - expense2)) {
                    return expense1 * expense2 * (2020 - expense1 - expense2);
                }
            }
        }
        return -1;
    }
}
