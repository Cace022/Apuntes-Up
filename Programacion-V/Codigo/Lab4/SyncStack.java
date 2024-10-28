import java.util.Vector;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class SyncStack {
    Vector<Character> buffer = new Vector<>();
    private JTextArea outputArea;
    public int num;

    // Constructor que recibe el JTextArea para mostrar la información
    public SyncStack(JTextArea outputArea) {
        this.outputArea = outputArea;
    }

    public synchronized char pop() {
        char c;
        while (buffer.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Character c1 = buffer.remove(buffer.size() - 1);
        c = c1.charValue();
        
        // Actualizar el JTextArea en el hilo correcto
        SwingUtilities.invokeLater(() -> outputArea.append("Consumidor: Pop -> " + c + "\n"));
        
        return c;
    }

    public synchronized void push(char c) {
        buffer.add(c);
        this.notify();

        // Actualizar el JTextArea en el hilo correcto
        SwingUtilities.invokeLater(() -> outputArea.append("Productor: Push -> " + c + "\n"));
    }
}
