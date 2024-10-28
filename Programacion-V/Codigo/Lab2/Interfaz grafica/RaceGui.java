import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceGui extends Thread {

    private int finish;
    private String name;
    private JProgressBar progressBar;
    private JLabel positionLabel;
    private static int positionCounter = 1;

    public RaceGui(int finish, String name, JProgressBar progressBar, JLabel positionLabel) {
        this.finish = finish;
        this.name = name;
        this.progressBar = progressBar;
        this.positionLabel = positionLabel;
    }

    public void run() {
        for (int i = 0; i < finish; i++) {
            progressBar.setValue(i);
            try {
                int sleepTime = (int) (Math.random() * 50);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println(name + " Fue Interrumpido");
            }
        }
        positionLabel.setText("Posición: " + positionCounter++); 
        System.out.println(name + " Terminó la carrera");        
    }

    public static class Race extends JFrame {
        private JProgressBar[] progressBars;
        private JLabel[] positionLabels;
        private JTextField[] nameFields;
        private JButton startButton;
        private RaceGui[] raceCars;

        public Race(int numCars, int finish) {
            setTitle("Carrera en Proceso");
            setSize(800, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            progressBars = new JProgressBar[numCars];
            positionLabels = new JLabel[numCars];
            nameFields = new JTextField[numCars];
            raceCars = new RaceGui[numCars];

            for (int i = 0; i < numCars; i++) {
                JLabel carLabel = new JLabel("Coche " + (i + 1) + ":");
                gbc.gridx = 0;
                gbc.gridy = i;
                gbc.insets = new Insets(5, 5, 5, 5);
                add(carLabel, gbc);

                nameFields[i] = new JTextField();
                nameFields[i].setPreferredSize(new Dimension(80, 20)); 
                gbc.gridx = 1;
                gbc.gridy = i;
                gbc.insets = new Insets(5, 5, 5, 20); 
                add(nameFields[i], gbc);

                progressBars[i] = new JProgressBar(0, finish);
                progressBars[i].setStringPainted(true);
                gbc.gridx = 2;
                gbc.gridy = i;
                gbc.insets = new Insets(5, 5, 5, 5); 
                add(progressBars[i], gbc);

                positionLabels[i] = new JLabel("Posición: ");
                gbc.gridx = 3;
                gbc.gridy = i;
                gbc.insets = new Insets(5, 5, 5, 5); 
                add(positionLabels[i], gbc);
            }

            startButton = new JButton("Iniciar la Carrera");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startButton.setEnabled(false); 
                    startRace(finish);
                }
            });
            gbc.gridx = 0;
            gbc.gridy = numCars;
            gbc.gridwidth = 4;
            gbc.insets = new Insets(20, 5, 5, 5); 
            add(startButton, gbc);
        }

        private void startRace(int finish) {
            
            for (int i = 0; i < raceCars.length; i++) {
                positionLabels[i].setText("Posición: ");
                progressBars[i].setValue(0); 
            }
            positionCounter = 1;

            for (int i = 0; i < raceCars.length; i++) {
                String name = nameFields[i].getText();
                raceCars[i] = new RaceGui(finish, name, progressBars[i], positionLabels[i]);
                raceCars[i].start();
            }

            
            new Thread(() -> {
                for (RaceGui raceCar : raceCars) {
                    try {
                        raceCar.join(); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                SwingUtilities.invokeLater(() -> startButton.setEnabled(true));
            }).start();
        }

        public static void main(String[] args) {
            int numCars = 5;
            int finish = 100;

            Race race = new Race(numCars, finish);
            race.setVisible(true);
        }
    }
}