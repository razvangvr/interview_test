package raz.inteview_test.everest.brynjolf;

public enum GameStatus {
    /*Bryn has Exited*/
    WIN,
    /*Bryn was Caught*/
    LOSE,
    /*undecided/due to movesCount Limit exceeded 4 moves - Phase1*/
    /*and maybe in Phase2 - "Sometimes there's no way to win after playing a wrong move" */
    UNDECIDED
}
