package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger LOGGER = Logger.getLogger(Day1.class.getName());

    private Day1() {
    }

    public static void run() throws IOException {
        String[] data = new String(Files.readAllBytes(Paths.get("inputs/input1.txt"))).split("\n\n");

        List<Integer> elves = Arrays.stream(data).map(x -> Arrays.stream(x.split("\n")).mapToInt(Integer::parseInt).sum())
                .sorted(Collections.reverseOrder()).toList();

        LOGGER.info(() -> "Part 1: " + elves.get(0));
        LOGGER.info(() -> "Part 2: " + (elves.get(0) + elves.get(1) + elves.get(2)));
    }

}
