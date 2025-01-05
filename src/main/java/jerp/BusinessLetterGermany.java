package jerp;

import java.io.File;


public class BusinessLetterGermany {
    private Object letterhead;

    public Object getLetterhead() {
        return letterhead;
    }

    public void setLetterhead(Object letterhead) {
        this.letterhead = letterhead;
    }


    public File saveAsPDF(String filename, String directoryPath) {

        boolean needsFileExtension =
                !(filename.trim().toLowerCase().endsWith(".pdf"));
        if (needsFileExtension) {
            filename = filename + ".pdf";
        }

        String fullyQualifiedPath = directoryPath + File.separator + filename;

        return null;
    }
}
