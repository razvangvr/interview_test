package raz.inteview_test.everest.brynjolf.room;

import org.junit.jupiter.api.BeforeEach;
import raz.inteview_test.everest.brynjolf.util.MatrixFileConverter;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class RoomMovementTestBase {

    static Path testSrcResources = Paths.get("src", "test", "resources");

    protected Path testSubDir = null;

    protected MatrixFileConverter matrixFileConverter = new MatrixFileConverter();

    Room room;

    @BeforeEach
    void setUp() {
        if(testSubDir==null){
            testSubDir = testSrcResources.resolve(testFilesSubDir());
        }
    }

    protected abstract String testFilesSubDir();
}
