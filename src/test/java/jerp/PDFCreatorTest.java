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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PDFCreatorTest {
    // TODO: make all visual tests layover tests

   /// Opens File in PDFReader, comment out during regular tests
    @Test // Opens the PDFFile on the host system for visual proof
    void shouldReturnBlankDINA4PdfFile() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String path = "src\\test\\resources\\tmpTestOutput\\TestBlankA4Page_";
        String fqn = path + dateExtension;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        File shouldBeBlankA4Pdf = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldBeBlankA4Pdf);
    }

    /// Opens File in PDFReader, comment out during regular tests
    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/mockLetterheads/letterhead_wood.jpg",
            "src/test/resources/mockLetterheads/letterhead_quillpen.jpg",
            "src/test/resources/mockLetterheads/letterhead_wood.png",
            "src/test/resources/mockLetterheads/lettehead_coffee.png"
    })
    void shouldPrintLetterHeadTypeA(String imagePath) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1);
        String path = "src\\test\\resources\\tmpTestOutput\\TestLetterhead_";
        UUID uuid = UUID.randomUUID();
        String fqn = path + fileExtension + dateExtension + uuid;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Layout layout = Layout.TypeA();
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(imagePath);
        letter.setLetterhead(letterhead);
        File shouldHaveLetterHead = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldHaveLetterHead);
    }

    /// Opens File in PDFReader, comment out during regular tests
    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/mockLetterheads/letterhead_wood.jpg",
            "src/test/resources/mockLetterheads/letterhead_quillpen.jpg",
            "src/test/resources/mockLetterheads/letterhead_wood.png",
            "src/test/resources/mockLetterheads/lettehead_coffee.png"
    })
    void shouldPrintLetterHeadTypeB(String imagePath) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1);
        String path = "src\\test\\resources\\tmpTestOutput\\TestLetterhead_";
        UUID uuid = UUID.randomUUID();
        String fqn = path + fileExtension + dateExtension + uuid;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Layout layout = Layout.TypeB();
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(imagePath);
        letter.setLetterhead(letterhead);
        File shouldHaveLetterHead = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldHaveLetterHead);
    }

    /// Opens File in PDFReader, comment out during regular tests
    @ParameterizedTest
    @ValueSource(strings = {
            "left", "center-left", "center", "center-right", "right",
            "", "+ßÄÜö2§$%"
    })
    void shouldAlignLetterHead(String alignment) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String path = "src\\test\\resources\\tmpTestOutput\\TestLetterheadAlignment_";
        UUID uuid = UUID.randomUUID();
        String fqn = path + dateExtension + uuid;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Layout layout = Layout.TypeA();
        layout.setLetterheadAlignment(alignment);
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(
                "src/test/resources/mockLetterheads/letterhead_wood.jpg");
        letter.setLetterhead(letterhead);
        File shouldHaveLetterHead = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldHaveLetterHead);
    }

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
