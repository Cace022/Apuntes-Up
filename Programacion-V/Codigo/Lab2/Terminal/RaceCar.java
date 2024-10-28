import java.util.Scanner;

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

}