package raz.inteview_test.everest.brynjolf.movement;

import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.ElementValue;

public class MovementCondition {
    final Element currentPosition;
    final Element nextPosition;

    public MovementCondition(Element currentPosition, Element nextPosition) {
        this.currentPosition = currentPosition;
        this.nextPosition = nextPosition;
    }

    public boolean isMoveIntoTargetAllowed() {

        if (nextPosition.getValue() == ElementValue.EMPTY)
            //Anyone can always move into a Empty Space
            return true;

        if (currentPosition.getValue() == ElementValue.GUARD && nextPosition.getValue() == ElementValue.BRYN)
            //a guard can move into a bryn
            return true;

        if (currentPosition.getValue() == ElementValue.BRYN && nextPosition.getValue() == ElementValue.EXIT)
            //Bryn can move into a Exit
            return true;

        if (currentPosition.getValue() == ElementValue.GUARD && nextPosition.getValue() == ElementValue.EXIT)
            //a Guard can not move into Exit
            return false;

        if (currentPosition.getValue() == ElementValue.GUARD && nextPosition.getValue() == ElementValue.GUARD)
            //a guard can not move into a guard
            return false;

        return false;
    }
}
