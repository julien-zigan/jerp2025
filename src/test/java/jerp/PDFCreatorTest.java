package jerp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class PDFCreatorTest {
////    Opens File in PDFReader, leave commented out during regular tests
//    @Test // Opens the PDFFile on the host system for visual proof
//    void shouldReturnBlankDINA4PdfFile() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
//        String dateExtension = LocalDateTime.now().format(formatter);
//        String path = "src\\test\\resources\\tmpTestOutput\\TestBlankA4Page_";
//        String fqn = path + dateExtension;
//
//        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
//        File shouldBeBlankA4Pdf = PDFCreator.createFrom(letter, fqn);
//
//        Desktop.getDesktop().open(shouldBeBlankA4Pdf);
//    }

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

    @Test // Opens the PDFFile on the host system for visual proof
    void shouldPrintLetterHead() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String path = "src\\test\\resources\\tmpTestOutput\\TestLetterhead_";
        String fqn = path + dateExtension;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Letterhead letterhead = new Letterhead(
                "src/test/resources/mockLetterheads/lettehead_coffee.png");
        letter.setLetterhead(letterhead);
        File shouldHaveLetterHead = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldHaveLetterHead);
    }
}
