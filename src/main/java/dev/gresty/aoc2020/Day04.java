package dev.gresty.aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.gresty.aoc2020.Utils.*;

public class Day04 {

    public static void main(String[] args) {
        testCombine();
        test();
        time(() -> "Puzzle 1: " + puzzle1());
        time(() -> "Puzzle 2: " + puzzle2());
    }

    public static void testCombine() {
        msg("" + combineInputs(Arrays.stream(testInput().split("\\R"))));
    }

    public static void test() {
        long valid = combineInputs(Arrays.stream(testInput().split("\\R"))).stream()
                .filter(Day04::allValuesPresent)
                .count();
        msg("Test: valid = " + valid);
    }

    public static long puzzle1() {
        var passportLines = withLines("day04.txt", Day04::combineInputs);
        return passportLines.stream()
                .filter(Day04::allValuesPresent)
                .count();
    }

    public static long puzzle2() {
        var passportLines = withLines("day04.txt", Day04::combineInputs);
        return passportLines.stream()
                .filter(Day04::fullCheck)
                .count();
    }

    public static List<String> combineInputs(Stream<String> stream) {
        return stream.collect(ArrayList::new,
                (a, t) -> {
                    if (t.length() == 0) {
                        a.add("");
                    } else if (a.size() == 0) {
                        a.add(t);
                    } else {
                        a.set(a.size() - 1, a.get(a.size() - 1) + " " + t);
                    }
                },
                ArrayList::addAll);
    }

    static String testInput() {
        return """
                ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
                byr:1937 iyr:2017 cid:147 hgt:183cm
                                
                iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
                hcl:#cfa07d byr:1929
                                
                hcl:#ae17e1 iyr:2013
                eyr:2024
                ecl:brn pid:760753108 byr:1931
                hgt:179cm
                                
                hcl:#cfa07d eyr:2025 pid:166559648
                iyr:2011 ecl:brn hgt:59in""";
    }

    public static boolean allValuesPresent(String input) {
        try {
            new Passport(input, false);
            return true;
        } catch (InvalidPassportException e) {
            msg("Error: " + e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean fullCheck(String input) {
        try {
            new Passport(input, true);
            return true;
        } catch (InvalidPassportException e) {
            msg("Error: " + e.getLocalizedMessage());
            return false;
        }
    }

    static class Passport {
        int byr;
        int iyr;
        int eyr;
        String hgt;
        String hcl;
        String ecl;
        String pid;
        String cid;

        Passport (String input, boolean enhancedCheck) {
            var values = decode(input);
            basicCheck(values);

            if (enhancedCheck) {
                byr = parseYear("byr", values, 1920, 2002);
                iyr = parseYear("iyr", values, 2010, 2020);
                eyr = parseYear("eyr", values, 2020, 2030);
                hgt = parseHeight(values);
                hcl = parseHairColour(values);
                ecl = parseEyeColour(values);
                pid = parsePassportId(values);
                cid = parseCountryId(values);

                if (byr > iyr) throw new InvalidPassportException("byr " + byr + " > iyr " + iyr);
                if (iyr > eyr) throw new InvalidPassportException("iyr " + iyr + " > eyr " + eyr);
            }
        }

        private static Map<String, String> decode(String input) {
            try {
                return Arrays.stream(input.trim().split(" "))
                        .map(s -> Pair.of(s.split(":")))
                        .collect(Collectors.toMap(Pair::a, Pair::b));
            } catch (RuntimeException e) {
                throw new InvalidPassportException("Invalid input: " + input, e);
            }
        }

        static final String[] checkedFields = new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
        private void basicCheck(Map<String, String> values) {
            if (Arrays.stream(checkedFields)
                    .map(values::get)
                    .anyMatch(v -> v == null || v.trim().length() == 0)) {
                throw new InvalidPassportException("Missing values!");
            }
        }

        private int parseYear(String name, Map<String, String> values, int min, int max) {
            String value = values.get(name);
            if (value == null) throw new InvalidPassportException("Missing " + name);
            try {
                int year = Integer.parseInt(value);
                if (year < min || year > max) throw new InvalidPassportException("Invalid " + name + ": " + value);
                return year;
            } catch (NumberFormatException e) {
                throw new InvalidPassportException("Invalid " + name + ": " + value);
            }
        }

        static final Pattern HEIGHT = Pattern.compile("^(\\d+)(in|cm)$");
        private String parseHeight(Map<String, String> values) {
            String value = values.get("hgt");
            if (value == null) throw new InvalidPassportException("Missing hgt");
            Matcher m = HEIGHT.matcher(value);
            if (!m.matches()) {
                throw new InvalidPassportException("Invalid hgt: " + value);
            }
            int heightNum = Integer.parseInt(m.group(1));
            if (m.group(2).equals("cm") && (heightNum < 150 || heightNum > 193)) {
                throw new InvalidPassportException("Invalid hgt: " + value);
            }
            if (m.group(2).equals("in") && (heightNum < 59 || heightNum > 76)) {
                throw new InvalidPassportException("Invalid hgt: " + value);
            }
            return value;
        }

        static final Pattern COLOUR_HEX = Pattern.compile("^#[a-f0-9]{6}$");
        private String parseHairColour(Map<String, String> values) {
            String value = values.get("hcl");
            if (value == null) throw new InvalidPassportException("Missing hcl");
            Matcher m = COLOUR_HEX.matcher(value);
            if (!m.matches()) {
                throw new InvalidPassportException("Invalid hcl: " + value);
            }
            return value;
        }

        static final Pattern COLOUR_NAME = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$");
        private String parseEyeColour(Map<String, String> values) {
            String value = values.get("ecl");
            if (value == null) throw new InvalidPassportException("Missing ecl");
            Matcher m = COLOUR_NAME.matcher(value);
            if (!m.matches()) {
                throw new InvalidPassportException("Invalid ecl: " + value);
            }
            return value;
        }

        static final Pattern PASSPORT = Pattern.compile("^\\d{9}$");
        private String parsePassportId(Map<String, String> values) {
            String value = values.get("pid");
            if (value == null) throw new InvalidPassportException("Missing pid");
            Matcher m = PASSPORT.matcher(value);
            if (!m.matches()) {
                throw new InvalidPassportException("Invalid pid: " + value);
            }
            return value;
        }

        private String parseCountryId(Map<String, String> values) {
            return values.get("cid");
        }



    }

    public static class InvalidPassportException extends RuntimeException {
        public InvalidPassportException(String reason) {
            super(reason);
        }
        public InvalidPassportException(String reason, Exception cause) {
            super(reason, cause);
        }
    }


}
