package raz.inteview_test.everest.brynjolf.solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static raz.inteview_test.everest.brynjolf.Direction.*;

import raz.inteview_test.everest.brynjolf.GameStatus;
import raz.inteview_test.everest.brynjolf.SimulationResult;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.Room;
import raz.inteview_test.everest.brynjolf.room.RoomMovementTestBase;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static raz.inteview_test.everest.brynjolf.GameStatus.WIN;

class PathSolverShould extends RoomMovementTestBase {

    PathSolver pathSolver;

    Room room;

    @Override
    protected String testFilesSubDir() {
        return "solver";
    }

    private void setUp(String inputFile) throws IOException {
        Path filePath = testSubDir.resolve(inputFile);
        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        pathSolver = new PathSolver(roomMatrix);
        room = new Room(roomMatrix);
    }

    @ParameterizedTest
    @MethodSource("testArgs")
    void find_movements_sequence_to_the_exit(String inputFile, List<Direction> expected, GameStatus expectedStatus) throws Exception {
        setUp(inputFile);

        //Building the Model/Output Data Set
        List<Direction> directionsToExit = pathSolver.findPath();

        assertEquals(expected, directionsToExit);

        //Evaluation
        //If the list of moves was correctly generated, you should win the game
        SimulationResult result = room.executeMoveSequence(directionsToExit);

        assertEquals(expectedStatus, result.getGameStatus());
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(
                        "initial_go_right.txt",
                        Collections.singletonList(RIGHT),
                        WIN)
        );
    }
}
