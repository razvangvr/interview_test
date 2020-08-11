package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.ElementValue;
import raz.inteview_test.everest.brynjolf.solver.PointsDelta;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.util.*;

/**
 * Acum am inteles de ce BFS gaseste ShortestPath,
 * - si de ce nu conteaza cu ce vecin incepi,
 * pt ca pt fiecare nod vecin ii evalueaza vecinii imediati, si daca nu ajungi la Exit,
 * evalueaza celalat vecin(nod) pe ce acelasi nivel
 */
public class BFSStandard2 {

    final Node[][] gameMatrix;
    private Node bryn;
    private Element exit;

    List<Element> pathToExit;

    Map<Element, Element> childToParent = new HashMap<>();

    public BFSStandard2(Element[][] matrix) {
        gameMatrix = new Node[matrix.length][matrix.length];
        initLocateBrynAndExit(matrix);
    }

    public Node getBryn() {
        return bryn;
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

    public List<Element> nextPath() {
        Node exit = exitFound(bryn);
        if (exit != null) {
            pathToExit = reconstructPath(exit);
        }
        return pathToExit;
    }

    private void checkIfPathExists_invalidate_it_and_go_to_next_path() {
        if (pathToExit != null) {
            pathToExit.remove(bryn);
            pathToExit.remove(exit);
            for (Element node : pathToExit) {
                node.setValue(ElementValue.WALL);
            }
            pathToExit = null;
            MatrixUtil.showMatrix(gameMatrix);
        }
    }

    public List<Direction> directionsToExit() {
        if (pathToExit == null)
            pathToExit = nextPath();
        return buildDirections(pathToExit);
    }


    /**
     * root/parent - adica Source/Bryn
     */
    Node exitFound(Node root) {
        Set<DirectionNode> directNeighbours = root.neighboursOnDirection();
        root.setVisited(true);
        //System.out.println("setting visited:"+root);
        if (isExitReached(directNeighbours)) {
            Node exit = getExit(directNeighbours);
            childToParent.put(exit, root);
            return exit;
        } else {
            for (DirectionNode oneNeighbour : directNeighbours) {
                childToParent.put(oneNeighbour.node(), root);
                return exitFound(oneNeighbour.node());
            }
        }

//        if(root.exitReached() ) - daca din nodul asta ajung la Exit, ma opresc
//                adaug existul la Path, si nodul curent
//         //daca nu, evalueazi vecinii
//        //for(root.getNeighbours();
        //return false;

        return null;
    }

    boolean isExitReached(Set<DirectionNode> directNeighbours) {
        return directNeighbours
                .stream()
                .map(e->e.node())
                .anyMatch(e -> e.isExit());
    }

    Node getExit(Set<DirectionNode> directNeighbours) {
        return directNeighbours
                .stream()
                .map(e->e.node())
                .filter(e -> e.isExit())
                .findFirst()
                .get();
    }

    private List<Element> reconstructPath(Element foundExit) {
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

    private List<Direction> buildDirections(List<Element> nodesFromBrynToExit) {
        List<Direction> directions = new ArrayList<>();

        Iterator<Element> pathIter = nodesFromBrynToExit.iterator();
        Element from = pathIter.next();
        Element to = null;
        boolean isNextElem = true;
        while (pathIter.hasNext()) {
            to = pathIter.next();

            System.out.println("from:"+from+" to:"+to);
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
