package raz.inteview_test.everest.brynjolf.room;

import org.junit.jupiter.api.Test;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RoomMovementShould extends RoomMovementTestBase {

    protected String testFilesSubDir() {
        return "movement";
    }

    @Test
    void bryn_on_south_edge_move_down() throws IOException {
        Path filePath = testSubDir.resolve("bryn_up_initial.txt");

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        room = new Room(roomMatrix);

        room.executeMoveSequence(Collections.singletonList(Direction.DOWN));

        //We're on the edge, and we have nowhere to go, so input and output should be equal
        assertArrayEquals(room.getRoomMatrix(), roomMatrix);
    }

    @Test
    void move_bryn_up() throws IOException {
        Path filePath = testSubDir.resolve("bryn_up_initial.txt");

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        room = new Room(roomMatrix);

        room.executeMoveSequence(Collections.singletonList(Direction.UP));

        Element[][] expectedMatrix = matrixFileConverter.loadFromFile(testSubDir.resolve("bryn_up_final.txt"));

        assertArrayEquals(expectedMatrix, room.getRoomMatrix());

        System.out.println(MatrixUtil.prettyPrint(room.getRoomMatrix()));
    }

    @Test
    void move_bryn_up_right() throws IOException {
        Path filePath = testSubDir.resolve("bryn_up_initial.txt");

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        room = new Room(roomMatrix);

        room.executeMoveSequence(List.of(Direction.UP, Direction.RIGHT));

        Element[][] expectedMatrix = matrixFileConverter.loadFromFile(testSubDir.resolve("bryn_up_right_final.txt"));

        assertArrayEquals(expectedMatrix, room.getRoomMatrix());

        System.out.println(MatrixUtil.prettyPrint(room.getRoomMatrix()));
    }
}
