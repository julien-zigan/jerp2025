package jerp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Letterhead {
    private final BufferedImage content;

    public Letterhead(String imagePath)
            throws IOException, IllegalArgumentException {
        File file = new File(imagePath);
        requirePNGorJPEG(file);
        content = ImageIO.read(file);
    }

    public BufferedImage getContent() {
        return content;
    }

    private void requirePNGorJPEG(File file) throws IOException {
        Path path = file.toPath();
        String contentType = Files.probeContentType(path);
        boolean isPNG = Objects.equals(contentType, "image/png");
        boolean isJPEG = Objects.equals(contentType, "image/jpeg");
        if (!isPNG && !isJPEG) {
            throw new IllegalArgumentException(contentType + " is not supported");
        }
    }
}
