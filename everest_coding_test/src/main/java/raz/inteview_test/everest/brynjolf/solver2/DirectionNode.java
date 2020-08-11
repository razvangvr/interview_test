package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.pathFinder.Node;

/**
 * It decorates a node with direction
 * */
public class DirectionNode {

    final Node delegate;
    final Direction direction;

    public DirectionNode(Node delegate, Direction dir) {
        this.delegate = delegate;
        this.direction = dir;
    }

    public Node node() {
        return delegate;
    }

    public Direction direction() {
        return direction;
    }

    @Override
    public String toString() {
        return "DirectionNode{" +
                "Node=" + delegate +
                ", Direction=" + direction +
                '}';
    }
}
