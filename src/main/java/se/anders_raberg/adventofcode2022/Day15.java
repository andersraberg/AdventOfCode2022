package se.anders_raberg.adventofcode2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    private static final Logger LOGGER = Logger.getLogger(Day15.class.getName());
    private static final Pattern PATTERN = Pattern
            .compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    private record Pos(int x, int y) {
    }

    private static  class SensorBeaconPair {
        private final Pos _sensorPos;
        private final Pos _beaconPos;
        private final int _distanceToBeacon;

        public SensorBeaconPair(Pos sensorPos, Pos beaconPos) {
            _sensorPos = sensorPos;
            _beaconPos = beaconPos;
            _distanceToBeacon = Math.abs(_sensorPos.x - _beaconPos.x) + Math.abs(_sensorPos.y - _beaconPos.y);
        }

        public static SensorBeaconPair parse(String sensorX, String sensorY, String beaconX, String beaconY) {
            return new SensorBeaconPair(new Pos(Integer.parseInt(sensorX), Integer.parseInt(sensorY)),
                    new Pos(Integer.parseInt(beaconX), Integer.parseInt(beaconY)));
        }

        public boolean insideRange(Pos pos) {
            return Math.abs(_sensorPos.x - pos.x) + Math.abs(_sensorPos.y - pos.y) <= _distanceToBeacon;
        }

        public Pos sensorPos() {
            return _sensorPos;
        }

        public Pos beaconPos() {
            return _beaconPos;
        }

    }

    private Day15() {
    }

    public static void run() throws IOException {
        List<SensorBeaconPair> sensors = Files.readAllLines(Paths.get("inputs/input15.txt")).stream() //
                .map(Day15::parseLine) //
                .toList();

        Set<Pos> positions = new HashSet<>();

        for (int i = -1_000_000; i < 6_000_000; i++) {
            Pos pos = new Pos(i, 2_000_000);

            boolean noneMatch = sensors.stream().anyMatch(s -> s.insideRange(pos));
            if (noneMatch) {
                positions.add(pos);
            }
        }

        positions.removeAll(sensors.stream().map(s -> s.sensorPos()).toList());
        positions.removeAll(sensors.stream().map(s -> s.beaconPos()).toList());
        LOGGER.info("Part 1 : " + positions.size());

    }

    private static SensorBeaconPair parseLine(String line) {
        Matcher m = PATTERN.matcher(line);
        m.find();
        return SensorBeaconPair.parse(m.group(1), m.group(2), m.group(3), m.group(4));
    }

}
