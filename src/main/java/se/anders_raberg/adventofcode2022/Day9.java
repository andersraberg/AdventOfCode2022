package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Day9 {
    private static final Logger LOGGER = Logger.getLogger(Day9.class.getName());

    private Day9() {
    }

    private enum Dir {
        R, L, U, D
    }

    private record Move(Dir dir, int steps) {
        public static Move parse(String str) {
            String[] split = str.split(" ");
            return new Move(Dir.valueOf(split[0]), Integer.valueOf(split[1]));
        }
    }

    private record Pos(int x, int y) {
    }

    public static void run() throws IOException {
        List<Move> moves = Files.readAllLines(Paths.get("inputs/input9.txt")).stream().map(Move::parse).toList();

        Pos start = new Pos(0, 0);
        Pos head = start;

        Map<Integer, Pos> tails = new HashMap<>();
        Map<Integer, Set<Pos>> visiteds = new HashMap<>();

        for (Move move : moves) {
            for (int s = 0; s < move.steps; s++) {
                head = updateHead(head, move.dir);
                tails.put(1, updateTail(head, tails.getOrDefault(1, start),
                        visiteds.computeIfAbsent(1, x -> new HashSet<>(Set.of(start)))));
                for (int t = 2; t < 10; t++) {
                    tails.put(t, updateTail(tails.get(t - 1), tails.getOrDefault(t, start),
                            visiteds.computeIfAbsent(t, x -> new HashSet<>(Set.of(start)))));
                }
            }
        }

        LOGGER.info(() -> "Part 1: " + visiteds.get(1).size());
        LOGGER.info(() -> "Part 2: " + visiteds.get(9).size());
    }

    private static Pos updateHead(Pos pos, Dir dir) {
        return switch (dir) {
        case R -> new Pos(pos.x + 1, pos.y);
        case L -> new Pos(pos.x - 1, pos.y);
        case U -> new Pos(pos.x, pos.y + 1);
        case D -> new Pos(pos.x, pos.y - 1);
        default -> throw new IllegalArgumentException("Unexpected value: " + dir);
        };
    }

    private static Pos updateTail(Pos head, Pos tail, Set<Pos> visited) {
        while (!adjecent(head, tail)) {
            int dx = Integer.compare(head.x, tail.x);
            int dy = Integer.compare(head.y, tail.y);

            if (dx == 0 && dy != 0) {
                tail = new Pos(tail.x, tail.y + dy);
            } else if (dy == 0 && dx != 0) {
                tail = new Pos(tail.x + dx, tail.y);
            } else {
                tail = new Pos(tail.x + dx, tail.y + dy);
            }
            visited.add(tail);
        }
        return tail;

    }

    private static boolean adjecent(Pos a, Pos b) {
        return Math.abs(a.x - b.x) <= 1 && Math.abs(a.y - b.y) <= 1;
    }

}
