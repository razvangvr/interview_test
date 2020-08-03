package raz.inteview_test.everest.brynjolf;

import org.junit.jupiter.api.Test;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.Room;
import raz.inteview_test.everest.brynjolf.util.MatrixFileConverter;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstablishmentTest {

    String movesSequence = "ludrr";

    Path testSrcResources = Paths.get("src", "test", "resources");

    Path testSubDir = testSrcResources.resolve("establishment");

    Room brynGame;

    MatrixFileConverter matrixFileConverter = new MatrixFileConverter();

    @Test
    void execute_movements_sequence() throws IOException {
        //Given a sequence of moves
        System.out.println(movesSequence);

        List<Direction> moves = Direction.parseString(movesSequence);

        // Given an initial/starting Room State Input
        String roomStrFormat = Files.readString(testSubDir.resolve("room.txt"));
        System.out.println("INPUT, starting Room State, Raw String >>");
        System.out.print(roomStrFormat);

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(testSubDir.resolve("room.txt"));
        System.out.println("starting Room State, Internal Representation printed >> ");
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        brynGame = new Room(roomMatrix);

        //WHEN executing the sequence of moves
        SimulationResult gameResult = brynGame.executeMoveSequence(moves);

        String roomOutput = brynGame.prettyPrint();


        //THEN we should get the following output
        String expectedStrFormat = Files.readString(testSubDir.resolve("final_room_ludrr_state.txt"));
        System.out.println("expected output >>");
        System.out.print(expectedStrFormat);

        assertEquals(expectedStrFormat.trim(), roomOutput);

        assertEquals(GameStatus.LOSE, gameResult.getGameStatus());

        assertEquals(2, gameResult.getMovesCount());

        System.out.println(gameResult.getGameStatus() + ": executed " + gameResult.getMovesCount() + " moves out of " + moves.size());
    }
}
