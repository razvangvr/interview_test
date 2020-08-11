package raz.inteview_test.everest.brynjolf.solver2;

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
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static raz.inteview_test.everest.brynjolf.Direction.RIGHT;
import static raz.inteview_test.everest.brynjolf.Direction.UP;
import static raz.inteview_test.everest.brynjolf.GameStatus.WIN;

class BFSStandard2Test  extends RoomMovementTestBase {

    BFSStandard2 solver;

    @Override
    protected String testFilesSubDir() {
        return "solver/bfs2";
    }

    private void setUp(String inputFile) throws IOException {
        Path filePath = testSubDir.resolve(inputFile);
        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        solver = new BFSStandard2(roomMatrix);

        room = new Room(roomMatrix);
    }

    @ParameterizedTest
    @MethodSource("testArgs")
    void find_movements_sequence_to_the_exit(String inputFile,
                                             //List<Element> expected
                                             List<Direction> expected
            , GameStatus expectedStatus
    ) throws Exception {
        setUp(inputFile);

        List<Element> nodesToExit = solver.nextPath();
        System.out.println("nodesToExit >>" + nodesToExit);

        List<Direction> directionsToExit = solver.directionsToExit();
        System.out.println("directionsToExit >>" + directionsToExit);

        //assertEquals(expected, directionsToExit);


        //Evaluation
        //If the list of moves was correctly generated, you should win the game
        SimulationResult result = room.executeMoveSequence(directionsToExit);
        System.out.println(result.getGameStatus());
//
        //assertEquals(expectedStatus, result.getGameStatus());

    }


    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(
                        "room_exit_on_first_level.txt",
                        Collections.singletonList(UP)
                        , WIN
                )
//                ,
//                Arguments.of(
//                        "roomA.txt",
//                        List.of(RIGHT, UP)
//                        , WIN
//                )
//                ,
//                Arguments.of(
//                        "roomB.txt",
//                        List.of(RIGHT, UP)
//                        , WIN
//                )
//                ,
//                Arguments.of(
//                        "room_solution_unreachable.txt",
//                        List.of(RIGHT, UP)
//                        , WIN
//                )
        );
    }
}
