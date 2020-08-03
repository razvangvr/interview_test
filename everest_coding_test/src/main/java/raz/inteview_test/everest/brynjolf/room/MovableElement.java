package raz.inteview_test.everest.brynjolf.room;

public class MovableElement extends Element{

    private final MovementObserver movementHandler;
    //private final Room room;

    public MovableElement(Element element, Room gameRoom)  {
        //super(value, rowIdx, colIdx);
        super(element.getValue(), element.rowIdx(), element.colIdx());
        this.movementHandler = new MovableElementConsumer(this, gameRoom);
    }

    public MovementObserver getMovementHandler() {
        return movementHandler;
    }
}
