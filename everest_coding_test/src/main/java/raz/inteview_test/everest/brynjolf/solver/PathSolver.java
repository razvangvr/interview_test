package raz.inteview_test.everest.brynjolf.solver;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.List;

public class PathSolver {

    private final Element[][] roomMatrix;

    public PathSolver(Element[][] roomMatrix) {
        this.roomMatrix = roomMatrix;
    }

    /*
    Chiar daca 2 PathSolvers returneaza ordinea/prioaritatea
    Moves in functie de pe care le executa primele oX/oY

    Ei sunt echivalenti/egali atata timp cat
    - the List<Direction> contain the same elements (but in different order)
    (deci daca ai 2 liste cu aceleasi elemente dar in diferent order, all you have to do is sort them!)
    - for a given input, the output is the same
    * */
    public List<Direction> findPath() {
        return null;
    }
}
