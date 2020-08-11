package raz.inteview_test.everest.brynjolf.pathFinder;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.solver2.DirectionNode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Node extends Element {

    final Node[][] roomMatrix;

    boolean visited;

    public Node(Element element, Node[][] roomMatrix) {
        super(element.getValue(), element.rowIdx(), element.colIdx());
        this.roomMatrix = roomMatrix;
    }

    /**
     * Get unVisited Neighbours of this/current node
     */
    public Set<Node> nodeNeighbours() {
        Set<Node> neighbours = new HashSet<>();
        Node neighbour = null;
        for (Direction direction : Direction.values()) {
            neighbour = nextNodeInDirection(this, direction);
            if (neighbour != null)
                neighbours.add(neighbour);
        }
        return neighbours.stream().filter(e -> !e.isVisited()).collect(Collectors.toSet());
    }

    /**
     * Might return Null if there is no next neighbour
     */
    private Node nextNodeInDirection(Node current, Direction direction) {
        Node neighbour = null;
        Node node;
        int rowIdx = direction.rowIdxOffset == 0 ? current.rowIdx() : current.rowIdx() + direction.rowIdxOffset;
        int colIdx = direction.colIdxOffset == 0 ? current.colIdx() : current.colIdx() + direction.colIdxOffset;

        try {
            node = roomMatrix[rowIdx][colIdx];
            if (!node.isWall() && !node.isGuard()) {
                neighbour = node;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ignored
        }
        return neighbour;
    }

    /**
     * Might return Null if there is no next neighbour
     */
    private Node getLastNodeInDirection(Direction direction) {
        Node nextNeighbour = nextNodeInDirection(this, direction);
        if (nextNeighbour != null) {
            if (nextNeighbour.isExit())
                return nextNeighbour;
            while (nextNodeInDirection(nextNeighbour, direction) != null) {
                nextNeighbour = nextNodeInDirection(nextNeighbour, direction);
                if (nextNeighbour.isExit())
                    return nextNeighbour;
            }
        }
        return nextNeighbour;
    }

    /**
     * Returneaza vecinii pe toate directiile nodului current,
     * - daca intalneste un zid, intoarce nodul dinaintea zidului(daca exita)
     * - daca intalneste Exit intoarce exitul
     * - daca intalneste un guard, pe directie exclude Directia
     * - Pt ca nu vrei sa intri in Guard, si deci nu are sens sa te duci in directia aia ca pierzi
     */
    public Set<DirectionNode> neighboursOnDirection() {
        Set<DirectionNode> neighboursInDirections = new HashSet<>();
        for (Direction direction : Direction.values()) {
            Node lastInDirection = getLastNodeInDirection(direction);
            if (lastInDirection != null && !lastInDirection.isVisited())
                neighboursInDirections.add(new DirectionNode(lastInDirection, direction));
        }
        return neighboursInDirections;
    }

    public Element[][] getRoomMatrix() {
        return roomMatrix;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
