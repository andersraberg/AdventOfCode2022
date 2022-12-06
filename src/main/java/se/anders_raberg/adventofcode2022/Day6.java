package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Day6 {
    private static final Logger LOGGER = Logger.getLogger(Day6.class.getName());

    private Day6() {
    }

    public static void run() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("inputs/input6.txt")));
        List<String> receivedChars = Arrays.stream(data.split("")).toList();

        LOGGER.info(() -> "Part 1 : " + findFirstMarker(receivedChars, 4));
        LOGGER.info(() -> "Part 2 : " + findFirstMarker(receivedChars, 14));
    }

    private static int findFirstMarker(List<String> receivedChars, int length) {
        for (int i = length; i < receivedChars.size(); i++) {
            if (receivedChars.subList(i - length, i).stream().distinct().count() == length) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

}
