package raz.inteview_test.everest.brynjolf;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Direction {

    UP("u"), DOWN("d"), RIGHT("r"), LEFT("l");

    private final String direction;

    Direction(String dir) {
        this.direction = dir;
    }

    private static Direction fromString(String dir) {
        for (Direction oneValue : Direction.values()) {
            if (oneValue.direction.equals(dir))
                return oneValue;
        }
        throw new IllegalArgumentException("Unknown Direction:" + dir);
    }

    public static List<Direction> parseString(String directionsSequence) {
        Stream<String> stringStream = directionsSequence.codePoints()
                .mapToObj(c -> String.valueOf((char) c));

        return stringStream
                .map(s -> Direction.fromString(s))
                .collect(Collectors.toList());
    }
}
