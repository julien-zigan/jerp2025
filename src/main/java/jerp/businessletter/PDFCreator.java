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

import jerp.businessletter.letterfields.Letterhead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static jerp.businessletter.Layout.DOCUMENT_HEIGHT_IN_POINTS;
import static jerp.businessletter.Layout.DOCUMENT_WIDTH_IN_POINTS;

public class PDFCreator {
    public static File createFrom(BusinessLetterDIN5008 letter,
                                  String path) throws IOException {
        path = addFileExtension(path);
        File file = new File(path);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                Layout layout = letter.getLayout();
                Letterhead letterhead = letter.getLetterhead();

                if (letterhead != null) {
                    float height = layout.getLetterheadHeightInPoints();
                    float width = (float) (height * letterhead.getAspectRatio());
                    float y = layout.getLetterheadY();

                    // TODO: Extract
                    if (width > DOCUMENT_WIDTH_IN_POINTS) {
                        double resizingFactor = DOCUMENT_WIDTH_IN_POINTS / width;
                        height *= (float) resizingFactor;
                        width *= (float) resizingFactor;
                        float heightDifference = DOCUMENT_HEIGHT_IN_POINTS
                                - layout.getLetterheadY()
                                - height;
                        y += heightDifference / 2;
                    }

                    float leftEnd = layout.getLetterheadX() - (width / 2);
                    float rightEnd = layout.getLetterheadX() + (width / 2);
                    float x = layout.getLetterheadX() - (width /2);

                    if (layout.getLetterheadX() < DOCUMENT_WIDTH_IN_POINTS / 2) {
                        if (leftEnd < 0) {
                            x = 0F;
                        }
                    } else {
                        if (rightEnd > DOCUMENT_WIDTH_IN_POINTS) {
                            x = DOCUMENT_WIDTH_IN_POINTS - width;
                        }
                    }

                    BufferedImage bufferedImage = letterhead.getGraphic();
                    PDImageXObject image  =
                            LosslessFactory.createFromImage(document, bufferedImage);
                    contentStream.drawImage(
                            image,
                            x,
                            y,
                            width,
                            height
                    );
                }
            }

            document.save(file);
        }
        return file;
    }

    private static String addFileExtension(String path) {
        boolean needsFileExtension =
                !(path.trim().toLowerCase().endsWith(".pdf"));
        if (needsFileExtension) {
            path = path + ".pdf";
        }
        return path;
    }


}
