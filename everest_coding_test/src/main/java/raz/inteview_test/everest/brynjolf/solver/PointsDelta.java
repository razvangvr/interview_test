package raz.inteview_test.everest.brynjolf.solver;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.room.Element;

import java.util.HashSet;
import java.util.Set;

import static raz.inteview_test.everest.brynjolf.Direction.*;

public class PointsDelta {

    final Element bryn;
    final Element exit;

    final int lineDelta;
    final int colDelta;

    public PointsDelta(Element bryn, Element exit) {
        this.bryn = bryn;

        lineDelta = exit.rowIdx() - bryn.rowIdx();
        colDelta = exit.colIdx() - bryn.colIdx();

        this.exit = exit;
    }

    public Set<Direction> exitIsToYour() {
        Direction lineMovement = null;
        Direction colMovement = null;
        if (lineDelta == 0) {
            //we move only RIGHT or LEFT, on the "columns axis"
            lineMovement = colDelta > 0 ? RIGHT : LEFT;
        } else if (colDelta == 0) {
            //we move only UP or DOWN, on the "lines axis"
            colMovement = lineDelta > 0 ? DOWN : UP;
        } else {
            //we move in both directions
            lineMovement = colDelta > 0 ? RIGHT : LEFT;
            colMovement = lineDelta > 0 ? DOWN : UP;
        }
        Set<Direction> directionsToExit = new HashSet<>();
        if (lineMovement != null)
            directionsToExit.add(lineMovement);
        if (colMovement != null)
            directionsToExit.add(colMovement);
        return directionsToExit;
    }

    @Override
    public String toString() {
        return "PointsDelta{" +
                "lineDelta=" + lineDelta +
                ", colDelta=" + colDelta +
                '}';
    }
}
