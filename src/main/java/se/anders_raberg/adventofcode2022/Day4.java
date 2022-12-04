package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.Range;

import se.anders_raberg.adventofcode2022.utilities.Pair;

public class Day4 {
    private static final Logger LOGGER = Logger.getLogger(Day4.class.getName());
    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    private Day4() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input4.txt")).stream().collect(Collectors.toList());
        LOGGER.info(() -> "Part 1: " + lines.stream().map(Day4::parse).filter(Day4::encloses).count());
        LOGGER.info(() -> "Part 2: " + lines.stream().map(Day4::parse).map(Day4::overlap).filter(Optional::isPresent)
                .map(Optional::get).count());
    }

    private static Pair<Range<Integer>, Range<Integer>> parse(String line) {
        Matcher m = PATTERN.matcher(line);
        if (m.matches()) {
            return new Pair<Range<Integer>, Range<Integer>>(
                    Range.closed(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))),
                    Range.closed(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))));
        }
        throw new IllegalArgumentException(line);
    }

    private static boolean encloses(Pair<Range<Integer>, Range<Integer>> pair) {
        return pair.first().encloses(pair.second()) || pair.second().encloses(pair.first());
    }

    private static Optional<Range<Integer>> overlap(Pair<Range<Integer>, Range<Integer>> pair) {
        if (pair.first().isConnected(pair.second())) {
            return Optional.of(pair.first().intersection(pair.second()));
        }
        return Optional.empty();
    }

}
