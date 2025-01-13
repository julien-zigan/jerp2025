package jerp;

public class Layout {
    private static final float DOCUMENT_WIDTH_IN_POINTS = 595.27563F;
    private static final float DOCUMENT_HEIGHT_IN_POINTS = 841.8898F;

    private float letterheadX;
    private float letterheadY;
    private float letterheadWidthInPoints;
    private float letterheadHeightInPoints;

    private Layout(
            float letterheadWidthInPoints,
            float letterheadHeightInPoints
    ) {
        this.letterheadX = 0;
        this.letterheadY = DOCUMENT_HEIGHT_IN_POINTS - letterheadHeightInPoints;
        this.letterheadWidthInPoints = letterheadWidthInPoints;
        this.letterheadHeightInPoints = letterheadHeightInPoints;
    }

    public static Layout TypeA() {
        return new Layout(
                DOCUMENT_WIDTH_IN_POINTS,
                76.5354F
        );
    }

    public static Layout TypeB() {
        return new Layout(
                DOCUMENT_WIDTH_IN_POINTS,
                127.559F
        );
    }

    public float getLetterheadX() {
        return letterheadX;
    }

    public float getLetterheadY() {
        return letterheadY;
    }

    public float getLetterheadWidthInPoints() {
        return letterheadWidthInPoints;
    }

    public float getLetterheadHeightInPoints() {
        return letterheadHeightInPoints;
    }

}
