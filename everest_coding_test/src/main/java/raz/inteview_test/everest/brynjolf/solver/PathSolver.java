package raz.inteview_test.everest.brynjolf.solver;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.List;
import java.util.stream.Collectors;

public class PathSolver {

    private final Element[][] roomMatrix;

    /* Get from Bryn to Exit*/
    private Element bryn;
    private Element exit;

    public PathSolver(Element[][] roomMatrix) {
        this.roomMatrix = roomMatrix;

        locateBrynAndExit(roomMatrix);
    }

    private void locateBrynAndExit(Element[][] roomMatrix) {
        Element oneElem;
        for (int line = 0; line < roomMatrix.length; line++) {
            for (int col = 0; col < roomMatrix.length; col++) {
                oneElem = roomMatrix[line][col];
                if (oneElem.isBryn()) {
                    bryn = oneElem;
                }
                if (oneElem.isExit()) {
                    exit = oneElem;
                }
            }
        }
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

        //Step 1
        //Diferenta punctelor in plan cartezian
        PointsDelta delta = new PointsDelta(bryn, exit);

        return delta.exitIsToYour().stream().collect(Collectors.toList());
    }
}
