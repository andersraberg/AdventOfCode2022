package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day2 {
    private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());

    private Day2() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input2.txt")).stream().collect(Collectors.toList());

        LOGGER.info(() -> "Part 1: " + lines.stream().mapToInt(x -> calcRoundPart1(x)).sum());
        LOGGER.info(() -> "Part 2: " + lines.stream().mapToInt(x -> calcRoundPart2(x)).sum());
    }

    private static int calcRoundPart1(String str) {
        return switch (str) {
        // Choice value + win/draw/lose value
        case "A X" -> 1 + 3;
        case "A Y" -> 2 + 6;
        case "A Z" -> 3 + 0;
        case "B X" -> 1 + 0;
        case "B Y" -> 2 + 3;
        case "B Z" -> 3 + 6;
        case "C X" -> 1 + 6;
        case "C Y" -> 2 + 0;
        case "C Z" -> 3 + 3;
        default -> throw new IllegalArgumentException("Unexpected value: " + str);
        };
    }

    private static int calcRoundPart2(String str) {
        return switch (str) {
        // Choice value + win/draw/lose value
        case "A X" -> 3 + 0; // C
        case "A Y" -> 1 + 3; // A
        case "A Z" -> 2 + 6; // B
        case "B X" -> 1 + 0; // A
        case "B Y" -> 2 + 3; // B
        case "B Z" -> 3 + 6; // C
        case "C X" -> 2 + 0; // B
        case "C Y" -> 3 + 3; // C
        case "C Z" -> 1 + 6; // A
        default -> throw new IllegalArgumentException("Unexpected value: " + str);
        };
    }
}
