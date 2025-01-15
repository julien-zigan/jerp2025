package jerp;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static jerp.Layout.DOCUMENT_HEIGHT_IN_POINTS;
import static jerp.Layout.DOCUMENT_WIDTH_IN_POINTS;

public class PDFCreator {
    public static File createFrom(BusinessLetterDIN5008 letter,
                                     String path) throws IOException {
        path = addFileExtension(path);
        File file = new File(path);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                Layout layout = letter.getLayout();
                Letterhead letterhead = letter.getLetterhead();

                if (letterhead != null) {
                    float height = layout.getLetterheadHeightInPoints();
                    float width = (float) (height * letterhead.getAspectRatio());
                    float y = layout.getLetterheadY();

                    // TODO: Extract
                    if (width > DOCUMENT_WIDTH_IN_POINTS) {
                        double resizingFactor = DOCUMENT_WIDTH_IN_POINTS / width;
                        height *= (float) resizingFactor;
                        width *= (float) resizingFactor;
                        float heightDifference = DOCUMENT_HEIGHT_IN_POINTS
                                - layout.getLetterheadY()
                                - height;
                        y += heightDifference / 2;
                    }

                    float leftEnd = layout.getLetterheadX() - (width / 2);
                    float rightEnd = layout.getLetterheadX() + (width / 2);
                    float x = layout.getLetterheadX() - (width /2);

                    if (layout.getLetterheadX() < DOCUMENT_WIDTH_IN_POINTS / 2) {
                        if (leftEnd < 0) {
                            x = 0F;
                        }
                    } else {
                        if (rightEnd > DOCUMENT_WIDTH_IN_POINTS) {
                            x = DOCUMENT_WIDTH_IN_POINTS - width;
                        }
                    }

                    BufferedImage bufferedImage = letterhead.getGraphic();
                    PDImageXObject image  =
                            LosslessFactory.createFromImage(document, bufferedImage);
                    contentStream.drawImage(
                            image,
                            x,
                            y,
                            width,
                            height
                    );
                }
            }

            document.save(file);
        }
        return file;
    }

    private static String addFileExtension(String path) {
        boolean needsFileExtension =
                !(path.trim().toLowerCase().endsWith(".pdf"));
        if (needsFileExtension) {
            path = path + ".pdf";
        }
        return path;
    }


}
