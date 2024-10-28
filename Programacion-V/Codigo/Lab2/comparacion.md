# Asignacion 2 

Instrucciones: Modificar las clases RaceCar.java y Race.java del laboratorio LAB15.2

## 1. Librerias Importadas:

### Original:
```java
import java.util.Scanner;
```

### Modificacion:
```java
import javax.swing.*; // Para interfaces gráficas (Swing)
import java.awt.*; // Para gráficos y eventos (AWT)
import java.awt.event.ActionEvent; // Para eventos de acción
import java.awt.event.ActionListener; // Para manejar eventos de acción
```

### Diferencias:
- **`javax.swing.*`**: Utilizado para crear componentes de interfaz gráfica más avanzados y personalizables.
- **`java.awt.*`**: Utilizado para gráficos básicos y manejo de eventos.
- **`java.awt.event.ActionEvent`**: Representa un evento de acción, como un clic en un botón.
- **`java.awt.event.ActionListener`**: Interfaz para recibir eventos de acción.

## 2. Clase RaceCar

### Original:
```java
public class RaceCar extends Thread{

    private int finish;
    private String name; 

    // Realizacion del constructor de la clase
    public RaceCar(int finish, String name) {
        this.finish = finish;
        this.name = name;
    }

    // Dentro del metodo run se realiza un bucle que ejecute un numero de veces finish.
    public void run () {
        for (int i = 0; i < finish; i++) {
            System.out.println(name + " " + i);
        try {
            int sleepTime = (int) (Math.random() * 5000);  // Se usa math.random que es un metodo que genera un numero aleatorio entre 0 y 1
            Thread.sleep(sleepTime);                       // Se duerme el hilo por un tiempo aleatorio
        } catch (InterruptedException e) {
            System.out.println(name + "Fue Interrumpido"); // Se imprime un mensaje si el hilo es interrumpido
        }
        System.out.println(name + "Termino la carrera");   // Se imprime un mensaje cuando el hilo termina la carrera
        }
    }
}
```

### Modificacion:
```java
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
}
```

### Diferencias:
- **Original**: La clase [`RaceCar`] imprime el progreso de la carrera en la consola.
- **Modificada**: La clase [`RaceGui`] actualiza una barra de progreso [`JProgressBar`] y una etiqueta de posición [`JLabel`] en una interfaz gráfica.

## 3. Clase Race

### Original:
```java
// Se crea la clase public Race que almacena el metodo main 
public class Race {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);      // Se crea un objeto scanner para leer la entrada del usuario
        RaceCar[] cars = new RaceCar[5];               // Se crea un arreglo de objetos RaceCar

        for (int i = 0; i < cars.length; i++) {
            System.out.println("Ingrese el nombre del carro: ");
            String name = scanner.nextLine();
            cars[i] = new RaceCar(2, name);
        }

        for (RaceCar car : cars) {
            car.start();
        }
        scanner.close();
    }
}
```

### Modificacion: 
```java
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
```

### Diferencias:
- **Original**: La clase [`Race`] utiliza la consola para interactuar con el usuario y mostrar el progreso de la carrera.

- **Modificada**: La clase [`Race`] extiende [`JFrame`] para crear una interfaz gráfica que muestra barras de progreso y etiquetas de posición para cada coche. También incluye un botón para iniciar la carrera.