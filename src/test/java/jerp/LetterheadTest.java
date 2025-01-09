package jerp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LetterheadTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/mockLetterheads/lettehead_coffee.png",
                            "src/test/resources/mockLetterheads/letterhead_autotrac.svg",
                            "src/test/resources/mockLetterheads/letterhead_wood.jpg"})
    void mustNotThrowException(String path) {
        assertDoesNotThrow(() -> {new Letterhead(path);});
    }
}