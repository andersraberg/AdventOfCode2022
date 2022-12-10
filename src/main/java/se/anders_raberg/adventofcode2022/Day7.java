package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private static final Logger LOGGER = Logger.getLogger(Day7.class.getName());
    private static final Pattern FILE_PATTERN = Pattern.compile("(\\d+) (.+)");
    private static final Pattern DIR_PATTERN = Pattern.compile("dir (.+)");
    private static final Pattern CD_DOWN_PATTERN = Pattern.compile("\\$ cd ([a-z]+)");
    private static final Pattern CD_UP_PATTERN = Pattern.compile("\\$ cd \\.\\.");
    private static final int MAX_DIR_SIZE = 100_000;
    private static final int REQUIRED_UNUSED_DISK_SPACE = 30_000_000;
    private static final int TOTAL_DISK_SPACE = 70_000_000;

    private Day7() {
    }

    private record Directory(Directory parent, Map<String, Directory> dirs, Map<String, Integer> files) {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input7.txt"));
        Directory directory = new Directory(null, new HashMap<>(), new HashMap<>());
        Directory top = directory;

        Matcher m;
        for (String line : lines) {
            m = FILE_PATTERN.matcher(line);
            if (m.matches()) {
                directory.files.putIfAbsent(m.group(2), Integer.parseInt(m.group(1)));
            }

            m = DIR_PATTERN.matcher(line);
            if (m.matches()) {
                directory.dirs.putIfAbsent(m.group(1), new Directory(directory, new HashMap<>(), new HashMap<>()));
            }

            m = CD_DOWN_PATTERN.matcher(line);
            if (m.matches()) {
                directory = directory.dirs.get(m.group(1));
            }

            m = CD_UP_PATTERN.matcher(line);
            if (m.matches()) {
                directory = directory.parent;
            }
        }

        List<Integer> dirSizes = new ArrayList<>();
        int topLevelSize = collectSizes(top, dirSizes);
        LOGGER.info(() -> "Part 1 : " + dirSizes.stream().filter(size -> size <= MAX_DIR_SIZE).reduce(0, Integer::sum));

        int needTofree = REQUIRED_UNUSED_DISK_SPACE - TOTAL_DISK_SPACE + topLevelSize;
        LOGGER.info(() -> "Part 2 : "
                + dirSizes.stream().sorted().filter(size -> size >= needTofree).findFirst().orElseThrow());
    }

    private static int collectSizes(Directory dir, List<Integer> result) {
        int dirSize = dir.files.values().stream().reduce(0, Integer::sum)
                + dir.dirs.values().stream().map(d -> collectSizes(d, result)).reduce(0, Integer::sum);
        result.add(dirSize);
        return dirSize;
    }

}
