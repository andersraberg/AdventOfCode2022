package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.LongBinaryOperator;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.anders_raberg.adventofcode2022.utilities.Pair;

public class Day11 {
    private static final Logger LOGGER = Logger.getLogger(Day11.class.getName());
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\s*new = old ([\\+\\*]) (.+)");
    private static final Pattern MONKEY_PATTERN = Pattern.compile("\\s*Monkey (\\d+).*;" + //
            "\\s*Starting items\\: (.+);" + //
            "\\s*Operation\\: (.+);" + //
            "\\s*Test\\: divisible by (\\d+);" + //
            "\\s*If true\\: throw to monkey (\\d+);" + //
            "\\s*If false\\: throw to monkey (\\d+)");

    private Day11() {
    }

    private static class Monkey {
        private List<Long> _items;
        private final UnaryOperator<Long> _operation;
        private final long _testDevisor;
        private final long _trueMonkey;
        private final long _falseMonkey;
        private long _inspectionCounter;

        public Monkey(List<Long> items, UnaryOperator<Long> operation, long testDevisor, long trueMonkey,
                long falseMonkey) {
            _items = new ArrayList<>(items);
            _operation = operation;
            _testDevisor = testDevisor;
            _trueMonkey = trueMonkey;
            _falseMonkey = falseMonkey;
        }

        public List<Pair<Long, Long>> updateAndRemove(long devisor, long moduloDevisor) {
            List<Pair<Long, Long>> list = _items.stream() //
                    .map(i -> _operation.apply(i) / devisor) //
                    .map(i -> i % moduloDevisor) //
                    .map(i -> new Pair<>(i, i % _testDevisor == 0 ? _trueMonkey : _falseMonkey)).toList();
            _inspectionCounter += list.size();
            _items.clear();
            return list;
        }

        public void add(long item) {
            _items.add(item);
        }

        public long getInspectionCounter() {
            return _inspectionCounter;
        }

        public long getTestDevisor() {
            return _testDevisor;
        }

    }

    public static void run() throws IOException {
        String[] data = new String(Files.readAllBytes(Paths.get("inputs/input11.txt"))).split("\n\n");

        LOGGER.info(() -> "Part 1: " + runGame(data, 20, 3));
        LOGGER.info(() -> "Part 2: " + runGame(data, 10_000, 1));
    }

    private static long runGame(String[] data, long rounds, long divsor) {
        Map<Long, Monkey> monkeys = parseMonkeys(data);
        long superModulo = monkeys.values().stream().map(Monkey::getTestDevisor).reduce(Math::multiplyExact)
                .orElseThrow();
        for (long i = 0; i < rounds; i++) {
            for (Long monkeyKey : monkeys.keySet()) {
                List<Pair<Long, Long>> updateAndRemove = monkeys.get(monkeyKey).updateAndRemove(divsor, superModulo);
                updateAndRemove.forEach(p -> monkeys.get(p.second()).add(p.first()));
            }
        }

        return monkeys.values().stream().map(Monkey::getInspectionCounter).sorted(Collections.reverseOrder()).limit(2)
                .reduce(Math::multiplyExact).orElseThrow();
    }

    private static Map<Long, Monkey> parseMonkeys(String[] data) {
        Map<Long, Monkey> monkeys = new TreeMap<>();

        for (String monkeyString : data) {
            Matcher m = MONKEY_PATTERN.matcher(monkeyString.replace("\n", ";"));
            m.find();
            List<Long> items = Arrays.stream(m.group(2).split("[ ,]+")).map(Long::parseLong).toList();
            monkeys.put(Long.parseLong(m.group(1)), new Monkey( //
                    items, //
                    parseFuntion(m.group(3)), //
                    Long.parseLong(m.group(4)), //
                    Long.parseLong(m.group(5)), //
                    Long.parseLong(m.group(6))));
        }
        return monkeys;
    }

    private static UnaryOperator<Long> parseFuntion(String str) {
        Matcher m = FUNCTION_PATTERN.matcher(str);
        m.find();
        LongBinaryOperator op = m.group(1).equals("+") ? Math::addExact : Math::multiplyExact;
        return m.group(2).equals("old") ? o -> op.applyAsLong(o, o)
                : o -> op.applyAsLong(o, Long.parseLong(m.group(2)));
    }
}
