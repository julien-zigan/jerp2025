package jerp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;


class BusinessLetterGermanyTest {

    @Test
    void toPDF() {
        BusinessLetterGermany letter = new BusinessLetterGermany();

        File letterPDF = letter.toPDF();

        assertNotNull(letterPDF);
    }
}