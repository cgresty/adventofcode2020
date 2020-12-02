package dev.gresty.aoc2020;

import java.io.*;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Utils {

    public static void msg(String msg) {
        System.out.println(msg);
    }

    public static <T> T withLines(String filename, Function<Stream<String>, T> function) {
        try (var in = readFile(filename)) {
            return function.apply(in.lines());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static InputStream streamFile(String filename) throws IOException {
//        return resource(filename).openStream();
//    }

    public static BufferedReader readFile(String filename) throws IOException {
        return new BufferedReader(new InputStreamReader(resource(filename).openStream()));
    }

    public static URL resource(String filename) throws FileNotFoundException {
        URL url = Utils.class.getClassLoader().getResource("dev/gresty/aoc2020/" + filename);
        if (url == null) throw new FileNotFoundException("dev/gresty/aoc2020/" + filename);
        return url;
    }

    public static void time(Supplier<String> supplier) {
        long start = System.currentTimeMillis();
        msg(supplier.get());
        long duration = System.currentTimeMillis() - start;
        msg("Duration: " + duration + "ms");
    }
}
