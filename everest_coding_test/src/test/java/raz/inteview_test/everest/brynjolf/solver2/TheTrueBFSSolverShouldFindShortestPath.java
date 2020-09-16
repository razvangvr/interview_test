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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static raz.inteview_test.everest.brynjolf.Direction.*;
import static raz.inteview_test.everest.brynjolf.GameStatus.LOSE;
import static raz.inteview_test.everest.brynjolf.GameStatus.WIN;


class TheTrueBFSSolverShouldFindShortestPath extends RoomMovementTestBase {

    IPathSolver solver;

    @Override
    protected String testFilesSubDir() {
        return "solver/bfs2";
    }

    private void setUp(String inputFile) throws IOException {
        Path filePath = testSubDir.resolve(inputFile);
        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        solver = new TheTrueBFSSolver(roomMatrix);

        room = new Room(roomMatrix);
    }

    /*
    - directionsToExit >>[LEFT, UP, RIGHT]
    Expected :[RIGHT, UP, LEFT]
    Actual   :[LEFT, UP, RIGHT]
    * */
    @ParameterizedTest
    @MethodSource("testArgsBB")
    void generate_the_all_possible_paths(
            String inputFile,
            //List<Element> expected
            List<Direction> expectedDirections
            , GameStatus expectedStatus
    ) throws Exception {
        setUp(inputFile);

        Set<List<Element>> all_possible_paths = new HashSet<>();
        List<Element> nodesToExit;
        while ((nodesToExit = solver.nextPathToExit()) != null) {
            //System.out.println(nodesToExit);
            all_possible_paths.add(nodesToExit);
        }

        System.out.println(all_possible_paths);
    }

    private static Stream<Arguments> testArgsBB() {
        return Stream.of(
//                Arguments.of(
//                        "room_exit_on_first_level.txt",
//                        Collections.singletonList(UP)
//                        , WIN
//                )
//                ,
                Arguments.of(
                        "roomB.txt",
                        List.of(RIGHT, UP, LEFT)
                        , WIN
                )

        );
    }

    @ParameterizedTest
    @MethodSource("testArgs")
    void find_movements_sequence_to_the_exit(
            String inputFile,
            //List<Element> expected
            List<Direction> expectedDirections
            , GameStatus expectedStatus
    ) throws Exception {
        setUp(inputFile);

        List<Element> nodesToExit = solver.nextPathToExit();
        System.out.println("nodesToExit >>" + nodesToExit);

        List<Direction> directionsToExit = solver.directionsToExit();
        System.out.println("directionsToExit >>" + directionsToExit);

        assertEquals(expectedDirections, directionsToExit);


        //Evaluation
        //If the list of moves was correctly generated, you should win the game
        SimulationResult result = solver.hasSolution() ? room.executeMoveSequence(directionsToExit) : SimulationResult.EXIT_CANT_BE_REACHED;
        System.out.println(result.getGameStatus());
//
        assertEquals(expectedStatus, result.getGameStatus());

    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of(
                        "room_exit_on_first_level.txt",
                        Collections.singletonList(UP)
                        , WIN
                )
                ,
                Arguments.of(
                        "roomA.txt",
                        List.of(RIGHT, UP)
                        , WIN
                )
                ,
                /*
                ATTN, this TestCase has 2 solutions, which are equal as MovesCount and Directional Moves:
                visiting node:[2][1] b graphLevel of Parent :0
                And sometimes, I get one Path, another times I get the other
------------------------------------------------------------------------
visiting node:[3][1] . graphLevel of Parent :1
visiting node:[2][0] . graphLevel of Parent :2
visiting node:[2][3] . graphLevel of Parent :3
visiting node:[3][3] . graphLevel of Parent :4
visiting node:[3][0] . graphLevel of Parent :5
visiting node:[2][3] . graphLevel of Parent :6
visiting node:[0][0] . graphLevel of Parent :7
nodesToExit >>[[2][1] b, [2][0] ., [0][0] ., [0][2] e]
from:[2][1] b to:[2][0] .
from:[2][0] . to:[0][0] .
from:[0][0] . to:[0][2] e
directionsToExit >>[LEFT, UP, RIGHT]


org.opentest4j.AssertionFailedError:
Expected :[RIGHT, UP, LEFT]
Actual   :[LEFT, UP, RIGHT]
------------------------------------------------------------------------
visiting node:[2][1] b graphLevel of Parent :0
visiting node:[2][3] . graphLevel of Parent :1
visiting node:[2][0] . graphLevel of Parent :2
visiting node:[3][1] . graphLevel of Parent :3
visiting node:[2][0] . graphLevel of Parent :4
visiting node:[3][3] . graphLevel of Parent :5
visiting node:[0][3] . graphLevel of Parent :6
nodesToExit >>[[2][1] b, [2][3] ., [0][3] ., [0][2] e]
from:[2][1] b to:[2][3] .
from:[2][3] . to:[0][3] .
from:[0][3] . to:[0][2] e
directionsToExit >>[RIGHT, UP, LEFT]
WIN
                * */
                Arguments.of(
                        "roomB.txt",
                        List.of(RIGHT, UP, LEFT)
                        , WIN
                )
                ,
                Arguments.of(
                        "room_solution_unreachable.txt",
                        null
                        , LOSE
                )
        );
    }

}
