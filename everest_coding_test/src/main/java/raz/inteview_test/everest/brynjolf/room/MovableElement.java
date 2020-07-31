package raz.inteview_test.everest.brynjolf.room;

public class MovableElement extends Element{

    private final MovementObserver movementHandler;

    public MovableElement(Element element, Element[][] roomMatrix)  {
        //super(value, rowIdx, colIdx);
        super(element.getValue(), element.rowIdx(), element.colIdx());
        this.movementHandler = new MovableElementConsumer(this, roomMatrix);
    }

    public MovementObserver getMovementHandler() {
        return movementHandler;
    }
}
