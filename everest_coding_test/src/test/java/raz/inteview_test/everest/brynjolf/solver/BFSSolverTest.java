package raz.inteview_test.everest.brynjolf.solver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.GameStatus;
import raz.inteview_test.everest.brynjolf.SimulationResult;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.Room;
import raz.inteview_test.everest.brynjolf.room.RoomMovementTestBase;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static raz.inteview_test.everest.brynjolf.Direction.RIGHT;
import static raz.inteview_test.everest.brynjolf.Direction.UP;
import static raz.inteview_test.everest.brynjolf.GameStatus.WIN;

class BFSSolverTest extends RoomMovementTestBase {

    BFSSolver solver;

    Room room;

    private void setUp(String inputFile) throws IOException {
        Path filePath = testSubDir.resolve(inputFile);
        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        solver = new BFSSolver(roomMatrix);

        room = new Room(roomMatrix);
    }

    @Test
    void nodesToExit() {
    }

    @Override
    protected String testFilesSubDir() {
        return "solver/bfs";
    }

    @ParameterizedTest
    @MethodSource("testArgs")
    void find_movements_sequence_to_the_exit(String inputFile, List<Direction> expected, GameStatus expectedStatus) throws Exception {
        setUp(inputFile);

        //Building the Model/Output Data Set
        List<Direction> directionsToExit = solver.nodesToExit();

        assertEquals(expected, directionsToExit);

        //Evaluation
        //If the list of moves was correctly generated, you should win the game
        SimulationResult result = room.executeMoveSequence(directionsToExit);

        assertEquals(expectedStatus, result.getGameStatus());

        System.out.println(directionsToExit);
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(
                        "room_exit_on_first_level.txt",
                        Collections.singletonList(UP),
                        WIN)
                ,
                Arguments.of(
                        "roomA.txt",
                        List.of(RIGHT,UP),
                        WIN)
        );
}
}
