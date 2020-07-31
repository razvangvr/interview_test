package raz.inteview_test.everest.brynjolf;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstablishmentTest {

    String movesSequence = "ur";

    Path testSrcResources = Paths.get("src", "test", "resources");

    Path testSubDir = testSrcResources.resolve("establishment/step0_up_right");

    Room brynGame;

    @Test
    void execute_movements_sequence() throws IOException {
        //Given a sequence of moves
        System.out.println(movesSequence);

        //XXX> I need to parse movesSequence into an Enum

        // Given an initial/starting Room State Input
        String roomStrFormat = Files.readString(testSubDir.resolve("room.txt"));
        System.out.println("starting Room State, Input");
        System.out.print(roomStrFormat);

        //WHEN executing the sequence of moves
        String roomOutput = brynGame.executeMoveSequence();

        //THEN we should get the following output
        String expectedStrFormat = Files.readString(testSubDir.resolve("room_up_right(final-expected).txt"));
        System.out.println("expected output >>");
        System.out.print(expectedStrFormat);

        assertEquals(expectedStrFormat, roomOutput);
    }
}
