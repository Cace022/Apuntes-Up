import javax.swing.SwingUtilities;

public class SyncTest {
    public static void main(String[] args) {
        // Crear una ventana gráfica
        SwingUtilities.invokeLater(() -> {
            SyncTestGUI gui = new SyncTestGUI();
            gui.setVisible(true);
        });
    }
}