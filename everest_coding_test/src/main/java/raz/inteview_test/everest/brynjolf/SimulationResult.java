package raz.inteview_test.everest.brynjolf;

public class SimulationResult {//this will be returned by room executeMoveSequence

    final GameStatus gameStatus;
    final int movesCount;

    public SimulationResult(GameStatus gameStatus, int movesCount) {
        this.gameStatus = gameStatus;
        this.movesCount = movesCount;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getMovesCount() {
        return movesCount;
    }
}
