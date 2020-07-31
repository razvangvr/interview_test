//package raz.inteview_test.everest.brynjolf.room;
//
//import raz.inteview_test.everest.brynjolf.Direction;
//
///**
// * This element takes part in movement
// * It's an Element which needs to observe/react to a MoveEvent
// * <p>
// * Some elements(Walls, Exit) have nothing to do with Movement
// * - they don't move,
// * - their value can not be changed
// */
//public class MotionSensitiveElement extends Element implements MovementObserver {
//
////    public MotionSensitiveElement(ElementValue value) {
////        super(value);
////    }
//
//    private final Element[][] roomMatrix;
//
//    public MotionSensitiveElement(Element element, Element[][] roomMatrix) {
//        super(element.getValue(), element.rowIdx(), element.colIdx());
//        this.roomMatrix = roomMatrix;
//    }
//
//    @Override
//    public void onMoveEvent(Direction direction) {
//        //1. Get the target/next position
//        Element target = null;//= getNextPosition(direction);
//        if (target == null) {
//            //On the Edge, there is no target in this direction
//            return;
//        }
//
//        //As long as it is a BlankSpace we can perform the move
//        /*
//        That means
//        - transfer/transition the value to the next position/Element
//        - set the value of the `this`/current element to Empty Space
//        * */
//    }
//
//    private Element getNextPosition(Direction direction) {
//        int rowIdx = direction.rowIdxOffset == 0 ? this.rowIdx() : this.rowIdx() + direction.rowIdxOffset;
//        int colIdx = direction.colIdxOffset == 0 ? this.colIdx() : this.colIdx() + direction.colIdxOffset;
//
//        Element target = null;
//
//        try {
//            target = roomMatrix[rowIdx][colIdx];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            //Ignored
//        }
//
//        return target;
//    }
//
//
//}
