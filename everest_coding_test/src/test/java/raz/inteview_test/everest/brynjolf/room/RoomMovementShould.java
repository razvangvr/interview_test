package raz.inteview_test.everest.brynjolf.room;

import org.junit.jupiter.api.Test;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.util.MatrixFileConverter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RoomMovementShould {

    Path testSrcResources = Paths.get("src", "test", "resources");
    Path testSubDir = testSrcResources.resolve("movement");

    MatrixFileConverter matrixFileConverter = new MatrixFileConverter();

    Room room;


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

        assertArrayEquals(expectedMatrix, roomMatrix);
    }
}
