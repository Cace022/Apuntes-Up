import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class InterfazGui extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private JTextField inputField;
    private JTextArea outputArea;
    private PrintsNumbers p;
    private Thread t;
    private int totalTime;

    public InterfazGui() {
        setTitle("Contador con Hilos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Panel superior para controles
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        JLabel inputLabel = new JLabel("Ingrese tiempo (segundos):");
        inputField = new JTextField(10);
        startButton = new JButton("Iniciar");
        stopButton = new JButton("Pausar");
        stopButton.setEnabled(false);

        // Área de texto para mostrar los números
        outputArea = new JTextArea(15, 20);
        outputArea.setEditable(false);
        outputArea.setBorder(new EmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // Añadir componentes al panel de control
        controlPanel.add(inputLabel);
        controlPanel.add(inputField);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        // Añadir componentes a la ventana
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Crear una única instancia de PrintsNumbers
        p = new PrintsNumbers();

        startButton.addActionListener(e -> {
            try {
                int n = Integer.parseInt(inputField.getText());
                totalTime = n; // Guardar el tiempo total ingresado

                if (p.isCompleted()) { 
                    // Si el proceso anterior terminó, limpiar pantalla y reiniciar
                    outputArea.setText("");
                    p.reset(); // Reiniciar el contador
                }

                if (p.isPaused()) {
                    // Reanudar si está pausado
                    p.resumePrinting();
                } else {
                    // Iniciar un nuevo proceso si no está en pausa
                    p.startPrinting(n);
                    t = new Thread(p);
                    t.start();
                }

                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                inputField.setEnabled(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese un número válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        stopButton.addActionListener(e -> {
            if (p.isRunning()) {
                p.pausePrinting();
                resetControls();
            }
        });
    }

    private void resetControls() {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        inputField.setEnabled(!p.isRunning()); // Si está corriendo, deshabilitar el input
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGui().setVisible(true));
    }
    
    // Clase interna PrintsNumbers
    private class PrintsNumbers implements Runnable {
        private volatile boolean keepgoing = true;
        private volatile boolean paused = false;
        private volatile boolean running = false;
        private volatile boolean completed = false;
        private int currentNumber = 1;
        private int totalTime = 0;

        public void stopPrinting() {
            keepgoing = false;
            running = false;
            paused = false;
        }

        public void startPrinting(int totalTime) {
            this.totalTime = totalTime;
            keepgoing = true;
            running = true;
            completed = false;
            paused = false;
        }

        public void pausePrinting() {
            paused = true;
            running = false;
        }

        public void resumePrinting() {
            paused = false;
            running = true;
            synchronized (this) {
                notify();
            }
        }

        public boolean isRunning() {
            return running;
        }

        public boolean isPaused() {
            return paused;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void reset() {
            currentNumber = 1;
            completed = false;
        }

        public void run() {
            while (keepgoing) {
                synchronized (this) {
                    while (paused) {
                        try {
                            wait(); // Esperar hasta que se reanude
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (currentNumber > totalTime) {
                    completed = true;
                    SwingUtilities.invokeLater(() -> {
                        outputArea.append("Completado\n"); 
                        outputArea.setCaretPosition(outputArea.getDocument().getLength());
                        resetControls();
                    });
                    stopPrinting(); // Terminar el cont5eo
                    return;
                }

                final int number = currentNumber++;
                SwingUtilities.invokeLater(() -> {
                    outputArea.append(number + "\n");
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                });
                
                try {
                    Thread.sleep(1000); // Contar cada segundo
                } catch (InterruptedException e) {
                    System.out.println("Error");
                }
            }
        }
    }
}
