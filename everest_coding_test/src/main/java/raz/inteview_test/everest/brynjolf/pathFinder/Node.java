package raz.inteview_test.everest.brynjolf.pathFinder;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

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
            int rowIdx = direction.rowIdxOffset == 0 ? this.rowIdx() : this.rowIdx() + direction.rowIdxOffset;
            int colIdx = direction.colIdxOffset == 0 ? this.colIdx() : this.colIdx() + direction.colIdxOffset;

            try {
                neighbour = roomMatrix[rowIdx][colIdx];
                if (!neighbour.isWall())
                    neighbours.add(neighbour);
            } catch (ArrayIndexOutOfBoundsException e) {
                //Ignored
            }
        }
        return neighbours.stream().filter(e->!e.isVisited()).collect(Collectors.toSet());
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
