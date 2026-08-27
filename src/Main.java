import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 10);
        SwingUtilities.invokeLater(() -> {
            ImageEditorModel model = new ImageEditorModel();
            ImageEditorView view = new ImageEditorView();
            new ImageEditorController(model, view);
            view.setVisible(true);
        });
    }
}
