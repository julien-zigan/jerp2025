package jerp;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

class VisualTestCleanUp {
    @Test
    void cleanUp() {
        File dir = new File("src/test/resources/tmpTestOutput");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            file.delete();
        }
    }
}
