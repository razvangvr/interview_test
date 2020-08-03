package raz.inteview_test.everest.brynjolf.movement;

import org.junit.jupiter.api.function.Executable;
import raz.inteview_test.everest.brynjolf.GameStatus;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.Room;

// rename to MovementTransition?
public class MovementCondition {
    final Element currentPosition;
    final Element nextPosition;
    final Room gameRoom;

    Executable executableAdditionalLogic;

    public MovementCondition(Element currentPosition, Element nextPosition, Room room) {
        this.currentPosition = currentPosition;
        this.nextPosition = nextPosition;
        this.gameRoom = room;
    }

    public void executeAdditionalLogic() {
        try {
            if (executableAdditionalLogic != null)
                executableAdditionalLogic.execute();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidTarget() {
        if (nextPosition == null || nextPosition.isWall())
            return false;
        else
            return true;
    }

    public boolean isMoveIntoTargetAllowed() {

        if (nextPosition.isEmpty())
            //Anyone can always move into a Empty Space
            return true;

        if (currentPosition.isGuard() && nextPosition.isBryn()) {
            //a guard can move into a bryn
            executableAdditionalLogic = () -> gameRoom.setGameStatus(GameStatus.LOSE);
            return true;
        }

        if (currentPosition.isBryn() && nextPosition.isGuard()) {
            //it's a stupid move, but... if you really want to... bryn can move into a guard, and you lose!
            executableAdditionalLogic = () -> gameRoom.setGameStatus(GameStatus.LOSE);
            return true;
        }

        if (currentPosition.isBryn() && nextPosition.isExit()) {
            //Bryn can move into a Exit
            executableAdditionalLogic = () -> gameRoom.setGameStatus(GameStatus.WIN);
            return true;
        }

        if (currentPosition.isGuard() && nextPosition.isExit())
            //a Guard can not move into Exit
            return false;

        if (currentPosition.isGuard() && nextPosition.isGuard())
            //a guard can not move into a guard
            return false;

        return false;
    }
}
