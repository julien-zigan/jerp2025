package jerp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Letterhead {
    private final BufferedImage content;

    public Letterhead(String imagePath) throws IOException {
        content = ImageIO.read(new File(imagePath));
    }

    public BufferedImage getContent() {
        return content;
    }
}
