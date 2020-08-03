package raz.inteview_test.everest.brynjolf.movement;

import raz.inteview_test.everest.brynjolf.room.Element;

public class MovementCondition {
    final Element currentPosition;
    final Element nextPosition;

    public MovementCondition(Element currentPosition, Element nextPosition) {
        this.currentPosition = currentPosition;
        this.nextPosition = nextPosition;
    }

    public boolean isMoveIntoTargetAllowed() {

        if (nextPosition.isEmpty())
            //Anyone can always move into a Empty Space
            return true;

        if (currentPosition.isGuard() && nextPosition.isBryn())
            //a guard can move into a bryn
            return true;

        if (currentPosition.isBryn() && nextPosition.isExit())
            //Bryn can move into a Exit
            return true;

        if (currentPosition.isGuard() && nextPosition.isExit())
            //a Guard can not move into Exit
            return false;

        if (currentPosition.isGuard() && nextPosition.isGuard())
            //a guard can not move into a guard
            return false;

        return false;
    }
}
