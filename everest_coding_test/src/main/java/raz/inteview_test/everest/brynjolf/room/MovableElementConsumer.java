package raz.inteview_test.everest.brynjolf.room;

import raz.inteview_test.everest.brynjolf.Direction;
import raz.inteview_test.everest.brynjolf.movement.MovementCondition;

//this class actually executes/implements the move logic
public class MovableElementConsumer implements MovementObserver {

    private final Element element;
    private final Element[][] roomMatrix;
    private final Room gameRoom;

    public MovableElementConsumer(Element element, /*Element[][] roomMatrix*/ Room gameRoom) {
        this.element = element;
        this.gameRoom = gameRoom;
        roomMatrix = gameRoom.getRoomMatrix();
    }

    @Override
    public void onMoveEvent(Direction direction) {

        if (!isActive())
            return;

        //1. Get the target/next position
        Element target = getNextPosition(direction);
        MovementCondition canMoveIntoTarget = new MovementCondition(element, target, gameRoom);
        boolean canTransitionIntoTarget = canMoveIntoTarget.isValidTarget() && canMoveIntoTarget.isMoveIntoTargetAllowed();
        //System.out.println(element.toString() + " -> " + target + " " + canTransitionIntoTarget);
        if (canTransitionIntoTarget) {
            //That means:
            //1. - transfer/transition the value to the next position/Element
            if (target.isEmpty() || target.isBryn()) {
                //As long as it is a BlankSpace, (or a Guard can move into Bryn), we should transition the value to the targe
                target.setValue(element.getValue());
            }


            //2. - set the value of the `this`/current element to Empty Space
            element.setValue(ElementValue.EMPTY);
            canMoveIntoTarget.executeAdditionalLogic();

            //3. Because the target has been updated (and it has become an active MovableElement(e.g:Bryn/Guard)),
            // Propagate the move, chain reaction
            if (!target.isStructural()) {
                //This is a particular case: Bryn can move into the exit.
                //But the exit is not a MotionAware Element, so it doesn't need to be further notified by invoking `onMoveEvent`
                ((MovableElement) target).getMovementHandler().onMoveEvent(direction);
            }
        } else {
            //The current movable element can't move
            //considering the Target(next position) which is given by the current direction
            //we can't move/advance
            //so just return for the current moveEvent
            return;
        }
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

    /**
     * Only Bryn or Guard can move, hence isActive - returns true
     * For the rest of Elements(e.g the Empty Space) is passive in movement
     */
    protected boolean isActive() {
        return (element.getValue() == ElementValue.BRYN || element.getValue() == ElementValue.GUARD);
    }
}
