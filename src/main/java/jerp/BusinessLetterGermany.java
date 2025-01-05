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



    public File toPDF() {

        return new File("Maketestgreen.pdf");
    }
}

