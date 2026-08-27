public class Image {
    private final Pixel[][] pixels;

    public Image(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser positivas.");
        }
        pixels = new Pixel[height][width];
    }

    public Image(Pixel[][] pixels) {
        if (pixels == null || pixels.length == 0 || pixels[0].length == 0) {
            throw new IllegalArgumentException("La matriz de píxeles no puede estar vacía.");
        }
        this.pixels = new Pixel[pixels.length][pixels[0].length];
        for (int row = 0; row < pixels.length; row++) {
            if (pixels[row].length != pixels[0].length) {
                throw new IllegalArgumentException("La matriz de píxeles debe ser rectangular.");
            }
            for (int col = 0; col < pixels[row].length; col++) {
                Pixel pixel = pixels[row][col];
                if (pixel == null) {
                    throw new IllegalArgumentException("La imagen contiene un píxel nulo.");
                }
                this.pixels[row][col] = new Pixel(
                        pixel.getRed(), pixel.getGreen(), pixel.getBlue());
            }
        }
    }

    public int getHeight() {
        return pixels.length;
    }

    public int getWidth() {
        return pixels[0].length;
    }

    public Pixel getPixel(int row, int col) {
        return pixels[row][col];
    }

    public void setPixel(int row, int col, Pixel pixel) {
        if (pixel == null) {
            throw new IllegalArgumentException("El píxel no puede ser nulo.");
        }
        pixels[row][col] = pixel;
    }

    public Image copy() {
        return new Image(pixels);
    }
}
