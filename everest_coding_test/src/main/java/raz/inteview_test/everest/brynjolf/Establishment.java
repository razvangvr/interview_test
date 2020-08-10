package raz.inteview_test.everest.brynjolf;

import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.Room;
import raz.inteview_test.everest.brynjolf.util.MatrixFileConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Attainment/The Goal: ability to perfectly simulate the outcome of a sequence of moves
 * That is:
 * Input:
 * - the room, it is to be read from room.txt
 * - String containing the sequence of moves: l,r,u,d
 * <hr/>
 * <p>
 * Write a commandline application that will take this sequence as input and prints how the room looks. That is:<b>perfectly simulate the outcome of a sequence of moves<b/>
 * </p>
 */
public class Establishment {
    private final static MatrixFileConverter matrixFileConverter = new MatrixFileConverter();

    static Path resourcesDir = Paths.get("src", "main", "resources");

    private final String roomInputStr;
    private final Path roomFilePath;
    private final String movesSequence;
    private final List<Direction> moves;
    private SimulationResult gameResult;

    public SimulationResult getGameResult() {
        return gameResult;
    }

    public Establishment(Path roomFilePath, String movesSequence) {
        this.roomFilePath = roomFilePath;
        this.movesSequence = movesSequence;
        roomInputStr = null;

        validateInput();
        moves = Direction.parseString(movesSequence);
    }

    public Establishment(String roomFileContent, String movesSequence) {
        this.roomFilePath = null;
        this.movesSequence = movesSequence;
        roomInputStr = roomFileContent;

        validateInput();
        moves = Direction.parseString(movesSequence);
    }

    private void validateInput() {
        if (roomFilePath != null) {
            if (!Files.exists(roomFilePath))
                throw new IllegalArgumentException("No valid filePath" + roomFilePath + " expecting 'room.txt' in project location resources/room.txt");
        } else {
            if (roomInputStr == null) {
                throw new IllegalArgumentException("No room.txt, it is required");
            }
        }


        if (movesSequence == null || movesSequence.isEmpty())
            throw new IllegalArgumentException("movesSequence is required, it can't be empty");
    }

    /**
     * prints how the rooms looks at the end
     */
    String executeMoves() throws IOException {
        Element[][] roomMatrix = roomFilePath != null ? matrixFileConverter.loadFromFile(roomFilePath) : matrixFileConverter.loadFromFile(roomInputStr);

        Room brynGame = new Room(roomMatrix);

        gameResult = brynGame.executeMoveSequence(moves);

        String roomOutput = brynGame.prettyPrint();

        return roomOutput;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            throw new IllegalArgumentException("Moves Sequence is required!");

        Path filePath = resourcesDir.resolve("room.txt");
        String movesSeq = args[0];

        String roomStrFormat = null;
        Establishment phase1 = null;
        System.out.println("Following movesSequence will be executed:" + movesSeq);
        try {
            roomStrFormat = Files.readString(filePath);
            phase1 = new Establishment(filePath, movesSeq);
            System.out.println("RUNNING FROM INTELLIJ");
        } catch (NoSuchFileException io) {
            System.out.println("RUNNING FROM BASH/CLI");
            try (InputStream inputStream = Establishment.class.getResourceAsStream("/room.txt");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                roomStrFormat = reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
                phase1 = new Establishment(roomStrFormat, movesSeq);
            }
        }

        System.out.println("Step 1, reading from room.txt the Room String Representation, starting position:");
        System.out.println(roomStrFormat);

        String outputStrMatrixFormat = phase1.executeMoves();

        System.out.println("Output:");
        System.out.println(outputStrMatrixFormat);

        System.out.println(phase1.getGameResult().getGameStatus() + ": executed " + phase1.getGameResult().getMovesCount() + " moves out of " + phase1.moves.size());
    }

}
