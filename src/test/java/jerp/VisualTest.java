package jerp;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VisualTest {

    /// Opens File in PDFReader, comment out during regular tests
    @Test
    // Opens the PDFFile on the host system for visual proof
    void shouldReturnBlankDINA4PdfFile() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String path = "src\\test\\resources\\tmpTestOutput\\TestBlankA4Page_";
        String fqn = path + dateExtension;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        File shouldBeBlankA4Pdf = PDFCreator.createFrom(letter, fqn);

        PDDocument overlayDoc = Loader.loadPDF(
                new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf"));
        Overlay overlayObj = new Overlay();

        // TODO: Extract this as overlayWithForm()
        PDDocument originalDoc = Loader.loadPDF(shouldBeBlankA4Pdf);
        overlayObj.setOverlayPosition(Overlay.Position.FOREGROUND);
        overlayObj.setInputPDF(originalDoc);
        overlayObj.setAllPagesOverlayPDF(overlayDoc);
        Map<Integer, String> ovmap = new HashMap<Integer, String>(); // empty map is a dummy
        overlayObj.overlay(ovmap);
        originalDoc.save(path + dateExtension + "OVERLAY.pdf");
        overlayDoc.close();
        originalDoc.close();

        Desktop.getDesktop().open(new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf"));
    }
 /// Opens File in PDFReader, comment out during regular tests @ParameterizedTest
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
            "left",
            "center-left",
            "center",
            "center-right",
            "right",
            "", "+ßÄÜö2§$%"
    })
    void shouldAlignLetterHead(String alignment) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String path = "src\\test\\resources\\tmpTestOutput\\TestLetterheadAlignment_";
        UUID uuid = UUID.randomUUID();
        String fqn = path + dateExtension + uuid;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Layout layout = Layout.TypeB();
        layout.setLetterheadAlignment(alignment);
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(
                "src/test/resources/mockLetterheads/letterhead_wood.png");
        letter.setLetterhead(letterhead);
        File shouldHaveLetterHead = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldHaveLetterHead);
    }

    /// Opens File in PDFReader, comment out during regular tests
    @ParameterizedTest
    @ValueSource(strings = {
            "left",
            "center-left",
            "center",
            "center-right",
            "right",
            "", "+ßÄÜö2§$%"
    })
    void shouldAdjustHeightAndWidthLetterHead(String alignment) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_H.mm");
        String dateExtension = LocalDateTime.now().format(formatter);
        String path = "src\\test\\resources\\tmpTestOutput\\TestLetterheadAlignment_";
        UUID uuid = UUID.randomUUID();
        String fqn = path + dateExtension + uuid;

        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Layout layout = Layout.TypeB();
        layout.setLetterheadAlignment(alignment);
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(
                "src/test/resources/mockLetterheads/letterhead_ultrawide.jpg");
        letter.setLetterhead(letterhead);
        File shouldHaveLetterHead = PDFCreator.createFrom(letter, fqn);

        Desktop.getDesktop().open(shouldHaveLetterHead);
    }


}