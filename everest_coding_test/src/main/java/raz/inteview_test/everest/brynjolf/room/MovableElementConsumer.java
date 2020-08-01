package raz.inteview_test.everest.brynjolf.room;

import raz.inteview_test.everest.brynjolf.Direction;

//this class actually executes/implements the move logic
public class MovableElementConsumer implements MovementObserver {

    private final Element element;
    private final Element[][] roomMatrix;

    public MovableElementConsumer(Element element, Element[][] roomMatrix) {
        this.element = element;
        this.roomMatrix = roomMatrix;
    }

    /*
    Basic/base/common Movement logic
    * */
    @Override
    public void onMoveEvent(Direction direction) {
        //System.out.println(element.toString());
        if (!isActive())
            return;

        //1. Get the target/next position
        Element target = getNextPosition(direction);

        if (isValidTargetForMove(target)) {
            //As long as it is a BlankSpace we can perform the move
            //That means:
            //1. - transfer/transition the value to the next position/Element
            target.setValue(element.getValue());

            //2. - set the value of the `this`/current element to Empty Space
            element.setValue(ElementValue.EMPTY);

            //3. Because the target has been updated (and it has become an active MovableElement(e.g:Bryn/Guard)),
            // Propagate the move, chain reaction
            ((MovableElement) target).getMovementHandler().onMoveEvent(direction);
        } else {
            //The current movable element can't move
            //considering the Target(next position) which is given by the current direction
            //we can't move/advance
            //so just return for the current moveEvent
            return;
        }
    }

    private boolean isValidTargetForMove(Element target) {
        return target != null && !target.getValue().isStructural();
    }

    private Element getNextPosition(Direction direction) {
        int rowIdx = direction.rowIdxOffset == 0 ? element.rowIdx() : element.rowIdx() + direction.rowIdxOffset;
        int colIdx = direction.colIdxOffset == 0 ? element.colIdx() : element.colIdx() + direction.colIdxOffset;

        Element target = null;

        try {
            target = roomMatrix[rowIdx][colIdx];
        } catch (ArrayIndexOutOfBoundsException e) {
            //Ignored
        }

        return target;
    }

    //protected boolean canMoveInGivenDirection()

    //protected boolean canHandleDirection() - tinand cont de directie, are target? are ceva de executat?
    //se poate muta ceva?

    /**
     * Only Bryn or Guard can move, hence is active
     * the rest of Elements(e.g the Empty Space) is passive in movement
     */
    protected boolean isActive() {
        return (element.getValue() == ElementValue.BRYN || element.getValue() == ElementValue.GUARD);
    }
}
