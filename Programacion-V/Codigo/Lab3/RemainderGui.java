import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.Timer;
import java.util.TimerTask;

public class RemainderGui {
    private JButton iniciarButton;
    private JButton pausarButton;
    private JTextArea textArea;
    private Timer timer;
    private boolean isPaused;
    private int currentMessageIndex; 

    private String[] messages = {"Hello World", "Goodbye World", "Hello Again"};
    private int[] delays = {0, 2000, 5000};

    public RemainderGui() {
        JFrame frame = new JFrame("Remainder GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout(10, 10));
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        iniciarButton = new JButton("Iniciar");
        pausarButton = new JButton("Pausar");
        pausarButton.setEnabled(false);  
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(iniciarButton);
        buttonPanel.add(pausarButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(panel);
        
        iniciarButton.addActionListener(e -> startTimer());
        pausarButton.addActionListener(e -> pauseTimer());
        
   
        frame.setVisible(true);
    }

    private void startTimer() {
        if (isPaused) {
            isPaused = false;
            iniciarButton.setEnabled(false);  
            pausarButton.setEnabled(true);    
            resumeTimer();
        } else {
            if (currentMessageIndex >= messages.length) {
                currentMessageIndex = 0;
                textArea.setText(""); 
            }
            iniciarButton.setEnabled(false);  
            pausarButton.setEnabled(true);    
            startNewTimer();
        }
    }

    private void startNewTimer() {
        timer = new Timer();
        for (int i = currentMessageIndex; i < messages.length; i++) {
            timer.schedule(new Remainder(messages[i], i), delays[i] - (i > 0 ? delays[i - 1] : 0));
        }
    }

    private void resumeTimer() {
        timer = new Timer();
        for (int i = currentMessageIndex; i < messages.length; i++) {
            timer.schedule(new Remainder(messages[i], i), delays[i] - (i > 0 ? delays[i - 1] : 0));
        }
    }

    private void pauseTimer() {
        if (timer != null) {
            isPaused = true;
            timer.cancel(); 
            iniciarButton.setEnabled(true);   
            pausarButton.setEnabled(false);   
        }
    }

    private class Remainder extends TimerTask {
        String message;
        int messageIndex;

        public Remainder(String message, int messageIndex) {
            this.message = message;
            this.messageIndex = messageIndex;
        }

        @Override
        public void run() {
            SwingUtilities.invokeLater(() -> {
                textArea.append(message + "\n");
                currentMessageIndex = messageIndex + 1; 
                if (currentMessageIndex >= messages.length) {
                    iniciarButton.setEnabled(true); 
                    pausarButton.setEnabled(false);
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RemainderGui());
    }
}
