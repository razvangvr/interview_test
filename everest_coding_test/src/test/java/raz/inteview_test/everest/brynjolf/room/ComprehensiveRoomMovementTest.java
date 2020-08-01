package raz.inteview_test.everest.brynjolf.room;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ComprehensiveRoomMovementTest extends RoomMovementTestBase {

    @Override
    protected String testFilesSubDir() {
        return "movement/guards";
    }

    @ParameterizedTest
    @MethodSource("test1Args")
    @DisplayName("Th")
    void test_all_movement_combinations(String inputFile, String outputFile, String movesStr) throws Exception {
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
                Arguments.of("initial_1_guard_following_bryn.txt", "final_1_guard_following_bryn.txt", "rurd"),
                Arguments.of("initial_2_guards.txt", "final_2_guards.txt", "ur"),/* Make sure that 2 Guards follow the same Direction*/
                Arguments.of("initial_2_guards_collide.txt", "final_2_guards_collide.txt", "ul"),/* Make sure that 2 Guards CAN'T Collide,*/
                Arguments.of("initial_guard_overlaps_into_bryn.txt", "final_guard_overlaps_into_bryn.txt", "ul"),/*A guard CAN Move into Bryn => Caught*/
                Arguments.of("initial_bryn_can_exit_room.txt", "final_bryn_can_exit_room.txt", "ul")/*Bryn CAN Move into exit, but the guard CAN NOT => Escape/Win*/
        );
    }
}
