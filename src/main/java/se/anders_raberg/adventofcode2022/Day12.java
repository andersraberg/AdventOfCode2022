package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;

public class Day12 {
    private static final Logger LOGGER = Logger.getLogger(Day12.class.getName());

    private Day12() {
    }

    private record Pos(int y, int x) {
    }

    public static void run() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputs/input12.txt"));
        Graph<Pos, String> graph = new DefaultDirectedGraph<>(String.class);

        int height = lines.size();
        int width = lines.get(0).length();

        Map<Pos, Character> grid = new HashMap<>();
        for (int y = 0; y < height; y++) {
            char[] row = lines.get(y).trim().toCharArray();
            for (int x = 0; x < width; x++) {
                char c = row[x];
                grid.put(new Pos(y, x), c);
            }
        }
        Pos start = getSpecialPos(grid, 'S');
        grid.put(start, 'a');

        Pos end = getSpecialPos(grid, 'E');
        grid.put(end, 'z');

        grid.keySet().forEach(graph::addVertex);
        addEdges(grid, height, width, graph);

        DijkstraShortestPath<Pos, String> dijkstra = new DijkstraShortestPath<>(graph);

        LOGGER.info(() -> "Part 1: " + dijkstra.getPath(start, end).getLength());

        LOGGER.info(() -> "Part 2: " + grid.entrySet().stream() //
                .filter(e -> e.getValue().equals('a')) //
                .map(Entry::getKey) //
                .map(k -> dijkstra.getPath(k, end)) //
                .mapToInt(p -> p == null ? Integer.MAX_VALUE : p.getLength()) //
                .min().orElseThrow());
    }

    private static Pos getSpecialPos(Map<Pos, Character> grid, Character c) {
        return grid.entrySet().stream() //
                .filter(e -> e.getValue().equals(c)) //
                .map(Entry::getKey)//
                .findFirst() //
                .orElseThrow();
    }

    private static void addEdges(Map<Pos, Character> grid, int height, int width, Graph<Pos, String> graph) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                addEdge(grid, graph, new Pos(y, x), new Pos(y - 1, x));
                addEdge(grid, graph, new Pos(y, x), new Pos(y + 1, x));
                addEdge(grid, graph, new Pos(y, x), new Pos(y, x - 1));
                addEdge(grid, graph, new Pos(y, x), new Pos(y, x + 1));
            }
        }

    }

    private static void addEdge(Map<Pos, Character> grid, Graph<Pos, String> graph, Pos from, Pos to) {
        if (grid.getOrDefault(to, Character.MAX_VALUE) <= grid.get(from) + 1) {
            graph.addEdge(from, to, String.format("%s -> %s", from, to));
        }
    }

}
