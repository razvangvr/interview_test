package raz.inteview_test.everest.brynjolf;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.Room;
import raz.inteview_test.everest.brynjolf.room.RoomMovementTestBase;
import raz.inteview_test.everest.brynjolf.solver2.BFSStandard2;
import raz.inteview_test.everest.brynjolf.solver2.DirectionNode;
import raz.inteview_test.everest.brynjolf.util.MatrixUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static raz.inteview_test.everest.brynjolf.Direction.*;

class DirectionalNeighboursTest extends RoomMovementTestBase {

    BFSStandard2 solver;

    @Override
    protected String testFilesSubDir() {
        return "directionalNeighbours";
    }

    private void setUp(String inputFile) throws IOException {
        Path filePath = testSubDir.resolve(inputFile);
        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);
        System.out.println(MatrixUtil.prettyPrint(roomMatrix));

        solver = new BFSStandard2(roomMatrix);

        room = new Room(roomMatrix);
    }

    @ParameterizedTest
    @MethodSource("test1Args")
        //@DisplayName("Th")
    void test_all_movement_combinations(String inputFile, Set<Direction> expected) throws Exception {
        setUp(inputFile);

        Set<DirectionNode> directionNodes = solver.getBryn().neighboursOnDirection();
        Set<Direction> res = directionNodes.stream().map(DirectionNode::direction).collect(Collectors.toSet());

        assertEquals(expected, res);
    }

    private static Stream<Arguments> test1Args() {
        return Stream.of(
//                Arguments.of("next_to_a_wall.txt", Set.of(DOWN, LEFT, RIGHT))
//                ,
//                Arguments.of("neighbours_in_all_directions_RightExit.txt", Set.of(UP, DOWN, LEFT, RIGHT))
//                ,
//                Arguments.of("downGuard_rightExit_upSpace.txt", Set.of(UP, RIGHT))
//                ,
                Arguments.of("2_spaces_till_wall.txt", Set.of(UP, RIGHT, LEFT))

        );
    }
}
