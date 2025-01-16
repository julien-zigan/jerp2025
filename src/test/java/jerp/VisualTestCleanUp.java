package jerp;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

@Disabled
class VisualTestCleanUp {
    @Test
    void cleanUp() {
        File dir = new File("src/test/resources/tmpTestOutput");
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            file.delete();
        }
    }
}
