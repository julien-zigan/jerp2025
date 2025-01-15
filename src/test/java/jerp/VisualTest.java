package jerp;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VisualTest {

    /// Opens File in PDFReader, comment out during regular tests
    @Test
    // Opens the PDFFile on the host system for visual proof
    void shouldCreateBlankDINA4PdfFileWithTemplateOverlay() throws Exception {
        String uuid = UUID.randomUUID().toString();
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        File shouldBeBlankA4Pdf = PDFCreator.createFrom(letter, uuid);
        shouldBeBlankA4Pdf.deleteOnExit();

        File templateLayout = new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf");

        File overlayedOnTemplate = overlay(templateLayout, shouldBeBlankA4Pdf);

        Desktop.getDesktop().open(overlayedOnTemplate);
    }

    /// Opens File in PDFReader, comment out during regular tests @ParameterizedTest
    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/mockLetterheads/letterhead_wood.jpg",
            "src/test/resources/mockLetterheads/letterhead_quillpen.jpg",
            "src/test/resources/mockLetterheads/letterhead_wood.png",
            "src/test/resources/mockLetterheads/lettehead_coffee.png"
    })
    void shouldPrintLetterHead_TYPE_A(String imagePath) throws Exception {
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        Layout layout = Layout.TypeA();
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(imagePath);
        letter.setLetterhead(letterhead);
        String uuid = UUID.randomUUID().toString();

        File shouldHaveLetterHead = PDFCreator.createFrom(letter, uuid);

        File templateLayout = new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf");
        File overlayedOnTemplate = overlay(templateLayout, shouldHaveLetterHead);
        Desktop.getDesktop().open(overlayedOnTemplate);
    }

    /// Opens File in PDFReader, comment out during regular tests @ParameterizedTest
    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/mockLetterheads/letterhead_wood.jpg",
            "src/test/resources/mockLetterheads/letterhead_quillpen.jpg",
            "src/test/resources/mockLetterheads/letterhead_wood.png",
            "src/test/resources/mockLetterheads/lettehead_coffee.png"
    })
    void shouldPrintLetterHead(String imagePath) throws Exception {
        Layout[] layouts = {Layout.TypeA(), Layout.TypeB()};
        File templateTypeA = new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf");
        File templateTypeB = new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_B.pdf");

        for (Layout layout : layouts) {
            BusinessLetterDIN5008 letter = setUpLetterWithLetterHead(layout, imagePath);
            String uuid = UUID.randomUUID().toString();

            File shouldHaveLetterHead = PDFCreator.createFrom(letter, uuid);

            // TODO figure out how to determine correct template
            File overlayedOnTemplate = overlay(templateLayout, shouldHaveLetterHead);
            Desktop.getDesktop().open(overlayedOnTemplate);
        }
    }

    private BusinessLetterDIN5008 setUpLetterWithLetterHead(Layout layout, String imagePath) throws Exception {
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        letter.setLayout(layout);
        Letterhead letterhead = new Letterhead(imagePath);
        letter.setLetterhead(letterhead);
        return letter;
    }

    private File overlay(File background, File foreground) throws IOException {
        String path = "src\\test\\resources\\tmpTestOutput\\";
        String filename = getCallerMethodName();
        UUID uuid = UUID.randomUUID();
        String fqn = path + filename + uuid + ".pdf";

        PDDocument overlayDoc = Loader.loadPDF(background);
        Overlay overlayObj = new Overlay();
        PDDocument originalDoc = Loader.loadPDF(foreground);
        overlayObj.setOverlayPosition(Overlay.Position.FOREGROUND);
        overlayObj.setInputPDF(originalDoc);
        overlayObj.setAllPagesOverlayPDF(overlayDoc);
        Map<Integer, String> ovmap = new HashMap<Integer, String>(); // empty map is a dummy
        overlayObj.overlay(ovmap);
        originalDoc.save(fqn);
        overlayDoc.close();
        originalDoc.close();
        return new File(fqn);
    }

    public static String getCallerMethodName() {
        return StackWalker.getInstance()
                .walk(s -> s.skip(2).findFirst())
                .get()
                .getMethodName();
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
                "src/test/resources/mockLetterheads/letterhead_quillpen.jpg");
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
