package raz.inteview_test.everest.brynjolf.room;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.GameStatus;
import raz.inteview_test.everest.brynjolf.SimulationResult;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStatusTest extends RoomMovementTestBase {

    @Override
    protected String testFilesSubDir() {
        return "movement/game_scenarios";
    }

    @ParameterizedTest
    @MethodSource("test1Args")
    void test_different_game_scenarios(String inputFile,
                                       String outputFile,
                                       String movesStr,
                                       int movesCount,
                                       GameStatus gameStatus) throws Exception {
        Path filePath = testSubDir.resolve(inputFile);

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        room = new Room(roomMatrix);

        List<Direction> moves = Direction.parseString(movesStr);

        SimulationResult result =  room.executeMoveSequence(moves);

        Element[][] expectedMatrix = matrixFileConverter.loadFromFile(testSubDir.resolve(outputFile));

        System.out.println(MatrixUtil.prettyPrint(room.getRoomMatrix()));

        assertArrayEquals(expectedMatrix, room.getRoomMatrix());

        assertEquals(gameStatus, result.getGameStatus());

        assertEquals(movesCount, result.getMovesCount());
    }

    private static Stream<Arguments> test1Args() {
        return Stream.of(
                Arguments.of("initial_undecided_6x_up.txt", "final_undecided_6x_up.txt", "uuuuuu", 4, GameStatus.UNDECIDED),//Testing the Undecided Status due to movesCount Limit exceeded
                Arguments.of("initial_2_guards.txt", "final_2_guards_right_win.txt", "r", 1, GameStatus.WIN),//Testing the Win Status, 1 Move
                Arguments.of("initial_2_guards.txt", "final_2_guards_up_left_caught.txt", "ul", 2, GameStatus.LOSE)//Testing the Lose/Caught Status, 1 Move
        );
    }
}
