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

        return new File(path);
    }

}
