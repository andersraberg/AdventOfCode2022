package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.anders_raberg.adventofcode2022.utilities.Triple;

public class Day5 {
    private static final Logger LOGGER = Logger.getLogger(Day5.class.getName());
    private static final Pattern POSITION_PATTERN = Pattern.compile("(...) ?");
    private static final Pattern ELEMENT_PATTERN = Pattern.compile("\\[(.)\\]");
    private static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    private Day5() {
    }

    public static void run() throws IOException {
        List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("inputs/input5.txt")));
        List<Triple<Integer, Integer, Integer>> moves = parseMoves(lines);

        // Part 1
        //
        Map<Integer, Stack<String>> stacksPart1 = parseStacks(lines);
        moves.forEach(move -> {
            for (int i = 0; i < move.first(); i++) {
                String element = stacksPart1.get(move.second()).pop();
                stacksPart1.get(move.third()).push(element);
            }
        });

        LOGGER.info("Part 1 : " + getTopElements(stacksPart1));

        // Part 2
        //
        Map<Integer, Stack<String>> stacksPart2 = parseStacks(lines);
        moves.forEach(move -> {
            Stack<String> tmp = new Stack<>();
            for (int i = 0; i < move.first(); i++) {
                tmp.push(stacksPart2.get(move.second()).pop());
            }
            while (!tmp.empty()) {
                stacksPart2.get(move.third()).push(tmp.pop());
            }

        });
        LOGGER.info("Part 2 : " + getTopElements(stacksPart2));
    }

    private static String getTopElements(Map<Integer, Stack<String>> stacks) {
        return stacks.values().stream().map(Stack::peek).reduce("", String::concat);
    }

    private static List<Triple<Integer, Integer, Integer>> parseMoves(List<String> lines) {
        List<Triple<Integer, Integer, Integer>> result = new ArrayList<>();
        lines.forEach(line -> {
            Matcher m = MOVE_PATTERN.matcher(line);
            if (m.matches()) {
                result.add(new Triple<>( //
                        Integer.parseInt(m.group(1)), //
                        Integer.parseInt(m.group(2)), //
                        Integer.parseInt(m.group(3))));
            }

        });
        return result;
    }

    private static Map<Integer, Stack<String>> parseStacks(List<String> lines) {
        Map<Integer, Stack<String>> result = new TreeMap<>();
        lines.forEach(line -> {
            Matcher m = POSITION_PATTERN.matcher(line);
            int column = 1;
            while (m.find()) {
                Matcher m2 = ELEMENT_PATTERN.matcher(m.group(1));
                if (m2.matches()) {
                    result.computeIfAbsent(column, v -> new Stack<>()).add(0, m2.group(1));
                }
                column++;
            }

        });
        return result;
    }
}
