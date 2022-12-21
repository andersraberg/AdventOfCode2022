package se.anders_raberg.adventofcode2022.utilities;

public class ToStringUtilities {
    private ToStringUtilities() {
    }

    public static <X> String toString(X[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                sb.append(grid[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String toString(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                sb.append(grid[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String toString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                sb.append(grid[y][x]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
