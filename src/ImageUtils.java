import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static Image load(String filename) throws IOException, InvalidImageFormatException {
        BufferedImage bufferedImage = ImageIO.read(new File(filename));
        if (bufferedImage == null) {
            throw new InvalidImageFormatException(
                    "El archivo seleccionado no contiene una imagen válida.");
        }

        Pixel[][] pixels = new Pixel[bufferedImage.getHeight()][bufferedImage.getWidth()];
        for (int row = 0; row < bufferedImage.getHeight(); row++) {
            for (int col = 0; col < bufferedImage.getWidth(); col++) {
                int rgb = bufferedImage.getRGB(col, row);
                pixels[row][col] = new Pixel(
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        rgb & 0xFF);
            }
        }
        return new Image(pixels);
    }

    public static void save(Image image, String filename) throws IOException {
        String format = extensionOf(filename);
        if (!ImageIO.write(toBufferedImage(image), format, new File(filename))) {
            throw new IOException("No existe un escritor para el formato " + format + ".");
        }
    }

    public static BufferedImage toBufferedImage(Image image) {
        BufferedImage output = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                Pixel p = image.getPixel(row, col);
                int rgb = (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
                output.setRGB(col, row, rgb);
            }
        }
        return output;
    }

    private static String extensionOf(String filename) {
        int dot = filename.lastIndexOf('.');
        return dot < 0 ? "png" : filename.substring(dot + 1).toLowerCase();
    }
}
