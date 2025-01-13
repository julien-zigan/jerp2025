package jerp;

public class Layout {
    public static final float DOCUMENT_WIDTH_IN_POINTS = 595.27563F;
    public static final float DOCUMENT_HEIGHT_IN_POINTS = 841.8898F;

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

    public void setLetterheadAlignment(String alignment) {
        letterheadX = switch (alignment) {
            case "center-left" -> (float) DOCUMENT_WIDTH_IN_POINTS / 4;
            case "center" -> (float) DOCUMENT_WIDTH_IN_POINTS / 2;
            case "center-right" -> (float) DOCUMENT_WIDTH_IN_POINTS * 3 / 4;
            case "right" -> (float) DOCUMENT_WIDTH_IN_POINTS;
            default -> 0F;
        };

    }

}
