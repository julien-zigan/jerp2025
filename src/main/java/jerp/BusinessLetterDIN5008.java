package jerp;

import java.io.File;

public class BusinessLetterDIN5008 {
    private Layout layout;
    private Object letterhead;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
    public Object getLetterhead() {
        return letterhead;
    }

    public void setLetterhead(Object letterhead) {
        this.letterhead = letterhead;
    }

    public File saveAsPDF(String path) {
        path = addFileExtension(path);

        return new File(path);
    }

    private String addFileExtension(String filename) {
        boolean needsFileExtension =
                !(filename.trim().toLowerCase().endsWith(".pdf"));
        if (needsFileExtension) {
            filename = filename + ".pdf";
        }
        return filename;
    }


}
