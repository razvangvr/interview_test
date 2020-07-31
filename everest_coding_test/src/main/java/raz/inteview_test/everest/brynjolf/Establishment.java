package raz.inteview_test.everest.brynjolf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Establishment {

    static Path resourcesDir = Paths.get("src", "main", "resources");

    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            throw new IllegalArgumentException("Moves Sequence is required!");
        System.out.println("Step 0, sequence of moves Program Args >>" + Arrays.toString(args));
        System.out.println("sequence of moves:"+args[0]);

        Path filePath = resourcesDir.resolve("room.txt");

        String roomStrFormat = Files.readString(filePath);

        System.out.println("Step 1, read from room.txt/Room String Representation,  starting/initial position >> ");
        System.out.print(roomStrFormat);
    }

    //TODO next step:
    /**
     *  - the room will be in the above format, it is read from room.txt
     *
     * Write a commandline application that
     * - will take this sequence as input and
     * - prints how the room looks at the end
     * */
    /*
    1/ Load the Game Room from room.txt
    Trebuie sa aplic o secventa de miscari - sequence of moves
    that is: <<Run the sequence of moves>>
    that is: evaluate/apply the - sequence of moves, considering the Game Rules/the Rules of Room,

    and the result, the output of <<Running/Executing the sequence of moves>> should be:
    expected-final state, considering the Game Rules/Rules of Room

    XXX> in order to run/execute the sequence of moves I need an internal representation
    * */
}
