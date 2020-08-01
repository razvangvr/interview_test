package raz.inteview_test.everest.brynjolf.room;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GameStatusRoomMovement extends RoomMovementTestBase {

    @Override
    protected String testFilesSubDir() {
        return "movement/game_scenarios";
    }

    @ParameterizedTest
    @MethodSource("test1Args")
    void test_different_game_scenarios(String inputFile, String outputFile, String movesStr) throws Exception {
        //Path filePath = testSubDir.resolve("bryn_up_left_up_right_initial.txt");
        Path filePath = testSubDir.resolve(inputFile);

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        room = new Room(roomMatrix);

        List<Direction> moves = Direction.parseString(movesStr);

        room.executeMoveSequence(moves);

        Element[][] expectedMatrix = matrixFileConverter.loadFromFile(testSubDir.resolve(outputFile));

        System.out.println(MatrixUtil.prettyPrint(room.getRoomMatrix()));

        assertArrayEquals(expectedMatrix, room.getRoomMatrix());
    }

    private static Stream<Arguments> test1Args() {
        return Stream.of(
                Arguments.of("bryn_up_left_up_right_initial.txt", "bryn_up_left_up_right_final.txt", "ulur")
//                Arguments.of("bryn_up_left_up_right_final.txt"),
//                Arguments.of("ulur")
        );
    }
}
