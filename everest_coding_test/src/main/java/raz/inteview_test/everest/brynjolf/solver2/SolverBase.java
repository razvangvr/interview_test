package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.pathFinder.Node;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolverBase {

    final Node[][] gameMatrix;
    protected Node bryn;
    protected Element exit;

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
}
