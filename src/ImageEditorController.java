import java.io.File;
import java.io.IOException;

public class ImageEditorController {
    private final ImageEditorModel model;
    private final ImageEditorView view;

    public ImageEditorController(ImageEditorModel model, ImageEditorView view) {
        this.model = model;
        this.view = view;
        view.addLoadListener(event -> loadImage());
        view.addNegativeListener(event -> applyFilter(FilterType.NEGATIVE));
        view.addGrayscaleListener(event -> applyFilter(FilterType.GRAYSCALE));
        view.addBrightnessListener(event -> applyFilter(FilterType.BRIGHTNESS));
        view.addUndoListener(event -> undo());
        view.addResetListener(event -> reset());
        view.addSaveListener(event -> saveImage());
    }

    private void loadImage() {
        File file = view.chooseImageToOpen();
        if (file == null) {
            return;
        }
        view.setStatus("Cargando imagen...");
        try {
            model.loadImage(file.getAbsolutePath(), ImageUtils.load(file.getAbsolutePath()));
            view.setControlsEnabled(true);
            refresh();
            view.setStatus("Imagen cargada: " + file.getName());
        } catch (InvalidImageFormatException exception) {
            view.showError(exception.getMessage());
        } catch (ImageNotFoundException exception) {
            view.showError(exception.getMessage());
        } catch (IOException exception) {
            view.showError("No se pudo leer el archivo: " + exception.getMessage());
        } finally {
            view.repaint();
        }
    }

    private void applyFilter(FilterType type) {
        try {
            if (type == FilterType.NEGATIVE) {
                model.applyNegative();
            } else if (type == FilterType.GRAYSCALE) {
                model.applyGrayscale();
            } else {
                model.applyBrightness(30);
            }
            refresh();
            view.setStatus("Filtro aplicado correctamente.");
        } catch (ImageNotFoundException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void undo() {
        try {
            model.undo();
            refresh();
            view.setStatus("Se deshizo la última operación.");
        } catch (ImageNotFoundException | EmptyHistoryException exception) {
            view.showInfo(exception.getMessage());
        }
    }

    private void reset() {
        try {
            model.reset();
            refresh();
            view.setStatus("La imagen volvió a su estado original.");
        } catch (ImageNotFoundException exception) {
            view.showError(exception.getMessage());
        }
    }

    private void saveImage() {
        File file = view.chooseImageToSave();
        if (file == null) {
            return;
        }
        view.setStatus("Guardando imagen...");
        try {
            ImageUtils.save(model.getCurrentImage(), file.getAbsolutePath());
            view.showInfo("Imagen guardada correctamente.");
        } catch (ImageNotFoundException exception) {
            view.showError(exception.getMessage());
        } catch (IOException exception) {
            view.showError("No se pudo guardar la imagen: " + exception.getMessage());
        } finally {
            view.setStatus("Operación de guardado finalizada.");
        }
    }

    private void refresh() throws ImageNotFoundException {
        view.showImages(
                ImageUtils.toBufferedImage(model.getOriginalImage()),
                ImageUtils.toBufferedImage(model.getCurrentImage()));
        view.showHistory(model.getAppliedFilters(), model.getHistorySize());
    }

    private enum FilterType {
        NEGATIVE, GRAYSCALE, BRIGHTNESS
    }
}
