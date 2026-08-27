public class ImageEditor {
    private final Image source;

    public ImageEditor(Image source) {
        if (source == null) {
            throw new IllegalArgumentException("La imagen no puede ser nula.");
        }
        this.source = source;
    }

    public Image negative() {
        Image result = new Image(source.getHeight(), source.getWidth());
        for (int row = 0; row < source.getHeight(); row++) {
            for (int col = 0; col < source.getWidth(); col++) {
                Pixel p = source.getPixel(row, col);
                result.setPixel(row, col, new Pixel(
                        255 - p.getRed(), 255 - p.getGreen(), 255 - p.getBlue()));
            }
        }
        return result;
    }

    public Image grayscale() {
        Image result = new Image(source.getHeight(), source.getWidth());
        for (int row = 0; row < source.getHeight(); row++) {
            for (int col = 0; col < source.getWidth(); col++) {
                Pixel p = source.getPixel(row, col);
                int average = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
                result.setPixel(row, col, new Pixel(average, average, average));
            }
        }
        return result;
    }

    public Image brightness(int amount) {
        Image result = new Image(source.getHeight(), source.getWidth());
        for (int row = 0; row < source.getHeight(); row++) {
            for (int col = 0; col < source.getWidth(); col++) {
                Pixel p = source.getPixel(row, col);
                result.setPixel(row, col, new Pixel(
                        p.getRed() + amount,
                        p.getGreen() + amount,
                        p.getBlue() + amount));
            }
        }
        return result;
    }
}
