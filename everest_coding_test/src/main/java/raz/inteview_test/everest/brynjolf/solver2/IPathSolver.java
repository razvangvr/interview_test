package raz.inteview_test.everest.brynjolf.solver2;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.List;

public interface IPathSolver {

    List<Element> nextPathToExit();

    List<Direction> directionsToExit();

    boolean hasSolution();
}
