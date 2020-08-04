package raz.inteview_test.everest.brynjolf.solver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.ElementValue;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static raz.inteview_test.everest.brynjolf.Direction.*;

class PointsDeltaTest {

    @ParameterizedTest
    @MethodSource("testArgs")
    void same_line(Element bryn, Element exit, Set<Direction> expected) {

//        Element bryn = new Element(ElementValue.BRYN, 1, 0);
//        Element exit = new Element(ElementValue.EXIT, 1, 2);

        PointsDelta pointsDelta = new PointsDelta(bryn, exit);

//        assertEquals(0, pointsDelta.lineDelta);
//        assertEquals(2, pointsDelta.colDelta);

        Set<Direction> directionsToExit = pointsDelta.exitIsToYour();

        //assertThat(directionsToExit, contains(expected));
        assertEquals(expected, directionsToExit);


        System.out.println(pointsDelta + " " + directionsToExit);
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(
                        new Element(ElementValue.BRYN, 1, 0),
                        new Element(ElementValue.EXIT, 1, 2),
                        Collections.singleton(RIGHT)),
                Arguments.of(
                        new Element(ElementValue.BRYN, 1, 2),
                        new Element(ElementValue.EXIT, 1, 0),
                        Collections.singleton(LEFT)),
                Arguments.of(
                        new Element(ElementValue.BRYN, 2, 1),
                        new Element(ElementValue.EXIT, 0, 1),
                        Collections.singleton(UP)),
                Arguments.of(
                        new Element(ElementValue.BRYN, 0, 1),
                        new Element(ElementValue.EXIT, 2, 1),
                        Collections.singleton(DOWN)),
                Arguments.of(
                        new Element(ElementValue.BRYN, 2, 1),
                        new Element(ElementValue.EXIT, 0, 2),
                        Set.of(RIGHT, UP)),
                Arguments.of(
                        new Element(ElementValue.BRYN, 0, 1),
                        new Element(ElementValue.EXIT, 2, 0),
                        Set.of(LEFT, DOWN))

        );
    }
}
