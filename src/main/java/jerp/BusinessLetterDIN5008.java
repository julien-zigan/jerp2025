package jerp;

import java.io.File;
import java.io.IOException;

public class BusinessLetterDIN5008 {
    private Layout layout;
    private Letterhead letterhead;

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Letterhead getLetterhead() {
        return letterhead;
    }

    public void setLetterhead(Letterhead letterhead) {
        this.letterhead = letterhead;
    }

    public File saveAsPDF(String path) throws IOException {
        return PDFCreator.createFrom(this, path);
    }

}
