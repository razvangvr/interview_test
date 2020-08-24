package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.ElementValue;
import raz.inteview_test.everest.brynjolf.solver.PathSolver;
import raz.inteview_test.everest.brynjolf.solver.PointsDelta;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.util.*;

/**
 * Acum am inteles de ce BFS gaseste ShortestPath,
 * - si de ce nu conteaza cu ce vecin incepi,
 * pt ca pt fiecare nod vecin ii evalueaza vecinii imediati, si daca nu ajungi la Exit,
 * evalueaza celalat vecin(nod) pe ce acelasi nivel
 */
public class BFSStandard2 extends SolverBase implements IPathSolver {

    public BFSStandard2(Element[][] matrix) {
        super(matrix);
    }

    public Node getBryn() {
        return bryn;
    }


    public List<Element> nextPathToExit() {
        foundExit = exitFound(bryn);
        if (exit != null) {
            pathToExit = reconstructPath();
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
            pathToExit = nextPathToExit();
        return buildDirections(pathToExit);
    }

    @Override
    public boolean hasSolution() {
        if (pathToExit == null)
            return nextPathToExit() != null;
        else return true;
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


}
