package raz.inteview_test.everest.brynjolf.solver;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PathSolver {

    private final Element[][] roomMatrix;

    /* Get from Bryn to Exit*/
    private Element bryn;
    private Element exit;

    public PathSolver(Element[][] roomMatrix) {
        this.roomMatrix = roomMatrix;
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
    (deci daca ai 2 liste cu aceleasi elemente dar in diferent order, all you have to do is sort them based on priority(executam prima data pe oX sau oY?)
    - for a given input, the output is the same
    * */
    public List<Direction> findPath() {

        //Step 0 - locate Bryn and Exit
        locateBrynAndExit(roomMatrix);

        //Step 1 - Get the Directions in Absolute/Vacuum - as if there were NO Obstacles(no walls) and we could go straight to the exit
        //Diferenta punctelor in plan cartezian
        PointsDelta delta = new PointsDelta(bryn, exit);

        Set<Direction> directionsToExit_in_vacuum = delta.exitIsToYour();

        //In Vacuum, if we have to do 2 moves, it doesn't matter the order in which you execute them, the length of the path is always the same.

        return directionsToExit_in_vacuum.stream().collect(Collectors.toList());
    }
}
