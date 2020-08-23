package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth First Search
 * - genereaza toate Path-urile de la Source la Dest
 */
public class TheTrueBFSSolver extends SolverBase implements IPathSolver {

    //nu cred ca am nevoie de asta, ca clasa Node are notiunea de wasVisited
    //Set<Element> alreadyVisited = new HashSet<>();

    Queue<Node> nodesToVisit = new LinkedList<>();

    public int nodesVisits = 0;

    public TheTrueBFSSolver(Element[][] matrix) {
        super(matrix);
    }


    @Override
    public List<Element> nextPathToExit() {
        nodesToVisit.add(bryn);

        boolean exitFound = false;

        while (!nodesToVisit.isEmpty() && !exitFound) {
            exitFound = visitNode(nodesToVisit.remove());
        }
        pathToExit = reconstructPath();

        return pathToExit;
    }

    /**
     * Visit Node,
     * - check if exit is within reach of its direct neighbours
     * - if not add its direct neighbours to the queue
     */
    private boolean visitNode(Node parent) {
        nodesVisits++;
        System.out.println("visiting node:" + parent);
        parent.setVisited(true);

        Set<DirectionNode> directNeighbours = parent.neighboursOnDirection();

        boolean exitFound = isExitReached(directNeighbours);
        if (exitFound) {
            foundExit = getExit(directNeighbours);
            childToParent.put(foundExit, parent);
        } else {
            for (DirectionNode oneNeighbour : directNeighbours) {
                childToParent.put(oneNeighbour.node(), parent);
                nodesToVisit.offer(oneNeighbour.node());
            }
        }
        return exitFound;
    }


    @Override
    public List<Direction> directionsToExit() {
        if (pathToExit == null)
            pathToExit = nextPathToExit();
        return buildDirections(pathToExit);
    }

    //mai am Path-uri? sau le-am epuizat pe toate?
    //boolean hasNext(){}
}
