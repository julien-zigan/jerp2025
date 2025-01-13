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

    // TODO: implement alignment (left, left-center, center, right-center, right)


    public Letterhead(String imagePath)
            throws IOException, IllegalArgumentException {
        File file = requirePNGorJPEG(new File(imagePath));
        content = ImageIO.read(file);
    }

    public BufferedImage getContent() {
        return content;
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
