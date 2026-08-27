public class Pixel {
    private final int red;
    private final int green;
    private final int blue;

    public Pixel(int red, int green, int blue) {
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
