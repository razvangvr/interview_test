package raz.inteview_test.everest.brynjolf;

public class SimulationResult {//this will be returned by room executeMoveSequence


    public static final SimulationResult EXIT_CANT_BE_REACHED = new SimulationResult(GameStatus.LOSE, 0);

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
