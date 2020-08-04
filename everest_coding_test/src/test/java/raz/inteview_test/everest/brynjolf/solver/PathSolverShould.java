package raz.inteview_test.everest.brynjolf.solver;

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

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathSolverShould extends RoomMovementTestBase {

    PathSolver pathSolver;

    Room room;

    @Override
    protected String testFilesSubDir() {
        return "solver";
    }

    @ParameterizedTest
    @MethodSource("testArgs")
    void find_movements_sequence(String inputFile) throws Exception{
        Path filePath = testSubDir.resolve(inputFile);
        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        pathSolver = new PathSolver(roomMatrix);
        room = new Room(roomMatrix);

        //Building the Model/Output Data Set
        List<Direction> movesSeq = pathSolver.findPath();//Collections.singletonList(Direction.RIGHT);

        assertEquals(Direction.RIGHT, movesSeq.get(0));


        //Evaluation
        //If the list of moves was correctly generated, you should win
        SimulationResult result = room.executeMoveSequence(movesSeq);

        assertEquals(GameStatus.WIN, result.getGameStatus());
    }

    private static Stream<Arguments> testArgs() {
        return Stream.of(
                Arguments.of("initial_go_right.txt")
        );
    }
}
