package jerp;

import jerp.businessletter.Alignment;
import jerp.businessletter.BusinessLetterDIN5008;
import jerp.businessletter.Layout;
import jerp.businessletter.PDFCreator;
import jerp.businessletter.letterfields.Letterhead;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Disabled
class VisualTest {
    /// Tests in this class opens File in PDFReader, don't run with regular tests

    @Test
    void shouldCreateBlankDINA4PdfFileWithTemplateOverlay() throws Exception {
        String uuid = UUID.randomUUID().toString();
        BusinessLetterDIN5008 letter = new BusinessLetterDIN5008();
        File shouldBeBlankA4Pdf = PDFCreator.createFrom(letter, uuid);
        shouldBeBlankA4Pdf.deleteOnExit();

        File templateLayout = new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf");

        File overlayedOnTemplate = overlay(templateLayout, shouldBeBlankA4Pdf);

        Desktop.getDesktop().open(overlayedOnTemplate);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/mockLetterheads/letterhead_wood.jpg",
            "src/test/resources/mockLetterheads/letterhead_quillpen.jpg",
            "src/test/resources/mockLetterheads/letterhead_wood.png",
            "src/test/resources/mockLetterheads/lettehead_coffee.png"
    })
    void shouldPrintLetterHead(String imagePath) throws Exception {
        LayoutType[] layouts = {new LayoutTypeA(), new LayoutTypeB()};

        for (LayoutType layout  : layouts) {
            BusinessLetterDIN5008 letter = setUpLetterWithLetterHead(layout.getLayout(), imagePath);
            String uuid = UUID.randomUUID().toString();

            File shouldHaveLetterHead = PDFCreator.createFrom(letter, uuid);
            shouldHaveLetterHead.deleteOnExit();

            File overlayedOnTemplate = overlay(layout.getTemplate(), shouldHaveLetterHead);
            Desktop.getDesktop().open(overlayedOnTemplate);
        }
    }

    @Test
    void shouldAlignLetterHead() throws Exception {
        LayoutType[] layouts = {new LayoutTypeA(), new LayoutTypeB()};
        String imagePath = "src/test/resources/mockLetterheads/letterhead_quillpen.jpg";

        for (Alignment alignment : Alignment.values()) {
            for (LayoutType layout : layouts) {
                layout.getLayout().setLetterheadAlignment(alignment);
                BusinessLetterDIN5008 letter = setUpLetterWithLetterHead(layout.getLayout(), imagePath);
                String uuid = UUID.randomUUID().toString();

                File shouldHaveLetterHead = PDFCreator.createFrom(letter, uuid);
                shouldHaveLetterHead.deleteOnExit();

                File overlayedOnTemplate = overlay(layout.getTemplate(), shouldHaveLetterHead);
                Desktop.getDesktop().open(overlayedOnTemplate);
            }
        }
    }

    @Test
        void shouldAdjustHeightAndWidthLetterHead() throws Exception {
            for (Alignment alignment : Alignment.values()) {
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

    private class LayoutType {
        private final Layout layout;
        private final File template;

        public LayoutType(Layout layout, File template) {
            this.layout = layout;
            this.template = template;
        }

        public Layout getLayout() {
            return layout;
        }

        public File getTemplate() {
            return template;
        }
    }

    private class LayoutTypeA extends LayoutType {
        public LayoutTypeA() {
            super(
                    Layout.TypeA(),
                    new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_A.pdf")
            );
        }
    }

    private class LayoutTypeB extends LayoutType {
        public LayoutTypeB() {
            super(
                    Layout.TypeB(),
                    new File("src/test/resources/DIN_5008_Templates/DIN_5008_Form_B.pdf")
            );
        }
    }
}
