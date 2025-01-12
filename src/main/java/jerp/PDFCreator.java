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

                if (letter.getLetterhead() != null) {
                    BufferedImage bufferedImage = letter.getLetterhead().getContent();
                    int originalWidth = bufferedImage.getWidth();
                    int originalHeight = bufferedImage.getHeight();
                    double ratio = (double) originalWidth / originalHeight;
                    float computedHeight = layout.getLetterheadHeightInPoints();
                    float computedWidth = (float) (computedHeight * ratio);
                    PDImageXObject image  = LosslessFactory.createFromImage(document, bufferedImage);
                    contentStream.drawImage(
                            image,
                            layout.getLetterheadX(),
                            layout.getLetterheadY(),
                            computedWidth,
                            computedHeight
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
