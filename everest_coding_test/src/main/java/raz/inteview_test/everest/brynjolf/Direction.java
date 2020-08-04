package raz.inteview_test.everest.brynjolf;

import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Direction {

    UP("u", -1, 0),
    DOWN("d", +1, 0),
    RIGHT("r", 0, +1),
    LEFT("l", 0, -1);

    private final String direction;

    public final int rowIdxOffset;
    public final int colIdxOffset;

    Direction(String dir, int rowIdxOffset, int colIdxOffset) {
        this.direction = dir;
        this.rowIdxOffset = rowIdxOffset;
        this.colIdxOffset = colIdxOffset;
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

    public static Set<Element> nodeNeighbours(Element[][] roomMatrix, Element node) {
        Set<Element> neighbours = new HashSet<>();
        Element neighbour = null;
        for (Direction direction : Direction.values()) {
            int rowIdx = direction.rowIdxOffset == 0 ? node.rowIdx() : node.rowIdx() + direction.rowIdxOffset;
            int colIdx = direction.colIdxOffset == 0 ? node.colIdx() : node.colIdx() + direction.colIdxOffset;

            try {
                neighbour = roomMatrix[rowIdx][colIdx];
                if (!neighbour.isWall())
                    neighbours.add(neighbour);
            } catch (ArrayIndexOutOfBoundsException e) {
                //Ignored
            }
        }
        return neighbours;
    }
}
