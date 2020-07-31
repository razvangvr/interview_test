package raz.inteview_test.everest.brynjolf.util;

import org.junit.jupiter.api.Test;
import raz.inteview_test.everest.brynjolf.room.Element;
import raz.inteview_test.everest.brynjolf.room.ElementValue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MatrixFileConverterShould {

    Path testSrcResources = Paths.get("src", "test", "resources");
    Path testSubDir = testSrcResources.resolve("util");


    MatrixFileConverter matrixFileConverter = new MatrixFileConverter();

    @Test
    void load_matrix_from_file() throws IOException {
//        URL url = getClass().getClassLoader().getResource("room.txt");
//        Path path = Paths.get(url.toURI());
        Path filePath = testSubDir.resolve("room.txt");

        Element[][] roomMatrix = matrixFileConverter.loadFromFile(filePath);

        Element[][] expectedMatrix = new Element[3][3];

        expectedMatrix[0][0] = new Element(ElementValue.EMPTY);
        expectedMatrix[0][1] = new Element(ElementValue.WALL);
        expectedMatrix[0][2] = new Element(ElementValue.GUARD);

        expectedMatrix[1][0] = new Element(ElementValue.BRYN);
        expectedMatrix[1][1] = new Element(ElementValue.EMPTY);
        expectedMatrix[1][2] = new Element(ElementValue.EXIT);

        expectedMatrix[2][0] = new Element(ElementValue.GUARD);
        expectedMatrix[2][1] = new Element(ElementValue.EMPTY);
        expectedMatrix[2][2] = new Element(ElementValue.EMPTY);

        assertArrayEquals(expectedMatrix, roomMatrix, "matrices should be equal");

        System.out.println("Matrix from File:");
        MatrixUtil.showMatrix(roomMatrix);

        System.out.println(MatrixUtil.prettyPrint(roomMatrix));
    }
}
