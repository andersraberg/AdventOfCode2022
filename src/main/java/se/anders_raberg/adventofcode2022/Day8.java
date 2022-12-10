package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Day8 {
    private static final Logger LOGGER = Logger.getLogger(Day8.class.getName());

    private Day8() {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input8.txt"));

        int[][] grid = new int[lines.size()][lines.get(0).length()];

        for (int y = 0; y < lines.size(); y++) {
            String[] row = lines.get(y).trim().split("");
            for (int x = 0; x < row.length; x++) {
                grid[y][x] = Integer.parseInt(row[x]);
            }
        }

        int visibleTreeCounter = 0;
        int highestScenicScore = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (treeVisible(grid, y, x)) {
                    visibleTreeCounter++;
                }
                highestScenicScore = Math.max(highestScenicScore, scenicScore(grid, y, x));
            }
        }
        LOGGER.info("Part 1 : " + visibleTreeCounter);
        LOGGER.info("Part 2 : " + highestScenicScore);

    }

    private static boolean treeVisible(int[][] grid, int y, int x) {
        int value = grid[y][x];
        int maxY = grid.length;
        int maxX = grid[y].length;
        return IntStream.range(0, x).allMatch(i -> grid[y][i] < value)
                || IntStream.range(x + 1, maxX).allMatch(i -> grid[y][i] < value)
                || IntStream.range(0, y).allMatch(i -> grid[i][x] < value)
                || IntStream.range(y + 1, maxY).allMatch(i -> grid[i][x] < value);
    }

    private static int scenicScore(int[][] grid, int y, int x) {
        int value = grid[y][x];
        int maxY = grid.length;
        int maxX = grid[y].length;

        int countLeft = 0;
        for (int i = x - 1; i >= 0; i--) {
            countLeft++;
            if (grid[y][i] >= value) {
                break;
            }
        }

        int countRight = 0;
        for (int i = x + 1; i < maxX; i++) {
            countRight++;
            if (grid[y][i] >= value) {
                break;
            }
        }

        int countUp = 0;
        for (int i = y - 1; i >= 0; i--) {
            countUp++;
            if (grid[i][x] >= value) {
                break;
            }
        }

        int countDown = 0;
        for (int i = y + 1; i < maxY; i++) {
            countDown++;
            if (grid[i][x] >= value) {
                break;
            }
        }
        return countUp * countLeft * countRight * countDown;
    }

}
