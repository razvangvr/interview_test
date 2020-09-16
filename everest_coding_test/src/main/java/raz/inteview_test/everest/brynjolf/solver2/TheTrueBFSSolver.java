package raz.inteview_test.everest.brynjolf.solver2;

import org.javatuples.Pair;
import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.*;

/**
 * Breadth First Search
 * - genereaza toate Path-urile de la Source la Dest
 */
public class TheTrueBFSSolver extends SolverBase implements IPathSolver {

    //nu cred ca am nevoie de asta, ca clasa Node are notiunea de wasVisited
    //Set<Element> alreadyVisited = new HashSet<>();

    Queue<Node> nodesToVisit = new LinkedList<>();
    private Boolean hasSolution =null;
    public int nodesVisits = 0;


    public boolean hasSolution() {
        return hasSolution;
    }

    protected Map<Pair<Element, Integer>, Element> childParentLevelToParent = new HashMap<>();

    public TheTrueBFSSolver(Element[][] matrix) {
        super(matrix);
    }


    /**
     * @return returns null if I can find a path to exit
     */
    @Override
    public List<Element> nextPathToExit() {
        nodesToVisit.add(bryn);

        boolean exitFound = false;
        int graphLevel = 0;

        while (!nodesToVisit.isEmpty() && !exitFound) {
            exitFound = visitNode(nodesToVisit.remove(), graphLevel);
            graphLevel++;
        }
        hasSolution = exitFound;
        if (hasSolution) {
            pathToExit = reconstructPath(childParentLevelToParent);
            return pathToExit;
        } else
            return null;

    }

    /**
     * Visit Node,
     * - check if exit is within reach of its direct neighbours
     * - if not add its direct neighbours to the queue
     */
    private boolean visitNode(Node parent, int graphLevel) {
        nodesVisits++;
        System.out.println("visiting node:" + parent + " graphLevel of Parent :" + graphLevel);
        parent.setVisited(true);

        Set<DirectionNode> directNeighbours = parent.neighboursOnDirection();

        boolean exitFound = isExitReached(directNeighbours);
        if (exitFound) {
            foundExit = getExit(directNeighbours);
            childParentLevelToParent.put(new Pair(foundExit, graphLevel), parent);
        } else {
            for (DirectionNode oneNeighbour : directNeighbours) {
                childParentLevelToParent.put(new Pair(oneNeighbour.node(), graphLevel), parent);
                nodesToVisit.offer(oneNeighbour.node());
            }
        }
        return exitFound;
    }


    @Override
    public List<Direction> directionsToExit() {
        if (pathToExit == null)
            pathToExit = nextPathToExit();
        if (hasSolution)
            return buildDirections(pathToExit);
        else
            return null;
    }

    //mai am Path-uri? sau le-am epuizat pe toate?
    //boolean hasNext(){}
}
