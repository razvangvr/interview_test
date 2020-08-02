package raz.inteview_test.everest.brynjolf.room;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.GameStatus;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {

    private static final short MAX_LEN = 10;
    private static final short MAX_MOVES = 4;

    private final Element[][] roomMatrix;

    private List<MovementObserver> movementObservers = new ArrayList<>();

    private GameStatus gameStatus;

    private int movesCount = 0;

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
                    roomElem = new MovableElement(inputElem, this.roomMatrix);
                    roomMatrix[i][j] = roomElem;
                    movementObservers.add(
                            ((MovableElement) roomElem).getMovementHandler()
                    );
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
        for (int i = 0; i < moves.size(); i++) {
            ++movesCount;
            Direction direction = moves.get(i);
            movementObservers.stream()
                    .forEach(e -> e.onMoveEvent(direction));
            System.out.println("current move number:" + movesCount + " executed move:" + direction);
        }
    }

    public Element[][] getRoomMatrix() {
        return roomMatrix;
    }

    private void setGameStatus(GameStatus status) {
        if (gameStatus != null)
            throw new IllegalStateException("We already have a status:" + gameStatus + " a game status should be set only once. not on cumulative conditions");

        this.gameStatus = status;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public String prettyPrint() {
        return MatrixUtil.prettyPrint(roomMatrix);
    }
}
