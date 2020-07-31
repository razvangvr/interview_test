package raz.inteview_test.everest.brynjolf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static raz.inteview_test.everest.brynjolf.Direction.*;

class DirectionTest {

    List<Direction> expected = Arrays.asList(LEFT, UP, DOWN, RIGHT);

    @Test
    void parseString_invalid_direction() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Direction.parseString("lupdr"));
    }

    @Test
    void parseString() {
        List<Direction> output = Direction.parseString("ludr");
        assertEquals(expected, output);
    }
}
