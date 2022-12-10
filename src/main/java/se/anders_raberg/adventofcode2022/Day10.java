package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Range;

public class Day10 {
    private static final Logger LOGGER = Logger.getLogger(Day10.class.getName());
    private static final Pattern NOOP_PATTERN = Pattern.compile("noop");
    private static final Pattern ADDX_PATTERN = Pattern.compile("addx (.+)");
    private static final int LINE_LENGTH = 40;

    private Day10() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input10.txt"));

        int registerX = 1;
        List<Integer> registerValues = new ArrayList<>();
        for (String line : lines) {
            Matcher m;
            m = NOOP_PATTERN.matcher(line);
            if (m.matches()) {
                registerValues.add(registerX);
            }
            m = ADDX_PATTERN.matcher(line);
            if (m.matches()) {
                registerValues.add(registerX);
                registerValues.add(registerX);
                registerX += Integer.parseInt(m.group(1));
            }
        }

        LOGGER.info(() -> "Part 1: "
                + List.of(20, 60, 100, 140, 180, 220).stream().mapToInt(c -> c * registerValues.get(c - 1)).sum());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 240; i++) {
            Range<Integer> range = Range.closed(registerValues.get(i) - 1, registerValues.get(i) + 1);
            sb.append(range.contains(i % LINE_LENGTH) ? "#" : " ").append((i + 1) % 40 == 0 ? "\n" : "");
        }
        LOGGER.info(() -> "Part 2 : \n" + sb);
    }

}
