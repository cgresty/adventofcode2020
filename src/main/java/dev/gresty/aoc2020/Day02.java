package dev.gresty.aoc2020;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.gresty.aoc2020.Utils.time;
import static dev.gresty.aoc2020.Utils.withLines;

public class Day02 {

    public static void main(String[] args) {
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static int puzzle1() {
        return withLines("day02.txt",
                stream -> (int) stream.map(PolicyAndPassword::new)
                        .filter(PolicyAndPassword::isValid)
                        .count());
    }

    public static int puzzle2() {
        return withLines("day02.txt",
                stream -> (int) stream.map(PolicyAndPassword::new)
                        .filter(PolicyAndPassword::isValid2)
                        .count());
    }

    static class PolicyAndPassword {

        static final Pattern INPUT_REGEX = Pattern.compile("^(\\d+)-(\\d+) (\\w): (\\w+)$");

        final int min;
        final int max;
        final char c;
        final String pw;

        PolicyAndPassword(String input) {
            Matcher m = INPUT_REGEX.matcher(input);
            if (m.matches()) {
                min = Integer.parseInt(m.group(1));
                max = Integer.parseInt(m.group(2));
                c = m.group(3).charAt(0);
                pw = m.group(4);
            } else {
                throw new RuntimeException("Unrecognized input format [" + input + "]");
            }
        }

        boolean isValid() {
            int count = 0;
            for (int i = 0; i < pw.length(); i++) {
                count += pw.charAt(i) == c ? 1 : 0;
            }
            return count <= max && count >= min;
        }

        boolean isValid2() {
            int count = 0;
            count += testCharAt(min) ? 1 : 0;
            count += testCharAt(max) ? 1 : 0;
            return count == 1;
        }

        boolean testCharAt(int i) {
            return pw.length() >= i && pw.charAt(i - 1) == c;
        }
    }


}
