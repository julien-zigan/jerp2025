package jerp;

public class Layout {
    public static final float DOCUMENT_WIDTH_IN_POINTS = 595.27563F;
    public static final float DOCUMENT_HEIGHT_IN_POINTS = 841.8898F;

    private String type;

    private Alignment alignment = Alignment.LEFT;
    private float letterheadX;
    private float letterheadY;
    private float letterheadWidthInPoints;
    private float letterheadHeightInPoints;

    private Layout(
            String type,
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
                "Type A",
                DOCUMENT_WIDTH_IN_POINTS,
                76.5354F
        );
    }

    public static Layout TypeB() {
        return new Layout(
                "Type B",
                DOCUMENT_WIDTH_IN_POINTS,
                127.559F
        );
    }

    public String getType() {
        return type;
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

    @Override
    public String toString() {
        return "Layout [type=" + type + ", letterheadX=" + letterheadX + ", letterheadY="
                + letterheadY + ", letterheadWidthInPoints=" + letterheadWidthInPoints
                + ", letterheadHeightInPoints=" + letterheadHeightInPoints + "]";
    }

    public void setLetterheadAlignment(Alignment alignment) {
        this.alignment = alignment;
        letterheadX = switch (alignment) {
            case LEFT -> 0F;
            case CENTER_LEFT -> (float) DOCUMENT_WIDTH_IN_POINTS / 4;
            case CENTER -> (float) DOCUMENT_WIDTH_IN_POINTS / 2;
            case CENTER_RIGHT -> (float) DOCUMENT_WIDTH_IN_POINTS * 3 / 4;
            case RIGHT -> (float) DOCUMENT_WIDTH_IN_POINTS;
        };

    }

}
