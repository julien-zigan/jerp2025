package jerp;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

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
