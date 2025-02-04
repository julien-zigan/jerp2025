/*
* MIT License
*
* Copyright (c) 2025 Julien Zigan
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
 */
package jerp.businessletter;

/**
 * Provides size and positioning information of all letter regions.
 * Information needed for drawing letters (e.g. to the screen in a gui,
 * to a PDF) can be obtained from this class.
 *
 * There are two layout types for each of which there is a factory method.
 * Layout objects can only be created through these factory methods.
 * The Types represent the two Layout ypes specified in DIN 5008.
 *
 * The letterhead can be aligned left, center-left, center,
 * center-right or right. By default, it is left-aligned.
 */

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
        return "Layout [type=" + type + "\nletterheadX=" + letterheadX + "\nletterheadY="
                + letterheadY + "\nletterheadWidthInPoints=" + letterheadWidthInPoints
                + "\nletterheadHeightInPoints=" + letterheadHeightInPoints +
                "\nletterhead alignmetn=" + alignment + "]";
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
