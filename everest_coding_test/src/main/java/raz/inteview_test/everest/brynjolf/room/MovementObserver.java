package raz.inteview_test.everest.brynjolf.room;

import raz.inteview_test.everest.brynjolf.Direction;

//these can receive a move request and handle it
public interface MovementObserver {

    void onMoveEvent(Direction direction);
}
