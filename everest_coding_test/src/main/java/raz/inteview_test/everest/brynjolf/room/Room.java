package raz.inteview_test.everest.brynjolf.room;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {

    private static final short MAX_LEN = 10;

    private final Element[][] roomMatrix;

    private List<MovementObserver> movementObservers = new ArrayList<>();

    public Room(Element[][] matrix) {

        validateMatrix(matrix);

        this.roomMatrix = new Element[matrix.length][matrix.length];

        Element inputElem;
        Element roomElem;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                inputElem = matrix[i][j];
                if (inputElem.getValue().isStructural()) {
                    roomMatrix[i][j] = inputElem;
                } else {
                    //I don't like this...
                    //Better use composition and create a new Type which you add to the list of movementObservers
                    // you just need the `Element`
                    roomElem = new MotionSensitiveElement(inputElem, this.roomMatrix);
                    roomMatrix[i][j] = roomElem;
                    movementObservers.add((MovementObserver) roomElem);
                }
            }
        }
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
        for (Direction direction : moves) {
            movementObservers.stream()
                    .forEach(e -> e.onMoveEvent(direction));
        }
    }

    public Element[][] getRoomMatrix() {
        return roomMatrix;
    }

    public String prettyPrint() {
        return MatrixUtil.prettyPrint(roomMatrix);
    }
}
