package raz.inteview_test.everest.brynjolf.room;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.GameStatus;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStatusRoomMovement extends RoomMovementTestBase {

    @Override
    protected String testFilesSubDir() {
        return "movement/game_scenarios";
    }

    @ParameterizedTest
    @MethodSource("test1Args")
    void test_different_game_scenarios(String inputFile,
                                       String outputFile,
                                       String movesStr,
                                       int movesCount) throws Exception {
        //Path filePath = testSubDir.resolve("bryn_up_left_up_right_initial.txt");
        Path filePath = testSubDir.resolve(inputFile);

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        room = new Room(roomMatrix);

        List<Direction> moves = Direction.parseString(movesStr);

        room.executeMoveSequence(moves);

        Element[][] expectedMatrix = matrixFileConverter.loadFromFile(testSubDir.resolve(outputFile));

        System.out.println(MatrixUtil.prettyPrint(room.getRoomMatrix()));

        assertArrayEquals(expectedMatrix, room.getRoomMatrix());

        //assertEquals(GameStatus.UNDECIDED, room.getGameStatus());

        assertEquals(movesCount, room.getMovesCount());
    }

    private static Stream<Arguments> test1Args() {
        return Stream.of(
                //Arguments.of("initial_undecided_6x_up.txt", "final_undecided_6x_up.txt", "uuuuuu"),//Testing the Undecided Status due to movesCount Limit exceeded
                Arguments.of("initial_2_guards.txt", "final_2_guards_right_win.txt", "r", 1)//Testing the Win Status, 1 Move
        );
    }
}
