package jerp;

import jerp.businessletter.BusinessLetterDIN5008;
import jerp.businessletter.PDFCreator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PDFCreatorTest {

    @ParameterizedTest
    @ValueSource(strings = {".pdf", "ABC.PDF", "Äüö.pDF", "pDFx.y.z.pdf", "pepe.pdf"})
    void shouldCreateFileWithGivenPath(String path) throws Exception {
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();

        File shouldBeFileWithGivenPath = PDFCreator.createFrom(letter, path);

        assertTrue(Files.exists(Path.of(path)));

        shouldBeFileWithGivenPath.deleteOnExit();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "CDE", "öüÄ", "w.x.y.", "pepepdf"})
    void shouldCreateFileAndAddFileExtension(String path) throws Exception {
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();

        File shouldBeFileWithGivenPath = PDFCreator.createFrom(letter, path);

        assertTrue(Files.exists(Path.of(path + ".pdf")));

        shouldBeFileWithGivenPath.deleteOnExit();
    }

}
