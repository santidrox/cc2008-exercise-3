import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEditorView extends JFrame {
    private final JButton loadButton = new JButton("Abrir imagen");
    private final JButton negativeButton = new JButton("Negativo");
    private final JButton grayscaleButton = new JButton("Escala de grises");
    private final JButton brightnessButton = new JButton("Aumentar brillo");
    private final JButton undoButton = new JButton("Deshacer");
    private final JButton resetButton = new JButton("Reiniciar");
    private final JButton saveButton = new JButton("Guardar resultado");
    private final ImagePanel originalPanel = new ImagePanel();
    private final ImagePanel resultPanel = new ImagePanel();
    private final DefaultListModel<String> historyModel = new DefaultListModel<>();
    private final JLabel historyCountLabel = new JLabel("Operaciones: 0");
    private final JLabel statusLabel = new JLabel("Seleccione una imagen para comenzar.");
    private final JFileChooser chooser = new JFileChooser();

    public ImageEditorView() {
        super("Editor de filtros de imágenes");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Imágenes (PNG, JPG, JPEG, BMP)", "png", "jpg", "jpeg", "bmp"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setLocationByPlatform(true);
        buildInterface();
        setControlsEnabled(false);
    }

    private void buildInterface() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(loadButton);
        toolbar.add(negativeButton);
        toolbar.add(grayscaleButton);
        toolbar.add(brightnessButton);
        toolbar.add(undoButton);
        toolbar.add(resetButton);
        toolbar.add(saveButton);

        JPanel originalContainer = imageContainer("Imagen original", originalPanel);
        JPanel resultContainer = imageContainer("Imagen resultante", resultPanel);
        JSplitPane imageSplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, originalContainer, resultContainer);
        imageSplit.setResizeWeight(0.5);
        imageSplit.setBorder(null);

        JPanel historyPanel = new JPanel(new BorderLayout(5, 5));
        historyPanel.setBorder(BorderFactory.createTitledBorder("Historial de filtros"));
        historyPanel.add(new JScrollPane(new JList<>(historyModel)), BorderLayout.CENTER);
        historyPanel.add(historyCountLabel, BorderLayout.SOUTH);
        historyPanel.setPreferredSize(new Dimension(210, 0));

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        center.add(imageSplit, BorderLayout.CENTER);
        center.add(historyPanel, BorderLayout.EAST);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 10, 8, 10));
        add(toolbar, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel imageContainer(String title, ImagePanel panel) {
        JPanel container = new JPanel(new GridLayout(1, 1));
        container.setBorder(BorderFactory.createTitledBorder(title));
        container.add(panel);
        return container;
    }

    public File chooseImageToOpen() {
        return chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION
                ? chooser.getSelectedFile() : null;
    }

    public File chooseImageToSave() {
        chooser.setSelectedFile(new File("resultado.png"));
        return chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION
                ? ensurePngExtension(chooser.getSelectedFile()) : null;
    }

    public void showImages(BufferedImage original, BufferedImage result) {
        originalPanel.setImage(original);
        resultPanel.setImage(result);
    }

    public void showHistory(List<String> filters, int count) {
        historyModel.clear();
        for (int index = 0; index < filters.size(); index++) {
            historyModel.addElement((index + 1) + ". " + filters.get(index));
        }
        historyCountLabel.setText("Operaciones: " + count);
        undoButton.setEnabled(count > 0);
    }

    public void setControlsEnabled(boolean enabled) {
        negativeButton.setEnabled(enabled);
        grayscaleButton.setEnabled(enabled);
        brightnessButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);
        undoButton.setEnabled(false);
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addLoadListener(ActionListener listener) { loadButton.addActionListener(listener); }
    public void addNegativeListener(ActionListener listener) { negativeButton.addActionListener(listener); }
    public void addGrayscaleListener(ActionListener listener) { grayscaleButton.addActionListener(listener); }
    public void addBrightnessListener(ActionListener listener) { brightnessButton.addActionListener(listener); }
    public void addUndoListener(ActionListener listener) { undoButton.addActionListener(listener); }
    public void addResetListener(ActionListener listener) { resetButton.addActionListener(listener); }
    public void addSaveListener(ActionListener listener) { saveButton.addActionListener(listener); }

    private File ensurePngExtension(File file) {
        return file.getName().toLowerCase().endsWith(".png")
                ? file : new File(file.getParentFile(), file.getName() + ".png");
    }
}
