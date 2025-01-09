package jerp;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDTypedDictionaryWrapper;
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
                if (letter.getLetterhead() != null) {
                    BufferedImage bufferedImage = letter.getLetterhead().getContent();
                    PDImageXObject image  = LosslessFactory.createFromImage(document, bufferedImage);
                    contentStream.drawImage(image, 0, 0, 30, 20); // TODO: continue here
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
