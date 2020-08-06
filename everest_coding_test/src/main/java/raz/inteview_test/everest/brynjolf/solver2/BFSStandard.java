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
public class BFSStandard {

    final Node[][] gameMatrix;
    private Node bryn;
    private Element exit;

    List<Element> pathToExit;

    Map<Element, Element> childToParent = new HashMap<>();

    public BFSStandard(Element[][] matrix) {
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

    public List<Element> nextPath() {
        checkIfPathExists_invalidate_it_and_go_to_next_path();
        //SolutionPath path1; set visited in context of a SolutionPath, dar daca mai vrei alt path... atunci nodurile din path-ul asta le marcam ca DeadEnd
        //ca wall si asta va forta algoritmul ca caute in remaining space
        Node exit = exitFound(bryn);
        if (exit != null) {
            //path.add(exit);

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

    public List<Direction> findPathDir() {
        if (pathToExit == null)
            pathToExit = nextPath();
        return buildDirections(pathToExit);
    }


    /**
     * root/parent - adica Source/Bryn
     */
    Node exitFound(Node root) {
        Set<Node> directNeighbours = root.nodeNeighbours();
        root.setVisited(true);
        //System.out.println("setting visited:"+root);
        if (isExitReached(directNeighbours)) {
            Node exit = getExit(directNeighbours);
            childToParent.put(exit, root);
            return exit;
        } else {
            for (Node oneNeighbour : directNeighbours) {
                childToParent.put(oneNeighbour, root);
                return exitFound(oneNeighbour);
            }
        }

//        if(root.exitReached() ) - daca din nodul asta ajung la Exit, ma opresc
//                adaug existul la Path, si nodul curent
//         //daca nu, evalueazi vecinii
//        //for(root.getNeighbours();
        //return false;

        return null;
    }

    boolean isExitReached(Set<Node> directNeighbours) {
        return directNeighbours
                .stream()
                .anyMatch(e -> e.isExit());
    }

    Node getExit(Set<Node> directNeighbours) {
        return directNeighbours
                .stream()
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
}
