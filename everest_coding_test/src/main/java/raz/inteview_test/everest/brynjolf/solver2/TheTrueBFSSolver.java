package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Breadth First Search
 * - genereaza toate Path-urile de la Source la Dest
 * */
public class TheTrueBFSSolver extends SolverBase implements IPathSolver{

    //nu cred ca am nevoie de asta, ca clasa Node are notiunea de wasVisited
    //Set<Element> alreadyVisited = new HashSet<>();

    Queue<Element> nodesToVisit = new LinkedList<>();

    public TheTrueBFSSolver(Element[][] matrix) {
        super(matrix);
    }


    @Override
    public List<Element> nextPathToExit() {
        return null;
    }

    @Override
    public List<Direction> directionsToExit() {
        return null;
    }

    //mai am Path-uri? sau le-am epuizat pe toate?
    //boolean hasNext(){}
}
