import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageEditorModel {
    private String inputFileName;
    private Image originalImage;
    private Image currentImage;
    private final List<Image> history;
    private final List<String> appliedFilters;

    public ImageEditorModel() {
        history = new ArrayList<>();
        appliedFilters = new ArrayList<>();
    }

    public void loadImage(String fileName, Image image) throws InvalidImageFormatException {
        if (image == null) {
            throw new InvalidImageFormatException("La imagen seleccionada no es válida.");
        }
        inputFileName = fileName;
        originalImage = image.copy();
        currentImage = image.copy();
        history.clear();
        appliedFilters.clear();
    }

    public Image applyNegative() throws ImageNotFoundException {
        return applyFilter("Negativo", new ImageEditor(requireImage()).negative());
    }

    public Image applyGrayscale() throws ImageNotFoundException {
        return applyFilter("Escala de grises", new ImageEditor(requireImage()).grayscale());
    }

    public Image applyBrightness(int amount) throws ImageNotFoundException {
        String name = amount >= 0 ? "Brillo +" + amount : "Brillo " + amount;
        return applyFilter(name, new ImageEditor(requireImage()).brightness(amount));
    }

    public Image undo() throws ImageNotFoundException, EmptyHistoryException {
        requireImage();
        if (history.isEmpty()) {
            throw new EmptyHistoryException("No hay operaciones para deshacer.");
        }
        currentImage = history.remove(history.size() - 1);
        appliedFilters.remove(appliedFilters.size() - 1);
        return currentImage;
    }

    public Image reset() throws ImageNotFoundException {
        requireImage();
        currentImage = originalImage.copy();
        history.clear();
        appliedFilters.clear();
        return currentImage;
    }

    public Image getOriginalImage() throws ImageNotFoundException {
        requireImage();
        return originalImage;
    }

    public Image getCurrentImage() throws ImageNotFoundException {
        return requireImage();
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public int getHistorySize() {
        return history.size();
    }

    public List<String> getAppliedFilters() {
        return Collections.unmodifiableList(new ArrayList<>(appliedFilters));
    }

    public boolean hasImage() {
        return currentImage != null;
    }

    private Image applyFilter(String name, Image result) {
        history.add(currentImage.copy());
        appliedFilters.add(name);
        currentImage = result;
        return currentImage;
    }

    private Image requireImage() throws ImageNotFoundException {
        if (currentImage == null) {
            throw new ImageNotFoundException("Primero debe cargar una imagen.");
        }
        return currentImage;
    }
}
