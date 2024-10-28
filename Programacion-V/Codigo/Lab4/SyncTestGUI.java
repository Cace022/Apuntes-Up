import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SyncTestGUI extends JFrame {
    private JTextArea outputArea;
    private JButton startProducerButton;
    private JButton startConsumerButton;
    private SyncStack stack;
    private Thread producerThread;
    private Thread consumerThread;

    public SyncTestGUI() {
        setTitle("Productor - Consumidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Crear área de texto para mostrar las acciones
        outputArea = new JTextArea(15, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Crear panel para los botones
        JPanel buttonPanel = new JPanel();
        startProducerButton = new JButton("Iniciar Productor");
        startConsumerButton = new JButton("Iniciar Consumidor");
        buttonPanel.add(startProducerButton);
        buttonPanel.add(startConsumerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Instanciar el stack sincronizado
        stack = new SyncStack(outputArea); // Pasar el JTextArea

        // Acción para iniciar el productor
        startProducerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el área de salida antes de iniciar
                outputArea.setText("");
                // Deshabilitar el botón de productor hasta que termine el proceso
                startProducerButton.setEnabled(false);

                Producer producer = new Producer(stack);
                producerThread = new Thread(producer);
                producerThread.start();
                outputArea.append("Productor iniciado...\n");

                // Esperar a que termine el hilo y luego habilitar el botón
                new Thread(() -> {
                    try {
                        producerThread.join();  // Esperar a que termine el productor
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    SwingUtilities.invokeLater(() -> startProducerButton.setEnabled(true)); // Habilitar botón
                }).start();
            }
        });

        // Acción para iniciar el consumidor
        startConsumerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el área de salida antes de iniciar
                outputArea.setText("");
                // Deshabilitar el botón de consumidor hasta que termine el proceso
                startConsumerButton.setEnabled(false);

                Consumer consumer = new Consumer(stack);
                consumerThread = new Thread(consumer);
                consumerThread.start();
                outputArea.append("Consumidor iniciado...\n");

                // Esperar a que termine el hilo y luego habilitar el botón
                new Thread(() -> {
                    try {
                        consumerThread.join();  // Esperar a que termine el consumidor
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    SwingUtilities.invokeLater(() -> startConsumerButton.setEnabled(true)); // Habilitar botón
                }).start();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SyncTestGUI gui = new SyncTestGUI();
            gui.setVisible(true);
        });
    }
}
