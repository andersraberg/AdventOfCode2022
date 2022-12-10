package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import se.anders_raberg.adventofcode2022.utilities.SublistCollector;

public class Day3 {
    private static final Logger LOGGER = Logger.getLogger(Day3.class.getName());
    private static final int LOWER_CASE_OFFSET = 96;
    private static final int UPPER_CASE_OFFSET = 38;

    private Day3() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input3.txt"));
        LOGGER.info(() -> "Part 1: " + lines.stream().map(Day3::splitHalves).map(Day3::multiStringIntersection)
                .mapToInt(Day3::calcPrio).sum());

        LOGGER.info(() -> "Part 2: " + lines.stream().collect(new SublistCollector<>(3, false)).stream()
                .map(Day3::multiStringIntersection).mapToInt(Day3::calcPrio).sum());
    }

    private static List<String> splitHalves(String str) {
        int middle = str.length() / 2;
        return List.of(str.substring(0, middle), str.substring(middle));
    }

    private static Set<String> multiStringIntersection(List<String> strings) {
        Set<String> result = new HashSet<>(splitToChars(strings.get(0)));
        for (int i = 1; i < strings.size(); i++) {
            result.retainAll(splitToChars(strings.get(i)));
        }

        return result;
    }

    private static Set<String> splitToChars(String qwe) {
        return Arrays.stream(qwe.split("")).collect(Collectors.toSet());
    }

    private static int calcPrio(Set<String> priorities) {
        return priorities.stream().mapToInt(Day3::calcPrio).sum();
    }

    private static int calcPrio(String priority) {
        char charAt = priority.charAt(0);
        return Character.isLowerCase(charAt) ? charAt - LOWER_CASE_OFFSET : charAt - UPPER_CASE_OFFSET;
    }

}
