package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.solver.PointsDelta;

import java.util.*;

public class SolverBase {

    final Node[][] gameMatrix;
    protected Node bryn;
    protected Element exit;

    protected Element foundExit;

    protected List<Element> pathToExit;

    protected Map<Element, Element> childToParent = new HashMap<>();

    public SolverBase(Element[][] matrix) {
        gameMatrix = new Node[matrix.length][matrix.length];
        initLocateBrynAndExit(matrix);
    }

    private void initLocateBrynAndExit(Element[][] matrix) {
        Element oneElem;
        Node node;
        for (int line = 0; line < matrix.length; line++) {
            for (int col = 0; col < matrix.length; col++) {
                oneElem = matrix[line][col];
                node = new Node(oneElem, gameMatrix);
                gameMatrix[line][col] = node;
                if (oneElem.isBryn()) {
                    bryn = gameMatrix[line][col];
                }
                if (oneElem.isExit()) {
                    exit = oneElem;
                }
            }
        }
    }

    protected boolean isExitReached(Set<DirectionNode> directNeighbours) {
        return directNeighbours
                .stream()
                .map(e -> e.node())
                .anyMatch(e -> e.isExit());
    }

    protected Node getExit(Set<DirectionNode> directNeighbours) {
        return directNeighbours
                .stream()
                .map(e -> e.node())
                .filter(e -> e.isExit())
                .findFirst()
                .get();
    }

    protected List<Element> reconstructPath() {
        List<Element> path = null;
        Stack<Element> stack = new Stack<>();

        if (foundExit != null) {
            path = new ArrayList<>();
            Element parent = foundExit;
            stack.push(parent);
            boolean sourceReached = false;
            do {
                parent = childToParent.get(parent);
                sourceReached = parent.isBryn();
                if (parent != null) {
                    stack.push(parent);
                }
            } while (/*parent != null*/!sourceReached);

            while (!stack.empty()) {
                path.add(stack.pop());
            }
        }
        return path;
    }

    protected List<Direction> buildDirections(List<Element> nodesFromBrynToExit) {
        List<Direction> directions = new ArrayList<>();

        Iterator<Element> pathIter = nodesFromBrynToExit.iterator();
        Element from = pathIter.next();
        Element to = null;
        boolean isNextElem = true;
        while (pathIter.hasNext()) {
            to = pathIter.next();

            System.out.println("from:" + from + " to:" + to);
            PointsDelta delta = new PointsDelta(from, to);
            //pathIter.remove();

            from = to;
            to = null;

            Set<Direction> directionBetween2Neighbours = delta.exitIsToYour();
            if (directionBetween2Neighbours.size() != 1)
                throw new IllegalArgumentException("Only 1 dire between 2 adjacent neighbours");
            Direction currentDir = directionBetween2Neighbours.stream().findFirst().get();
            if (directions.isEmpty() || !directions.get(directions.size() - 1).equals(currentDir)) {
                directions.add(currentDir);
            }
        }
        return directions;
    }
}
