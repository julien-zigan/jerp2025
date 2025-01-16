package jerp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BusinessLetterDIN5008Test {

    @ParameterizedTest
    @ValueSource(strings = {"0.pdf", "ABD.PDF", "Äüß.pDF", "pDFx.o.z.pdf", "pupe.pdf"})
    void shouldCreateFileWithGivenPath(String path) throws Exception {
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();

        File shouldBeFileWithGivenPath = letter.saveAsPDF(path);

        assertTrue(Files.exists(Path.of(path)));

        shouldBeFileWithGivenPath.deleteOnExit();
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "CDEF", "öüÄi", "x-w.x.y.", "papepdf"})
    void shouldCreateFileAndAddFileExtension(String path) throws Exception {
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();

        File shouldBeFileWithGivenPath = letter.saveAsPDF(path);

        assertTrue(Files.exists(Path.of(path + ".pdf")));

        shouldBeFileWithGivenPath.deleteOnExit();
    }
}
