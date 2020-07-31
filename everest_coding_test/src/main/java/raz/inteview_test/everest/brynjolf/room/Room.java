package raz.inteview_test.everest.brynjolf.room;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.util.List;
import java.util.Objects;

public class Room {

    private static final short MAX_LEN = 10;

    private final Element[][] roomMatrix;

    public Room(Element[][] roomMatrix) {

        validateMatrix(roomMatrix);

        this.roomMatrix = roomMatrix;
    }

    private void validateMatrix(Element[][] roomMatrix) {
        Objects.requireNonNull(roomMatrix);
        int matrixArrays = roomMatrix.length;
        int oneArrayLen = roomMatrix[0].length;
        if (matrixArrays > MAX_LEN || oneArrayLen > MAX_LEN)
            throw new IllegalArgumentException("Room width will never exceed 10");
        if (matrixArrays != oneArrayLen)
            throw new IllegalArgumentException("A room is always a square");
    }

    public void executeMoveSequence(List<Direction> moves) {
    }

    public String prettyPrint() {
        return MatrixUtil.prettyPrint(roomMatrix);
    }
}
