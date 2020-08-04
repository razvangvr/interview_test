package raz.inteview_test.everest.brynjolf.solver;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.*;
import java.util.stream.Collectors;

public class BFSSolver {

    private final Element[][] roomMatrix;

    Queue<Element> nodesToVisit = new ArrayDeque<>();
    Map<Element, Element> childToParent = new HashMap<>();

    /* Get from Bryn to Exit*/
    private Element bryn;
    private Element exit;

    private Element foundExit;

    List<Element> nodesFromBrynToExit;

    Set<Element> alreadyVisited = new HashSet<>();

    public BFSSolver(Element[][] roomMatrix) {
        this.roomMatrix = roomMatrix;
        locateBrynAndExit();
    }

    private void locateBrynAndExit() {
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

    public List<Direction> nodesToExit() {
        nodesToVisit.add(bryn);

        boolean exitFound = false;
        while (!nodesToVisit.isEmpty() && !exitFound) {
            exitFound = checkExitIsWithInReach(nodesToVisit.remove());
        }

        nodesFromBrynToExit = reconstructPath();

        return buildDirections();
    }

    private List<Direction> buildDirections() {
        List<Direction> directions = new ArrayList<>();
        Element start;
        Iterator<Element> pathIter = nodesFromBrynToExit.iterator();
        boolean isNextElem = true;
        while (pathIter.hasNext() && isNextElem) {
            start = pathIter.next();
            isNextElem = pathIter.hasNext();
            PointsDelta delta = new PointsDelta(start, pathIter.next());
            //pathIter.remove();

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

    private List<Element> reconstructPath() {
        List<Element> path = null;
        Stack<Element> stack = new Stack<>();

        if (foundExit != null) {
            path = new ArrayList<>();
            Element current = foundExit;
            stack.push(current);
            do {
                current = childToParent.get(current);
                if (current != null) {
                    stack.push(current);
                }
            } while (current != null);

            while (!stack.empty()) {
                path.add(stack.pop());
            }
        }
        return path;
    }


    private boolean checkExitIsWithInReach(Element node) {
        alreadyVisited.add(node);
        Set<Element> neighbours = Direction.nodeNeighbours(this.roomMatrix, node);
        neighbours = neighbours
                .stream()
                .filter(e -> !alreadyVisited.contains(e))
                .collect(Collectors.toSet());

        Optional<Element> exitFound = neighbours
                .stream()
                .filter(e -> e.isExit())
                .findFirst();

        if (exitFound.isPresent()) {
            foundExit = exitFound.get();
            childToParent.put(exitFound.get(), node);
            return true;
        } else {
            //nodesToVisit.addAll(neighbours);
            for (Element neighbour : neighbours) {
                nodesToVisit.add(neighbour);
                childToParent.put(neighbour, node);
            }
            return false;
        }
    }
}
