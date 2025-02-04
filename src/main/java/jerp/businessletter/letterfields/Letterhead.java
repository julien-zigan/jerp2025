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
package jerp.businessletter.letterfields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Letterhead {
    private final BufferedImage graphic;

    public Letterhead(String imagePath)
            throws IOException, IllegalArgumentException {
        File file = requirePNGorJPEG(new File(imagePath));
        graphic = ImageIO.read(file);
    }

    public BufferedImage getGraphic() {
        return graphic;
    }

    public int getWidth() {
        return graphic.getWidth();
    }

    public int getHeight() {
        return graphic.getHeight();
    }

    public double getAspectRatio() {
        return graphic.getWidth() / (double) graphic.getHeight();
    }

    private File requirePNGorJPEG(File file) throws IOException {
        Path path = file.toPath();
        String contentType = Files.probeContentType(path);
        boolean isPNG = Objects.equals(contentType, "image/png");
        boolean isJPEG = Objects.equals(contentType, "image/jpeg");
        if (isPNG || isJPEG) {
            return file;
        } else {
            throw new IllegalArgumentException(contentType + " is not supported");
        }
    }
}
