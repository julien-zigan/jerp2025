package jerp;

public class Layout {
    private static final double DOCUMENT_WIDTH_IN_POINTS = 595.27563;
    private static final double DOCUMENT_HEIGHT_IN_POINTS = 841.8898;

    private double letterhead_width_in_points;
    private double letterhead_height_in_points;

    private Layout(double letterhead_width_in_points,
                   double letterhead_height_in_points) {

    }

    public static Layout TypeA() {
        return new Layout(DOCUMENT_WIDTH_IN_POINTS,
                76.5354);
    }

    public static Layout TypeB() {
        return new Layout(DOCUMENT_WIDTH_IN_POINTS,
                127.559);
    }


}
