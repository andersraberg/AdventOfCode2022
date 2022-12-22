package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
    private static final Logger LOGGER = Logger.getLogger(Day14.class.getName());
    private static final Pattern FIRST_POS_PATTERN = Pattern.compile("(\\d+),(\\d+)");
    private static final Pattern NEXT_POS_PATTERN = Pattern.compile(" -> (\\d+),(\\d+)");
    private static final int SOURCE_X = 500;
    private static final int FLOOR_MIN = 0;
    private static final int FLOOR_MAX = 1000;

    private Day14() {
    }

    private record Pos(int x, int y) {
        public static Pos parse(String x, String y) {
            return new Pos(Integer.parseInt(x), Integer.parseInt(y));
        }

        public Pos down() {
            return new Pos(x, y + 1);
        }

        public Pos downLeft() {
            return new Pos(x - 1, y + 1);
        }

        public Pos downRight() {
            return new Pos(x + 1, y + 1);
        }
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input14.txt"));

        Map<Pos, Character> grid1 = createGrid(lines);
        int max = grid1.keySet().stream().mapToInt(p -> p.y).max().orElseThrow();
        LOGGER.info(() -> "Part 1: " + countSand(grid1, max));

        Map<Pos, Character> grid2 = createGrid(lines);
        fillLine(grid2, new Pos(FLOOR_MIN, max + 2), new Pos(FLOOR_MAX, max + 2));
        LOGGER.info(() -> "Part 2 : " + countSand(grid2, max + 2));

    }

    private static int countSand(Map<Pos, Character> grid, int max) {
        int counter = 0;
        while (drop(grid, max)) {
            counter++;
        }
        return counter;
    }

    private static Map<Pos, Character> createGrid(List<String> lines) {
        Map<Pos, Character> grid = new HashMap<>();
        for (String line : lines) {
            Matcher m = FIRST_POS_PATTERN.matcher(line);
            if (m.find()) {
                Pos start = Pos.parse(m.group(1), m.group(2));
                m = NEXT_POS_PATTERN.matcher(line.substring(m.end()));
                while (m.find()) {
                    Pos end = Pos.parse(m.group(1), m.group(2));
                    fillLine(grid, start, end);
                    start = end;
                }
            }
        }
        return grid;
    }

    private static boolean drop(Map<Pos, Character> grid, int maxY) {
        int x = SOURCE_X;
        int y = 0;
        Pos pos = new Pos(x, y);

        boolean foundFreePos = false;
        while (!foundFreePos) {
            if (!grid.containsKey(pos.down())) {
                pos = pos.down();
            } else if (!grid.containsKey(pos.downLeft())) {
                pos = pos.downLeft();
            } else if (!grid.containsKey(pos.downRight())) {
                pos = pos.downRight();
            } else {
                foundFreePos = true;
            }
            if (pos.y > maxY) {
                return false;
            }
        }
        return grid.putIfAbsent(pos, 'O') == null;
    }

    private static void fillLine(Map<Pos, Character> grid, Pos start, Pos end) {
        for (int x = Math.min(start.x, end.x); x <= Math.max(start.x, end.x); x++) {
            for (int y = Math.min(start.y, end.y); y <= Math.max(start.y, end.y); y++) {
                grid.put(new Pos(x, y), '#');
            }
        }
    }

}
